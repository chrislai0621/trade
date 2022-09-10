INSERT INTO entrusted_order(id,vendor_id,stock_id,user_account_number,user_name,trade_type,stock_type,stock_condition,price_type,
                status,business,entrusted_qty,entrusted_price,remark,entrusted_timestamp,
                created_timestamp,created_user)
                VALUES(REPLACE(UUID(), '-', ''),REPLACE(UUID(), '-', ''),'1234','888-123456',
                '陳小華',0,0,0,0,2,1,1000,25.5,null,1662362448000,1662362448000,'SYSTEM');