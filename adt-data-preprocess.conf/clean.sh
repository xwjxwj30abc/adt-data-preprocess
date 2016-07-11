#!/bin/sh
export MNTDIR="/tmp/usbdisk"
# Remove Disk Device
if [ -f "mysqld.pid" ];then
    kill `cat mysqld.pid`
    echo "kill mysql"
    rm mysqld.pid
    echo "remove mysqld.pid"
fi

if [ "`ls -A $MNTDIR`" != "" ];then
   umount -l "$MNTDIR"
   echo "umount /tmp/usbdisk"
else 
   echo "the usbdisk path is not exist"
fi

exit 0

