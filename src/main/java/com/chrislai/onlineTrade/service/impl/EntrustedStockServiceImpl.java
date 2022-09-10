package com.chrislai.onlineTrade.service.impl;

import com.chrislai.onlineTrade.constant.common.LockPrefix;
import com.chrislai.onlineTrade.dao.IEntrustedStockDao;
import com.chrislai.onlineTrade.dto.EntrustedOrderRequest;
import com.chrislai.onlineTrade.service.IEntrustedTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Slf4j
public class EntrustedStockServiceImpl implements IEntrustedTradeService<EntrustedOrderRequest> {
    private final IEntrustedStockDao entrustedStockDao;
    private final RedissonClient redissonClient;

    /**
     * 產生委託訂單
     *
     * @param request entrustedOrderRequest
     * @return 影響筆數
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int entrustedOrder(EntrustedOrderRequest request) {
        RLock lock = redissonClient.getLock(LockPrefix.ENTRUSTED_STOCK + request.getStockId());
        boolean res;
        try {
            //鎖60秒自動釋放，取鎖取不到超過5秒就釋放
            res = lock.tryLock(60, 5, TimeUnit.SECONDS);
            if (res) {
                log.info("entrustedOrder stockId:{} 拿到鎖囉", request.getStockId());
                return entrustedStockDao.entrustedOrder(request);
            }
            log.error("entrustedOrder stockId:{} 取锁失败", request.getStockId());
        } catch (Exception ex) {
            log.error("entrustedOrder stockId:{} error:{}", request.getStockId(), ex.toString());
        } finally {
            log.info("entrustedOrder stockId:{} 解鎖囉", request.getStockId());
            lock.unlock();
        }
        return 0;
    }

    /**
     * 確認一些委託時的商業邏輯
     *
     * @param request request
     * @return true/false
     */
    @Override
    public void validateLogic(EntrustedOrderRequest request) throws Exception {
        if (!isExistVendor(request.getVendorId())) {
            throw new Exception("廠商不存在");
        }
        //判斷user account存不存在
        //判斷股票stock id存不存在
        //其他邏輯判斷
    }

    /**
     * 判斷vendor ID存不存在
     *
     * @param vendorId 廠商id
     * @return true/false
     */
    private boolean isExistVendor(String vendorId) {
        //查詢vendorid資料存不存在
        return true;
    }
}
