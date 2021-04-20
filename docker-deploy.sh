mvn clean package
docker build -t accounting:latest ./
docker tag accounting:latest scenecho/accounting:latest
docker push scenecho/accounting:latest
