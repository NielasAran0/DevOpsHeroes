eval $(minikube docker-env)
mvn -e package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/fight:1.1 .
kubectl delete deployment fight
kubectl apply -f kubernetes/
minikube service fight --url
