eval $(minikube docker-env)
cd first-service
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/first-service-jvm:1.1 .
kubectl delete deployment first-service
kubectl apply -f kubernetes/

cd ../second-service
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t workshop/second-service-jvm:latest .
kubectl delete deployment second-service
kubectl apply -f kubernetes/

minikube service first-service --url
