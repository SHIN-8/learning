#!/bin/sh

aws cognito-idp admin-respond-to-auth-challenge \
--user-pool-id <user_pool_id> \
--client-id <your_client_id> \
--session xxxxxxxxxxxxxxxxxxxxxxxxxx \
--challenge-name NEW_PASSWORD_REQUIRED \
--challenge-responses USERNAME=<username>,NEW_PASSWORD=<password>,userAttributes.name=<username>
