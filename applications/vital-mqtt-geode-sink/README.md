## Rough Notes Docker Hub

From the directory with the Dockerfile


```shell
mvn install
cd applications/vital-mqtt-geode-sink
mvn spring-boot:build-image
```

```shell script
docker tag vital-mqtt-geode-sink:0.0.1-SNAPSHOT $DOCKER_HUB_ID/vital-mqtt-geode-sink:0.0.1-SNAPSHOT 

docker login
docker push $DOCKER_HUB_ID/vital-mqtt-geode-sink:0.0.1-SNAPSHOT

```