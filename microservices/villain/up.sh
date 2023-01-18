eval $(minikube docker-env)
mvn package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/villain:1.1 .
kubectl delete deployment villain
kubectl apply -f kubernetes/
