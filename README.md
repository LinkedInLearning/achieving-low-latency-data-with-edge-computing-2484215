# edge-computing-vital-signs-showcase


![img.png](docs/images/architecture.jpg)


# Setup
## Start Apache Geode

In [Gfsh](https://gemfire.docs.pivotal.io/gemfire/tools_modules/gfsh/chapter_overview.html)

```shell
start locator --name=locator
configure pdx --read-serialized=true --disk-store=DEFAULT
start server --name=server
```


```shell
create region --name=VitalStat --type=PARTITION
create region --name=VitalAlert --type=PARTITION
create region --name=Vital  --type=REPLICATE
create region --name=VitalOverview --type=REPLICATE
```

## Start Applications

### start applications/vital-generator-source-app

```shell

java -jar applications/vital-generator-geode-source/target/vital-generator-geode-source-0.0.1-SNAPSHOT.jar

```
### start applications/vital-dashboard-web-app

```shell
java -jar applications/vital-dashboard-web-app/target/vital-dashboard-web-app-0.0.1-SNAPSHOT.jar --server.port=7000 
```

```shell
open http://localhost:7000
```

### start applications/vital-alerts-web-app

```shell
java -jar applications/vital-alerts-web-app/target/vital-alerts-web-app-0.0.1-SNAPSHOT.jar 
```


```shell
open http://localhost:8081
```



# Testing


Heart Rate

```shell
curl -X 'PUT' \
  'http://localhost:8080/vitalStats/0' \
  -H 'accept: application/hal+json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "0|user1",
  "vitalId": "0",
  "statName": "heartRate",
  "value": 60,
  "label": "17:28",
  "timestamp": "2021-12-12T01:17:28.090Z"
}'
```

```shell
curl -X 'PUT' \
  'http://localhost:8080/vitalStats/0' \
  -H 'accept: application/hal+json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1|user1",
  "vitalId": "1",
  "statName": "heartRate",
  "value": 75,
  "label": "17:28",
  "timestamp": "2021-12-12T01:17:28.090Z"
}'
```

Body Temperature

```shell
curl -X 'PUT' \
  'http://localhost:8080/vitalStats/0' \
  -H 'accept: application/hal+json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "0|daleWest",
  "vitalId": "0",
  "statName": "bodyTemperature",
  "value": 98,
  "label": "17:28",
  "timestamp": "2021-12-12T01:17:28.090Z"
}'
```


```shell
curl -X 'PUT' \
  'http://localhost:8080/vitalStats/0' \
  -H 'accept: application/hal+json' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1|daleWest",
  "vitalId": "1",
  "statName": "bodyTemperature",
  "value": 50,
  "label": "17:28",
  "timestamp": "2021-12-12T01:17:28.090Z"
}'
```


# K8 Installation


## GemFire on Kubernetes

From the root project directory

```shell
./deployment/cloud/k8/data-services/gemfire/geode-bitnami.sh
```

Configure GemFire

```shell
./deployment/cloud/k8/data-services/gemfire/gf-app-setup.sh
```


## RabbitMQ on Kubernetes

From the root project directory

```shell
./deployment/cloud/k8/data-services/rabbitmq/rabbit-k8-setup.sh
```