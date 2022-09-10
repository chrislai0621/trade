package com.chrislai.onlineTrade.constant.stock;

import com.fasterxml.jackson.annotation.JsonValue;

/**
*股票委託條件
*/
public enum EntrustedStockCondition {
    //name的部份，若有多國語系之後可以換成多國語系的key
    ROD(0, "ROD"),
    IOC(1, "IOC"),
    FOK(2, "FOK");
    @JsonValue
    private final int value;
    private final String name;

    EntrustedStockCondition(int value, String name) {
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
        for (EntrustedStockCondition item : EntrustedStockCondition.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (EntrustedStockCondition item : EntrustedStockCondition.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
