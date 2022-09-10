package com.chrislai.onlineTrade.dao;

import com.chrislai.onlineTrade.constant.stock.EntrustedStockStatus;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockTradeType;
import com.chrislai.onlineTrade.dao.rowmapper.EntrustedOrderRowMapper;
import com.chrislai.onlineTrade.dto.EntrustedOrderRequest;
import com.chrislai.onlineTrade.model.EntrustedOrder;
import com.chrislai.onlineTrade.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class EntrustedStockDaoImpl implements IEntrustedStockDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Value("${timezone}")
    private String TIME_ZONE;


    /**
     * 建立股票委託單
     *
     * @param request EntrustedOrderRequest
     * @return id
     */
    @Override
    public int entrustedOrder(EntrustedOrderRequest request) {
        Map<String, Object> map = new HashMap<>();
        Long currentTimestamp = LocalDateTime.now().atZone(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli();
        map.put("id", IDGenerator.uuid());
//        //這可以再做產生交易編號的的function，來帶交易序號，目前先用stock + currentTimestamp當序號
//        map.put("tradeNo","STOCK" + currentTimestamp);
        map.put("vendorId", request.getVendorId());
        map.put("stockId", request.getStockId());
        map.put("userAccountNumber", request.getUserAccountNumber());
        map.put("userName", request.getUserName());
        map.put("tradeType", request.getTradeType().getValue());
        map.put("stockType", request.getStockType().getValue());
        map.put("stockCondition", request.getStockCondition().getValue());
        map.put("priceType", request.getPriceType().getValue());
        //這邊可以改寫成透過系統設定開盤時間、盤後的時間、盤後結束時間去做判斷要給什麼狀態
        //目前先都給委託成功
        map.put("status", EntrustedStockStatus.ENTRUSTED_SUCCESS.getValue());
        map.put("business", request.getBusiness().getValue());
        if (request.getTradeType() == EntrustedStockTradeType.AFTER_HOURS_FRACTIONAL_SHARES) {//零股
            map.put("entrustedQty", request.getEntrustedQty());
        } else {// 一張等於1000股
            map.put("entrustedQty", request.getEntrustedQty() * 1000L);
        }
        map.put("entrustedPrice", request.getEntrustedPrice());
        map.put("remark", request.getRemark());
        map.put("entrustedTimestamp", currentTimestamp);
        map.put("createdTimestamp", currentTimestamp);
        map.put("createdUser", "SYSTEM");
        System.out.println("map:" + map.toString());
        String sql = "INSERT INTO entrusted_order(id,vendor_id,stock_id,user_account_number," +
                "user_name,trade_type,stock_type,stock_condition,price_type," +
                "status,business,entrusted_qty,entrusted_price,remark,entrusted_timestamp," +
                "created_timestamp,created_user) " +
                " VALUES(:id,:vendorId,:stockId,:userAccountNumber," +
                ":userName,:tradeType,:stockType,:stockCondition,:priceType," +
                ":status,:business,:entrustedQty,:entrustedPrice,:remark,:entrustedTimestamp," +
                ":createdTimestamp,:createdUser)";

        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<EntrustedOrder> getEntrustedOrder(EntrustedOrder request) {
        StringBuilder sb = new StringBuilder().append("SELECT id,vendor_id,stock_id,user_account_number,user_name,trade_type,stock_type,stock_condition,price_type,status,business,entrusted_qty,entrusted_price,remark,entrusted_timestamp,created_timestamp,created_user from entrusted_order where 0=0");
        Map<String, Object> map = new HashMap<>();
        Long currentTimestamp = LocalDateTime.now().atZone(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli();

        if (Objects.nonNull(request.getVendorId()) && !request.getVendorId().isEmpty()) {
            map.put("vendorId", request.getVendorId());
            sb.append(" and vendor_id = :vendorId");
        }
        if (Objects.nonNull(request.getStockId()) && !request.getStockId().isEmpty()) {
            map.put("stockId", request.getStockId());
            sb.append(" and stock_id = :stockId");
        }
        if (Objects.nonNull(request.getUserAccountNumber()) && !request.getUserAccountNumber().isEmpty()) {
            map.put("userAccountNumber", request.getUserAccountNumber());
            sb.append(" and user_account_number = :userAccountNumber");
        }
        if (Objects.nonNull(request.getUserName()) && !request.getUserName().isEmpty()) {
            map.put("userName", request.getUserName());
            sb.append(" and user_name = :userName");
        }
        if (Objects.nonNull(request.getTradeType())) {
            map.put("tradeType", request.getTradeType().getValue());
            sb.append(" and trade_type = :tradeType");
        }
        if (Objects.nonNull(request.getStockType())) {
            map.put("stockType", request.getStockType().getValue());
            sb.append(" and stock_type = :stockType");
        }
        if (Objects.nonNull(request.getStockCondition())) {
            map.put("stockCondition", request.getStockCondition().getValue());
            sb.append(" and stock_condition = :stockCondition");
        }
        if (Objects.nonNull(request.getPriceType())) {
            map.put("priceType", request.getPriceType().getValue());
            sb.append(" and price_type = :priceType");
        }
        if (Objects.nonNull(request.getStatus())) {
            map.put("status", request.getStatus().getValue());
            sb.append(" and status = :status");
        }
        if (Objects.nonNull(request.getBusiness())) {
            map.put("business", request.getBusiness().getValue());
            sb.append(" and business = :business");
        }
        if (Objects.nonNull(request.getEntrustedQty())) {
            if (request.getTradeType() == EntrustedStockTradeType.AFTER_HOURS_FRACTIONAL_SHARES) {//零股
                map.put("entrustedQty", request.getEntrustedQty());
            } else {// 一張等於1000股
                map.put("entrustedQty", request.getEntrustedQty() * 1000L);
            }
            sb.append(" and entrusted_qty = :entrustedQty");
        }
        if (Objects.nonNull(request.getEntrustedPrice())) {
            map.put("entrustedPrice", request.getEntrustedPrice());
            sb.append(" and entrusted_price = :entrustedPrice");
        }
        if (Objects.nonNull(request.getRemark()) && !request.getRemark().isEmpty()) {
            map.put("remark", request.getRemark());
            sb.append(" and remark = :remark");
        }


        return namedParameterJdbcTemplate.query(sb.toString(), map, new EntrustedOrderRowMapper());
    }
}
