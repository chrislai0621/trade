# trade
- 說明
實作簡易金融線上委託下單系統 
- 包含
1. 委託下單API
1. Thread 先進先出去處理委託的訂單搓合交易(參考EntrustedThreadExecutor此檔案)
- mysql table
```
CREATE TABLE `trade`.`entrusted_order` (
  `id` VARCHAR(32) NOT NULL,
   `trade_no` VARCHAR(100) NULL COMMENT '委託交易號',
  `vendor_id` VARCHAR(32) NOT NULL COMMENT '廠商id',
  `stock_id` VARCHAR(32) NOT NULL COMMENT '股票id',
  `user_account_number` VARCHAR(20) NOT NULL COMMENT 'user 證券帳號',
  `user_name` VARCHAR(32) NOT NULL COMMENT 'user 名字',
  `trade_type` TINYINT NOT NULL COMMENT '股票委託交易類型:0整股、1盤後、2盤後零股',
  `stock_type` TINYINT NOT NULL COMMENT '股票委託種類:0現股1融資2融券',
  `stock_condition` TINYINT NOT NULL COMMENT '股票委託條件:0ROD、1IOC、2FOK',
  `price_type` TINYINT NOT NULL COMMENT '股票委託價格類別:0限價、1市價',
  `status` TINYINT NOT NULL COMMENT '股票委託狀態:0預約中、1委託成功、2全部成交、3改量、4改價、-1刪單、-2委託失敗',
  `business` TINYINT NOT NULL COMMENT '商業條件:0賣、1買',
  `entrusted_qty` INT NOT NULL COMMENT '委託數量',
  `traded_qty` INT NULL COMMENT '成交數量',
  `canceled_qty` INT NULL COMMENT '取消數量',
  `entrusted_price` DECIMAL NOT NULL COMMENT '委託金額',
  `traded_price` DECIMAL NULL COMMENT '成交價格',
  `fee` DECIMAL NULL COMMENT '成交手續費',
  `processor` VARCHAR(50) NULL COMMENT '執行緒id',
  `remark` VARCHAR(200) NULL COMMENT '備註',
  `entrusted_timestamp` BIGINT NOT NULL COMMENT '委託日期時間TIMESTAMP',
  `traded_timestamp` BIGINT NULL COMMENT '成交日期時間TIMESTAMP',
  `canceled_timestamp` BIGINT NULL COMMENT '取消日期時間TIMESTAMP',
  `created_timestamp` BIGINT NOT NULL COMMENT '建立日期時間TIMESTAMP',
  `update_timestamp` BIGINT NULL COMMENT '更新時間TIMESTAMP',
  `created_user` VARCHAR(32) NOT NULL COMMENT '建立人員',
  `updated_user` VARCHAR(32)NULL COMMENT '修改人員',
  PRIMARY KEY (`id`))
COMMENT = '股票委託交易訂單';
```

- API: http://127.0.0.1:8080/stock/entrustedOrder
- 規格說明:

 ```
參數說明	型態	必填	說明	範例
vendorId 	String	Y	廠商id	123456
userAccountNumber	String	Y	user 證券帳號	888-123456
userName	String	Y	user 名字	王小明
stockId	String	Y	股票id	2135
business	int	Y	商業條件:0賣、1買	1
entrustedPrice	Decimal	Y	委託金額	25
entrustedQty	Int	Y	委託數量	1
tradeType	Int	Y	股票委託交易類型:0整股、1盤後、2盤後零股	
stockType	Int	Y	股票委託種類:0現股1融資2融券	
stockCondition	Int	Y	股票委託條件:0  ROD、1 IOC、2 FOK	
priceType	Int	Y	股票委託價格類別:0限價、1市價	
remark	String	N	備註	null
```

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
status 201 data Success
status 404 data Fail
```
