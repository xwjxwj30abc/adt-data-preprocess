#!/bin/sh

CUREENT_PASSWD="qweasdzxc"
export MNTDIR="/tmp/usbdisk"
# 清空数据库表里面的数据
USER="root"
PASSWORD=""
mysql -u$USER -p$PASSWORD -h127.0.0.1 -P28096 << EOF
    use vpn;
    #DELETE FROM AccessList;
    show tables;
EOF

echo $CURRENT_PASSWD | sudo -S kill `cat mysqld.pid`

# Remove Disk Device
if [ $? -eq 0 ];then
    echo "kill mysqld success,next umount disk "
    echo $CUREENT_PASSWD | sudo -S umount -l "$MNTDIR"
else
    echo "kill mysqld failed"
    exit 1
fi

echo $CURRENT_PASSWD | sudo -S rm mysqld.pid

exit 0

