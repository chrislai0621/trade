package com.chrislai.onlineTrade.dao.rowmapper;

import com.chrislai.onlineTrade.constant.common.Business;
import com.chrislai.onlineTrade.constant.stock.*;
import com.chrislai.onlineTrade.model.EntrustedOrder;
import com.chrislai.onlineTrade.util.IDGenerator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrustedOrderRowMapper implements RowMapper<EntrustedOrder> {
    @Override
    public EntrustedOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        EntrustedOrder item = new EntrustedOrder();
        item.setId(rs.getString("id"));
        item.setVendorId(rs.getString("vendor_id"));
        item.setStockId(rs.getString("stock_id"));
        item.setUserAccountNumber(rs.getString("user_account_number"));
        item.setUserName(rs.getString("user_name"));
        item.setTradeType(EntrustedStockTradeType.valueOf(EntrustedStockTradeType.getNameByValue(rs.getInt("trade_type"))));
        item.setStockType(EntrustedStockType.valueOf(EntrustedStockType.getNameByValue(rs.getInt("stock_type"))));
        item.setStockCondition(EntrustedStockCondition.valueOf(EntrustedStockCondition.getNameByValue(rs.getInt("stock_condition"))));
        item.setPriceType(EntrustedStockPriceType.valueOf(EntrustedStockPriceType.getNameByValue(rs.getInt("price_type"))));
        item.setStatus(EntrustedStockStatus.valueOf(EntrustedStockStatus.getNameByValue(rs.getInt("status"))));
        item.setBusiness(Business.valueOf(Business.getNameByValue(rs.getInt("status"))));
        item.setEntrustedQty(rs.getLong("entrusted_qty"));
        item.setEntrustedPrice(rs.getBigDecimal("entrusted_price"));
        item.setRemark(rs.getString("remark"));
        //太多欄位有一些就先不寫了
        return item;
    }
}
