** Please be patient while the chart is being deployed **

To obtain the credentials to access Apache Geode locators, run:

    export GEODE_SECURITY_USERNAME=$(kubectl get secret --namespace default cluster1-geode-auth -o jsonpath="{.data.username}" | base64 -d)
    export GEODE_SECURITY_PASSWORD=$(kubectl get secret --namespace default cluster1-geode-auth -o jsonpath="{.data.password}" | base64 -d)

Apache Geode locators can be accessed by clients via port 10334 on the following DNS name from within your cluster:

    cluster1-geode-locator.default.svc.cluster.local

Each Apache Geode Cache server can be accessed by clients via port 40404 on the following DNS name(s) from within your cluster:

    cluster1-geode-server-0.cluster1-geode-server-hl.default.svc.cluster.local
    cluster1-geode-server-1.cluster1-geode-server-hl.default.svc.cluster.local
    cluster1-geode-server-2.cluster1-geode-server-hl.default.svc.cluster.local

To create a pod that you can use as a Apache Geode client run the following commands:

    kubectl run cluster1-geode-client --restart='Never' --image docker.io/bitnami/geode:1.15.0-debian-11-r0 --env GEODE_SECURITY_USERNAME=$GEODE_SECURITY_USERNAME --env GEODE_SECURITY_PASSWORD=$GEODE_SECURITY_PASSWORD --namespace default --command -- sleep infinity

Then, you can use it to interact with the Apache Geode cluster:

    kubectl exec --tty -i cluster1-geode-client --namespace default -- bash
    gfsh -e "connect --locator=cluster1-geode-locator.default.svc.cluster.local[10334] --user=$GEODE_SECURITY_USERNAME --password=$GEODE_SECURITY_PASSWORD"

    kubectl exec --it cluster1-geode-client --namespace default --  gfsh 

To connect to your Apache Geode cluster from outside the cluster, perform the following steps:

1.  Create a port-forward to the ports:

    kubectl port-forward --namespace default svc/cluster1-geode-locator 10334:10334 &
    kubectl port-forward --namespace default svc/cluster1-geode-locator 7070:7070 &

2. Access the Locator and Pulse dashboard:

   echo "Locator URL: 127.0.0.1[10334]"
   echo "Pulse URL: http://127.0.0.1:7070/pulse"
