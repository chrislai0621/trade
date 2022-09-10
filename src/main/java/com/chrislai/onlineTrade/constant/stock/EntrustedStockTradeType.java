package com.chrislai.onlineTrade.constant.stock;

import com.fasterxml.jackson.annotation.JsonValue;

/**
*股票委託交易類型 整股、盤後、盤後零股
 * */
public enum EntrustedStockTradeType {
    //name的部份，若有多國語系之後可以換成多國語系的key
    WHOLE_SHARE(0, "整股"),
    AFTER_HOURS(1, "盤後"),
    AFTER_HOURS_FRACTIONAL_SHARES(2, "盤後零股");
    @JsonValue
    private final int value;
    private final String name;

    EntrustedStockTradeType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }
    public static String getNameByValue(Integer value) {
        for (EntrustedStockTradeType item : EntrustedStockTradeType.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (EntrustedStockTradeType item : EntrustedStockTradeType.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
