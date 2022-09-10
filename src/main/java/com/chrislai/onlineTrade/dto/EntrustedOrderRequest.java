package com.chrislai.onlineTrade.dto;

import com.chrislai.onlineTrade.constant.common.Business;
import com.chrislai.onlineTrade.constant.stock.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Builder
@Getter
@Setter
/**
 * 股票委託訂單
 */
public class EntrustedOrderRequest {
     /**
     * 廠商id
     * */
    @NotBlank
    private String vendorId;
    /**
     * user 證券帳號
     * */
    @NotBlank
    private String userAccountNumber;
    /**
     * user 名字
     * */
    @NotBlank
    private String userName;
    /**
     * 股票id
     */
    @NotBlank
    private String stockId;
    /**
     *商業條件
     */
    @NotNull
    private Business business;
    /**
     * 委託價格
     */
    @NotNull
    protected BigDecimal entrustedPrice;
    /**
     * 委託數量
     * */
    @NotNull
    private Long entrustedQty;

    /**
     * 交易類型
     */
    @NotNull
    private EntrustedStockTradeType tradeType;
    /**
     * 種類
     */
    @NotNull
    private EntrustedStockType stockType;
    /**
     *條件
     */
    @NotNull
    private EntrustedStockCondition stockCondition;
    /**
     *價格類別
     */
    @NotNull
    private EntrustedStockPriceType priceType;
    /**
     * 備註
     * */
    private String remark;


}
