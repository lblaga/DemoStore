#!/usr/bin/env bash
SERVER_URL=http://localhost:8989/

curl -XDELETE \
-H "Content-Type: application/json; Charset=UTF-8" \
-H "Accept: application/json; Charset=UTF-8"  \
-H "Cache-Control: no-cache" \
"${SERVER_URL}/products/1"