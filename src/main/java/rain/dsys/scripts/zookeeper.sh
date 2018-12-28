docker run --name zk1 --hostname zoo1 -d --env ZOO_MY_ID=1 --env "ZOO_SERVERS=server.1=0.0.0.1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888" zookeeper
docker run --name zk2 --hostname zoo2 -d --env ZOO_MY_ID=2 --env "ZOO_SERVERS=server.1=zoo1:2888:3888 server.2=0.0.0.1:2888:3888 server.3=zoo3:2888:3888" zookeeper
docker run --name zk3 --hostname zoo3 -d --env ZOO_MY_ID=3 --env "ZOO_SERVERS=server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=0.0.0.1:2888:3888" zookeeper



 docker run -d --name=zk1 --net=host 
 -e SERVER_ID=1 
 -e ADDITIONAL_ZOOKEEPER_1=server.1=127.0.0.1:2881:3881 
 -e ADDITIONAL_ZOOKEEPER_2=server.2=127.0.0.1:2882:3882 
 -e ADDITIONAL_ZOOKEEPER_3=server.3=127.0.0.1:2883:3883 
 zookeeper
