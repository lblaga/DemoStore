#!/usr/bin/env bash

SERVER_URL=http://localhost:8989/

curl -XGET \
-H "Content-Type: application/json; Charset=UTF-8" \
-H "Accept: application/json; Charset=UTF-8"  \
-H "Cache-Control: no-cache" \
"${SERVER_URL}/orders/search?from=2018-12-06&to=2018-12-31"