package com.chrislai.onlineTrade.constant.stock;

import com.fasterxml.jackson.annotation.JsonValue;

/**
*股票委託價格類別:限價、市價
*/
public enum EntrustedStockPriceType {
    LIMIT_PRICE(0, "限價"),
    MARKET_PRICE(1, "市價");
    @JsonValue
    private final int value;
    private final String name;

    EntrustedStockPriceType(int value, String name) {
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
        for (EntrustedStockPriceType item : EntrustedStockPriceType.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (EntrustedStockPriceType item : EntrustedStockPriceType.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
