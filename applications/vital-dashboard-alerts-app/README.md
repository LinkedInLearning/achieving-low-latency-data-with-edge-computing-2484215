## Rough Notes Docker Hub

From the directory with the Dockerfile


```shell
mvn install
cd applications/vital-dashboard-alerts-app
mvn spring-boot:build-image
```

```shell script
docker tag vital-dashboard-alerts-app:0.0.1-SNAPSHOT $DOCKER_HUB_ID/vital-dashboard-alerts-app:0.0.1-SNAPSHOT 

docker login
docker push $DOCKER_HUB_ID/vital-dashboard-alerts-app:0.0.1-SNAPSHOT

```