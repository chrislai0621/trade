# trade
- 說明
實作簡易金融線上委託下單系統 
- 包含
1. 委託下單API
1. Thread 先進先出去處理委託的訂單搓合交易(參考EntrustedThreadExecutor此檔案)


- API: http://127.0.0.1:8081/stock/entrustedOrder
- 規格說明

![image](https://user-images.githubusercontent.com/32324347/188313039-f19e980e-9e7a-4201-9297-8f2245968141.png)


request sample

```
{
"vendorId":"123456",
"userAccountNumber":"888-123456",
"userName":"王小明",
"stockId":"2135",
"business":1,
"entrustedPrice":25,
"entrustedQty":1,
"tradeType":0,
"stockType":0,
"stockCondition":0,
"priceType":0,
"remark":null
}
```

response
```
Success
```
