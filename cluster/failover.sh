#!/bin/bash

VIP="172.0.0.10"
DEV="eth0"

health_check() {
  arping -q -c 1 -I $DEV $VIP # VIPの割り当て確認
  return $?
}

ip_failover() {
  ip addr add $VIP/24 dev $DEV
  arping -q -A -I $DEV -c 1 $VIP # VIPを自身に割り当てる
}

while health_check; do
  echo "health check ok!"
  sleep 1
done

echo "failover!"

ip_failover
