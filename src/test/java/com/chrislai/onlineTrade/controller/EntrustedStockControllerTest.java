package com.chrislai.onlineTrade.controller;

import com.chrislai.onlineTrade.constant.common.Business;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockCondition;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockPriceType;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockTradeType;
import com.chrislai.onlineTrade.constant.stock.EntrustedStockType;
import com.chrislai.onlineTrade.dto.EntrustedOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class EntrustedStockControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    void entrustedOrder() throws Exception {
        EntrustedOrderRequest request = EntrustedOrderRequest.builder()
                .vendorId("123456")
                .userAccountNumber("888-123456")
                .userName("王小明")
                .stockId("2135")
                .business(Business.BUY)
                .entrustedPrice(BigDecimal.valueOf(25.5))
                .entrustedQty(1L)
                .tradeType(EntrustedStockTradeType.WHOLE_SHARE)
                .stockType(EntrustedStockType.CURRENT_STOCK)
                .stockCondition( EntrustedStockCondition.ROD)
                .priceType(EntrustedStockPriceType.LIMIT_PRICE).build();
        String json = objectMapper.writeValueAsString(request);
        System.out.println(json);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/stock/entrustedOrder")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .accept("application/json");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

    }
}