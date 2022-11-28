#!bin/bash

URL="<presigned-url>"

curl -X PUT --upload-file ./images/test-image.jpg $URL
