package com.chrislai.onlineTrade.job;

import java.util.List;

public interface IEntrustedJob<T> {
    void updateBuyProcessor(T request, Integer limit);

    List<T> getEntrustedData(T request);

    void updateSellProcessor(T request);

    boolean processData(T buyData, List<T> sellData );
}
