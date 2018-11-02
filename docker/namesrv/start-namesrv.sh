#!/bin/bash
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}

[ ! -e "$ROCKETMQ_HOME" ] && ROCKETMQ_HOME=/rocketmq-4.3.0
[ ! -e "$ROCKETMQ_HOME/bin/mqnamesrv" ] && echo 'we need rocketmq home,please set ROCKETMQ_HOME env path'

export ROCKETMQ_HOME

cd $ROCKETMQ_HOME

cp $ROCKETMQ_HOME/conf/namesrv.properties $ROCKETMQ_CONF_PATH

#修改broker.properties配置
#可以通过docker run -d rocker-namesrv listenPort=999 namesrvAddr=127.0.0.1:9876
#动态切换rocketmq broker配置信息文件
for arg in "$@"
do
        e=$arg
        i=$(expr index $e =)
        sp=${e:0:$i}
        cmd="sed -i 's/^#*${sp}.*/$1/g' broker.properties"
        eval $cmd
done

#启动rocketmq
