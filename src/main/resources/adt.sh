#!/bin/sh

#测试java调用shell
cd `dirname $0`
echo `pwd`

#测试登录远程机器进行impala-shell操作
#ssh -T root@kafka05 << eof
#su hdfs;
#impala-shell;
#show databases;
#use adt;
#show tables;
#insert into accesslist select #rowkey,id,service_code,net_ending_ip,net_ending_name,time,net_ending_mac,destination_ip,port,service_type,keyword1,keyword2,keyword3,mac,source_port,net_ending_ipv6,destination_ipv6,keyword11,keyword12,keyword13,keyword14,keyword15,keyword21,keyword22,keyword23,keyword24,keyword25,jd,wd,country_name,vpn1_ip,jd_vpn1,wd_vpn1,country_name_vpn1,vpn2_ip,jd_vpn2,wd_vpn2,country_name_vpn2,vpn3_ip,jd_vpn3,wd_vpn3,country_name_vpn3,cjsj #from accesslist_tmp;
#invalidate metadata;
#eof


#清空数据库表里面的数据
#USER="root"
#PASSWORD=""
#mysql -u$USER -p$PASSWORD -h127.0.0.1 -P28096<< EOF
#use vpn;
#DELETE FROM accesslist_20150402;
#show tables;
#EOF

#sudo kill `cat mysqld.pid` > /dev/null

#if [ $? -eq 0 ];then
 #   echo "kill mysqld success,next umount disk "
  #  umount /dev/sdb1 
#else 
 #   echo "kill mysqld failed"
#fi

exit 0
