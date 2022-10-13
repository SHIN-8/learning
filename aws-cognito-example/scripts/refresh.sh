#!/bin/sh

aws cognito-idp admin-initiate-auth \
--user-pool-id <your-user-pool-id> \
--client-id <your-client-id> \
--auth-flow REFRESH_TOKEN_AUTH \
--auth-parameters \
REFRESH_TOKEN=<your-refresh-token>
