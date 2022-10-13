#!/bin/sh

aws cognito-idp admin-initiate-auth \
--user-pool-id <user_pool_id> \
--client-id <your_client_id> \
--auth-flow ADMIN_NO_SRP_AUTH \
--auth-parameters USERNAME=<username>,PASSWORD=<password>
