# eazyBank-microservices
This is an Banking Service Microservices.
We have three different microservices:
1. Accounts : It provides accounts and customer related information
2. Loans : it provides Customer loans information.
3. Cards : it provides Credit Cards information.

Basic information about this project.
1. Config server to manage configuration for microservices which is hosted in github. Realod of Configuration at runtime when any changes
to properties happens at real time using actuator/cloudbus/RabbitMQ.
2. Eureka server to register and discover microservices along with Spring Cloud load balancer.
3. API Gateway to provide single entry point to all microservices.
4. OAUTH2 Authentication and Authorization using Keycloak.
5. Resillience4j Circuit breaker to handle Fault tolerance in microservices alonf with retry and rateLimiter..
4. Prometheus to metrics monitor all microservices.
5. Grafana to monitor all microservices.
6. Open Telemetry for distributes tracing across different microservices.
7. Loki used for log aggregation.
7. RabbitMQ to communicate between microservices which is replaced by Kafka. Asyncronous communication between microservices.
8. Docker Compose file for each environment default/QA/prod to deploy microservices.
9. Kubernetes manifest files for quick deployment.
10. Helm charts for quick kubernetes deployment.


How to Run the application in local:

1. Deploy the different database containers with the below command.
   a. docker run -p 3306:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql
   b. docker run -p 3306:3306 --name loansdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=loansdb -d mysql
   c. docker run -p 3306:3306 --name cardsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cardsdb -d mysql
   d. docker run -d -p 7080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.1 start-dev
2. Remaining server can be started from intellij. Start the config server following Eureka server, accounts, loans and cards. Finally run the API Gateway server. 

You can also generate the docker images and run the docker images using the docker compose file present inside the docker-compose folder.
Run the below command: docker compose up

The helms charts are ready to be directly deployed to Kubernetes cluster.
