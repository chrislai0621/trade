package com.chrislai.onlineTrade.constant.stock;

import com.fasterxml.jackson.annotation.JsonValue;

/**
* 股票委託狀態
*/
public enum EntrustedStockStatus {
    //name的部份，若有多國語系之後可以換成多國語系的key
    RESERVING(0, "預約中"),
    ENTRUSTED_SUCCESS(1, "委託成功"),
    ALL_TRADED(2, "全部成交"),
    CHANGE_QTY(3, "改量"),
    CHANGE_PRICE(4, "改價"),
    CANCEL_ORDER(-1, "刪單"),
    ENTRUSTED_FAIRE(-2, "委託失敗");
    @JsonValue
    private final int value;
    private final String name;

    EntrustedStockStatus(int value, String name) {
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
        for (EntrustedStockStatus item : EntrustedStockStatus.values()) {
            if (item.getValue() == value) {
                return item.getName();
            }
        }
        return null;
    }
    public static Integer getValueByName(String name) {
        for (EntrustedStockStatus item : EntrustedStockStatus.values()) {
            if (item.getName().equals(name)) {
                return item.getValue();
            }
        }
        return null;
    }
}
