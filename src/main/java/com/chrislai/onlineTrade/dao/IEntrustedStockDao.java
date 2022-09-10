package com.chrislai.onlineTrade.dao;

import com.chrislai.onlineTrade.dto.EntrustedOrderRequest;
import com.chrislai.onlineTrade.model.EntrustedOrder;

import java.util.List;


public interface IEntrustedStockDao {
    int entrustedOrder(EntrustedOrderRequest request);
    List<EntrustedOrder> getEntrustedOrder(EntrustedOrder request);
}
