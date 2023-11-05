## Initialize Kafka

Go to the infrastructure/docker-compose directory and
run the following commands:

***Important: execute them in order.***

```
docker-compose -f common.yml -f zookeeper.yml up
```

```
docker-compose -f common.yml -f kafka_cluster.yml up
```

```
docker-compose -f common.yml -f init_kafka up
```

You should have three kafka brokers, one kafka manager and one 
schema registry running.