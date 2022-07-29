export GEODE_SECURITY_USERNAME=$(kubectl get secret --namespace default cluster1-geode-auth -o jsonpath="{.data.username}" | base64 -d)
export GEODE_SECURITY_PASSWORD=$(kubectl get secret --namespace default cluster1-geode-auth -o jsonpath="{.data.password}" | base64 -d)


kubectl run cluster1-geode-client --restart='Never' --image docker.io/bitnami/geode:1.15.0-debian-11-r0 --env GEODE_SECURITY_USERNAME=$GEODE_SECURITY_USERNAME --env GEODE_SECURITY_PASSWORD=$GEODE_SECURITY_PASSWORD --namespace default --command -- sleep infinity

sleep 5


kubectl  exec -it cluster1-geode-client -- gfsh -e "connect --locator=cluster1-geode-locator.default.svc.cluster.local[10334] --user=$GEODE_SECURITY_USERNAME --password=$GEODE_SECURITY_PASSWORD" -e "create region --name=VitalStat --type=PARTITION"
kubectl  exec -it cluster1-geode-client -- gfsh -e "connect --locator=cluster1-geode-locator.default.svc.cluster.local[10334] --user=$GEODE_SECURITY_USERNAME --password=$GEODE_SECURITY_PASSWORD" -e "create region --name=Vital --type=PARTITION"
kubectl  exec -it cluster1-geode-client -- gfsh -e "connect --locator=cluster1-geode-locator.default.svc.cluster.local[10334] --user=$GEODE_SECURITY_USERNAME --password=$GEODE_SECURITY_PASSWORD" -e "create region --name=VitalOverview --type=PARTITION"
kubectl  exec -it cluster1-geode-client -- gfsh -e "connect --locator=cluster1-geode-locator.default.svc.cluster.local[10334] --user=$GEODE_SECURITY_USERNAME --password=$GEODE_SECURITY_PASSWORD" -e "create region --name=VitalAlert --type=PARTITION"
