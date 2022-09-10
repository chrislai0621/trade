package com.chrislai.onlineTrade.model;

import com.chrislai.onlineTrade.constant.common.Business;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BaseEntrusted {
    protected String id;
    /**
     * 廠商id
     * */
    protected String vendorId;
    /**
     * user 證券帳號
     * */
    protected String userAccountNumber;
    /**
     * user 名字
     * */
    protected String userName;
    /**
     *商業條件
     */
    protected Business business;
    /**
     * 委託數量
     * */
    protected Long entrustedQty;
    /**
     * 成交數量
     */
    protected Long tradedQty;
    /**
     * 取消數量
     */
    protected Long canceledQty;
    /**
     * 委託價格
     */
    protected BigDecimal entrustedPrice;
    /**
     * 成交價格
     */
    protected BigDecimal tradedPrice;
    /**
     * 成交手續費
     */
    protected BigDecimal fee;
    /**
     * 執行續id
     * */
    protected String processor;
    /**
     * 備註
     * */
    protected String remark;
    /**
     * 委託日期時間TIMESTAMP
     */
    protected Long entrustedTimestamp;
    /**
     * 成交日期時間TIMESTAMP
     */
    protected Long tradedTimestamp;
    /**
     * 取消日期時間TIMESTAMP
     */
    protected Long canceledTimestamp;
    /**
     * 建立日期時間TIMESTAMP
     */
    protected Long createdTimestamp;
    /**
     * 更新日期時間TIMESTAMP
     */
    protected Long updatedTimestamp;
    /**
     * 建立人員
     */
    protected String createdUser;
    /**
     * 修改人員
     */
    protected String updatedUser;

}
