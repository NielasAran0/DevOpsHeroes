eval $(minikube docker-env)
mvn package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/supes-monolith .
kubectl delete deployment supes-monolith
kubectl apply -f kubernetes/
minikube service supes-monolith --url
