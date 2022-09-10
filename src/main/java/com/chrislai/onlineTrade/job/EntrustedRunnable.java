package com.chrislai.onlineTrade.job;

import com.chrislai.onlineTrade.constant.common.Business;
import com.chrislai.onlineTrade.constant.common.LockPrefix;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockPriceType;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockStatus;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockTradeType;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockType;
import com.chrislai.onlineTrade.model.EntrustedOrder;
import com.chrislai.onlineTrade.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 針對股票現股、限價做交易撮合
 **/
@Slf4j
class EntrustedRunnable implements Runnable, IEntrustedJob<EntrustedOrder> {
    private EntrustedOrder entrustedBuyOrder = new EntrustedOrder();
    private Integer threadSeq;
    @Autowired
    private RedissonClient redissonClient;

    public EntrustedRunnable(Integer threadSeq) {
        this.threadSeq = threadSeq;
    }

    @Override
    public void run() {
        executeJob();
    }
    //只取已committed的資料
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void executeJob() {
        //1.產生processor 自已產uuid
        try {
            String processor = IDGenerator.uuid();
            EntrustedOrder top1BuyerParams = new EntrustedOrder();
            top1BuyerParams = getBasicCondition(top1BuyerParams);
            top1BuyerParams.setProcessor(processor);
            top1BuyerParams.setBusiness(Business.BUY);
            top1BuyerParams.setStatus(EntrustedStockStatus.ENTRUSTED_SUCCESS);

            updateBuyProcessor(top1BuyerParams, 1);

            EntrustedOrder proceesorparmas = new EntrustedOrder();
            proceesorparmas.setProcessor(processor);
            proceesorparmas.setBusiness(Business.BUY);
            List<EntrustedOrder> entrustedOrderList = getEntrustedData(proceesorparmas);

            if (!entrustedOrderList.isEmpty()) {
                EntrustedOrder sellerParams = new EntrustedOrder();
                sellerParams.setProcessor(processor);
                sellerParams.setBusiness(Business.SELL);
                sellerParams = getBasicCondition(sellerParams);
                sellerParams.setStatus(EntrustedStockStatus.ENTRUSTED_SUCCESS);

                proceesorparmas.setProcessor(processor);
                proceesorparmas.setBusiness(Business.SELL);

                log.info("EntrustedRunnable thread seq :{} Task processor: {}", threadSeq, processor);
                for (int i = 0; i < entrustedOrderList.size(); i++) {
                    entrustedBuyOrder = entrustedOrderList.get(i);
                    boolean res;
                    //鎖同支股票id，限價、現股、整股，同一價格
                    String lockName = LockPrefix.ENTRUSTED_STOCK +
                            entrustedBuyOrder.getStockId() +
                            entrustedBuyOrder.getPriceType() +
                            entrustedBuyOrder.getStockType() +
                            entrustedBuyOrder.getTradeType() +
                            entrustedBuyOrder.getEntrustedPrice();
                    //鎖同一支股票、限價、現買、整股、同價格
                    RLock lock = redissonClient.getLock(lockName);
                    try {

                        //鎖60秒自動釋放，取鎖取不到超過5秒就釋放
                        res = lock.tryLock(60, 5, TimeUnit.SECONDS);
                        if (res) {
                            log.info("entrustedOrderJob lockName:{} 拿到鎖囉", lockName);
                            sellerParams.setStockId(entrustedBuyOrder.getStockId());
                            sellerParams.setEntrustedPrice(entrustedBuyOrder.getEntrustedPrice());
                            sellerParams.setEntrustedQty(entrustedBuyOrder.getEntrustedQty());

                            updateSellProcessor(sellerParams);
                        //取回委賣資料
                            List<EntrustedOrder> entrustedSellOrder = getEntrustedData(proceesorparmas);
                            processData(entrustedBuyOrder, entrustedSellOrder);
                        }
                        log.info("entrustedOrderJob lockName:{} 拿鎖失敗", lockName);
                    } catch (Exception ex) {
                        log.info("entrustedOrderJob lockName:{} error:", lockName, ex.getMessage());
                    } finally {
                        log.info("entrustedOrderJob lockName:{} 解鎖成功", lockName);
                        lock.unlock();
                    }
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * 將時間最早的一筆，狀態是現買且整股委託成功的
     * * 押上processor欄位
     *
     * @param request 參數
     * @param limit   取回幾筆
     */
    @Override
    public void updateBuyProcessor(EntrustedOrder request, Integer limit) {

        // update entrusted_order set processor= #processor
        // where  status=#status
        // and business=#business
        // and trade_type = #tradeType
        // and price_type = #priceType
        // and stock_type =#stockType
        // order by entrusted_timestamp asc limit #limit;
    }

    /**
     * 透過processor 取回委託資料資料
     *
     * @param request
     * @return 資料
     */
    @Override
    public List<EntrustedOrder> getEntrustedData(EntrustedOrder request) {
        //3.透過processor id 取回1筆資料
        // SELECT  * FROM entrusted_order where processor= #processor order by entrusted_timestamp asc limit 0,10
        List<EntrustedOrder> entrustedOrderList = new ArrayList<>();
        //假資料
        EntrustedOrder currentData = new EntrustedOrder();
        currentData = getBasicCondition(currentData);
        currentData.setVendorId("123456");
        currentData.setUserAccountNumber("888-123456");
        currentData.setUserName("王小明");
        currentData.setBusiness(Business.BUY);
        currentData.setEntrustedPrice(BigDecimal.valueOf(25));
        currentData.setEntrustedQty(1L);
        currentData.setStockId("STOCK1");
        entrustedOrderList.add(currentData);
        return entrustedOrderList;
    }

    /**
     * 去將今天委託成功同股票id、現股、限價 、整股、押上processor值
     *
     * @param request 委託成功賣單資料
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateSellProcessor(EntrustedOrder request) {
        //
    }

    /**
     * 將買單與賣單做交易進行處理，去更新買單與賣單的traded_qty成交數量、traded_price成交價格、fee手續費、traded_timestamp'成交日期
     * update_timestamp更新時間 、updated_user修改人員
     * status =2全部成交
     * @param buyData,sellData
     */
    public boolean processData(EntrustedOrder buyData, List<EntrustedOrder> sellData) {
        return true;
    }

    private EntrustedOrder getBasicCondition(EntrustedOrder currentData) {
        //整股
        currentData.setTradeType(EntrustedStockTradeType.WHOLE_SHARE);
        //現股
        currentData.setStockType(EntrustedStockType.CURRENT_STOCK);
        //限價
        currentData.setPriceType(EntrustedStockPriceType.LIMIT_PRICE);
        return currentData;
    }
}
