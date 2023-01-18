eval $(minikube docker-env)
mvn package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/hero:1.1 .
kubectl delete deployment hero
kubectl apply -f kubernetes/
