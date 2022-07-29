# edge-computing-vital-signs-showcase


## Start Apache Geode

```shell
start locator --name=locator
```

```shell
configure pdx --read-serialized=true --disk-store=DEFAULT
```
```shell
start server --name=server1
```

In Apache Geode Gfsh

```shell
create region --name=VitalStat --type=PARTITION
create region --name=VitalAlert --type=PARTITION
create region --name=Vital  --type=REPLICATE
```

# Testing

Processors
```shell
java -jar applications/vital-edge-device-mqtt-app-processor/target/vital-edge-device-mqtt-app-processor-0.0.1-SNAPSHOT.jar
```

Sink

```java
java -jar applications/vital-mqtt-geode-sink/target/vital-mqtt-geode-sink-0.0.1-SNAPSHOT.jar
```

start generate

```shell
java -jar applications/vital-generator-mqtt-source/target/vital-generator-mqtt-source-0.0.1-SNAPSHOT.jar
```

Start Dashboard

```shell
java -jar applications/vital-dashboard-web-app/target/vital-dashboard-web-app-0.0.1-SNAPSHOT.jar --server.port=7000

```

```shell
open http://localhost:7000
```


---------------

Testing

```shell
curl -X 'POST' \
  'http://localhost:7100/vitalStats/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1|heartRate",
  "vitalId": "1",
  "statName": "heartRate",
  "value": 65,
  "label": "17:28:00",
  "timestamp": "2021-12-12T01:17:28.090Z"
}'
```

--------------

Alerts

```shell
java -jar applications/vital-alerts-web-app/target/vital-alerts-web-app-0.0.1-SNAPSHOT.jar --server.port=7001

```

```shell
open http://localhost:7001
```