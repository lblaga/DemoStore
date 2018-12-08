#!/usr/bin/env bash

SERVER_URL=http://localhost:8989/

curl -XPUT -i \
-H "Content-Type: application/json; Charset=UTF-8" \
-H "Accept: application/json; Charset=UTF-8"  \
-H "Cache-Control: no-cache" \
-d '{"name": "Product2b", "price": 6.89}' \
"${SERVER_URL}/products/2"