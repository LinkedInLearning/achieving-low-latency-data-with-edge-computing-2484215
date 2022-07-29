# Setup

RabbitMQ

```shell
./deployment/cloud/k8/data-services/rabbitmq/rabbit-k8-setup.sh
```

Get EXTERNAL-IP from service name=rabbitmq
```shell
kubectl get services
```

Get RabbitMQ user password/password
```shell
export ruser=`kubectl get secret rabbitmq-default-user -o jsonpath="{.data.username}"| base64 --decode`
export rpwd=`kubectl get secret rabbitmq-default-user -o jsonpath="{.data.password}"| base64 --decode`

echo ""
echo "USER:" $ruser
echo "PASSWORD:" $rpwd
```

```shell
export RABBIT_IP=`kubectl get services | grep rabbitmq | grep LoadBalancer | awk '{ print $4 }'`
echo $RABBIT_IP
```


Replace $RABBIT_IP with IP

```shell
rabbitmqctl -n rabbit set_parameter shovel cloud-shovel  '{"src-protocol": "amqp091", "src-uri": "amqp://", "src-queue": "mqtt-subscription-vital-mqtt-geode-sourceqos1", "dest-protocol": "amqp091", "dest-uri": "amqp://mqtt:mqtt@$RABBIT_IP", "dest-exchange": "amq.topic"}'
```

Setup MQTT

##mqtt-subscription-vital-mqtt-geode-sinkqos1



---------------
GemFire/Geode

```shell
./deployment/cloud/k8/data-services/geode/geode-bitnami.sh
./deployment/cloud/k8/data-services/geode/geode-bitnami-app-setup.sh
```

Deploy Cloud Applications


```shell
kubeclt apply -f deployment/cloud/k8/apps/vital-dashboard-alerts-app
kubeclt apply -f deployment/cloud/k8/apps/vital-mqtt-geode-sink
```

---------


Start Processors

```shell
java -jar applications/vital-edge-device-mqtt-app-processor/target/vital-edge-device-mqtt-app-processor-0.0.1-SNAPSHOT.jar --spring.profiles.active=brittany
```

```shell
open http://localhost:8094
```


Start Generator 

```shell
java -jar applications/vital-generator-mqtt-source/target/vital-generator-mqtt-source-0.0.1-SNAPSHOT.jar  --spring.profiles.active=brittany
```







Get EXTERNAL-IP of vital-dashboard-alerts-app 


```shell
k get services
```

Open browser 

```shell
export APP_IP=`kubectl get services | grep vital-dashboard-alerts-app  | grep LoadBalancer | awk '{ print $4 }'`
echo open http://$APP_IP
```


http://35.243.173.36