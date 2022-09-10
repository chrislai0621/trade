package com.chrislai.onlineTrade.service;

/**
 * 委託下單介面
 * 可擴充給股票、期貨、複委託等等...
 * @param <T>Request
 */
public interface IEntrustedTradeService<T> {
    int entrustedOrder(T request);
    void validateLogic(T request) throws Exception;
}
