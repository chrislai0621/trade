package com.chrislai.onlineTrade.constant.stock;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 股票委託種類:0現股1融資2融券
 */
public enum EntrustedStockType {
    //name的部份，若有多國語系之後可以換成多國語系的key
    CURRENT_STOCK(0, "現股"),
    FINANCING(1, "融資"),
    SECURITIES_LENDING(2, "融券");
    @JsonValue
    private final int value;
    private final String name;

    EntrustedStockType(int value, String name) {
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
        for (EntrustedStockType item : EntrustedStockType.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (EntrustedStockType item : EntrustedStockType.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
