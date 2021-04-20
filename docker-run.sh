docker pull scenecho/accounting:latest
docker stop accounting
docker rm accounting
docker run \
  -v /app:/app \
  -p 8081:8081 \
  -d \
  --name accounting \
  scenecho/accounting:latest
