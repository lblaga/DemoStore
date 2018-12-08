#!/usr/bin/env bash

SERVER_URL=http://localhost:8989/

curl -XPOST -i \
-H "Content-Type: application/json; Charset=UTF-8" \
-H "Accept: application/json; Charset=UTF-8"  \
-H "Cache-Control: no-cache" \
-d '{"buyerEmail": "buyer@gmail.com", "products" : [{"productId": 4, "amount": 2}, {"productId": 41, "amount": 3}]}' \
"${SERVER_URL}/orders"