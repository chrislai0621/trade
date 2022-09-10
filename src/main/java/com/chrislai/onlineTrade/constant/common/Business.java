package com.chrislai.onlineTrade.constant.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 商業:賣、買
 */
public enum Business {
    SELL(0, "賣"),
    BUY(1, "買");
    @JsonValue
    private final int value;
    private final String name;

    Business(int value, String name) {
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
        for (Business item : Business.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (Business item : Business.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
