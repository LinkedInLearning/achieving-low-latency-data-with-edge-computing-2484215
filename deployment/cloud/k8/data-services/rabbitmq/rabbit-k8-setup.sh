kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"


kubectl wait pod -l=app.kubernetes.io/name=rabbitmq-cluster-operator --for=condition=Ready --timeout=60s --namespace=rabbitmq-system

kubectl apply -f deployment/cloud/k8/data-services/rabbitmq/rabbitmq.yml

sleep 30

kubectl wait pod -l=statefulset.kubernetes.io/pod-name=rabbitmq-server-0 --for=condition=Ready --timeout=360s

kubectl exec rabbitmq-server-0 -- rabbitmqctl add_user mqtt mqtt
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_permissions  -p / mqtt ".*" ".*" ".*"
kubectl exec rabbitmq-server-0 -- rabbitmqctl set_user_tags mqtt administrator

