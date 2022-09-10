package com.chrislai.onlineTrade.model;

import com.chrislai.onlineTrade.constant.stock.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 股票委託訂單
 */
public class EntrustedOrder extends BaseEntrusted {

    /**
     * 股票id
     */
    private String stockId;
    /**
     * 交易類型
     */
    private EntrustedStockTradeType tradeType;
    /**
     * 種類
     */
    private EntrustedStockType stockType;
    /**
     *條件
     */
    private EntrustedStockCondition stockCondition;
    /**
     *價格類別
     */
    private EntrustedStockPriceType priceType;

    /**
     *狀態
     */
    private EntrustedStockStatus status;


}
