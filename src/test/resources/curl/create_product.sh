#!/usr/bin/env bash

SERVER_URL=http://localhost:8989/

curl -XPOST -i \
-H "Content-Type: application/json; Charset=UTF-8" \
-H "Accept: application/json; Charset=UTF-8"  \
-H "Cache-Control: no-cache" \
-d '{"name": "Product555", "price": 5.99}' \
"${SERVER_URL}/products"