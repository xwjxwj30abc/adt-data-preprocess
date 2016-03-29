#!/bin/sh

CUREENT_PASSWD="qweasdzxc"
# 清空数据库表里面的数据
USER="root"
PASSWORD=""
mysql -u$USER -p$PASSWORD -h127.0.0.1 -P28096 << EOF
    use vpn;
    #DELETE FROM accesslist_20150402;
    show tables;
EOF

echo $CURRENT_PASSWD | sudo -S kill `cat mysqld.pid`

#echo $CURRENT_PASSWD | sudo -S rm mysqld.pid

# Remove Disk Device
if [ $? -eq 0 ];then
    echo "kill mysqld success,next umount disk "
    echo $CUREENT_PASSWD | sudo -S umount /dev/sdd1
else
    echo "kill mysqld failed"
fi

exit 0

