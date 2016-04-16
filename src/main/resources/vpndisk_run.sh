#!/bin/sh

export SD=`ls /dev | grep 'sd.1' | grep -v "sda1"
export DEVNAME="/dev/$SD"
export MNTDIR="/tmp/usbdisk"
export DSYSUSER=`grep ':x:2:' /etc/passwd | cut -d':' -f 1`

USER_ID=`id -u`
if [ "$USER_ID" != "0" ]; then
    echo "need root privilege."
    exit 1
fi

if [ ! -b "$DEVNAME" ]; then
    echo "the USB disk device not exist."
    exit 1
fi

fsck -y "$DEVNAME"
mkdir -p "$MNTDIR"
mount -t ext3 "$DEVNAME" "$MNTDIR"

if [ ! -f "$MNTDIR"/usr/sbin/mysqld ]; then
    echo "mysql server file not exist."
    exit 1
fi

cp -a -f "$TOOLDIR"/noroot "$MNTDIR"/usr/sbin/
chroot "$MNTDIR"  /usr/sbin/mysqld --skip-grant-tables 2>&1 > /tmp/usbdb.log &
# wait the db process start
echo $! > mysqld.pid
sleep 5

# no root password, it is empty.
#mysql -u root -p -h 127.0.0.1 -P 28096

#killall mysqld
#umount -l "$MNTDIR"

exit 0
