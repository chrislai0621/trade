package com.chrislai.onlineTrade.util;
import java.util.UUID;

public class IDGenerator {
    public static String uuid() {
        return uuid16();
    }
    public static String uuid16() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toLowerCase();
    }
}
