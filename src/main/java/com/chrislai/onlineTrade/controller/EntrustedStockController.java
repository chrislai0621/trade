package com.chrislai.onlineTrade.controller;


import com.chrislai.onlineTrade.dto.EntrustedOrderRequest;
import com.chrislai.onlineTrade.service.IEntrustedTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class EntrustedStockController {
    private final IEntrustedTradeService entrustedTradeService;

    /**
     * 股票委話下單
     * @param request EntrustedOrderRequest
     * @return Success/Fail/Error message
     */
    @PostMapping("/entrustedOrder")
    public ResponseEntity<String> entrustedOrder(@RequestBody @Valid EntrustedOrderRequest request) {
        try {
            entrustedTradeService.validateLogic(request);
            //可以改設計回傳是ID或者是OBJECT，後面單元測試比較能測傳進去跟查到的值，是否一樣
            int count = entrustedTradeService.entrustedOrder(request);
            //可再優化成自己自定義的Exception透過ExceptionHandler回傳自定義的response
            if(count > 0)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body("Success");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
