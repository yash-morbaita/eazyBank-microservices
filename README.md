# eazyBank-microservices
This is an Banking Service Microservices.
We have three different microservices:
1. Accounts
2. Loans
3. Cards

   These services uses Eureka Naming services for service regustry and service discovery and client side load balancing using Spring Cloud Load Balancer and Feign.
The API gateway is a point of entry along with spring security for Authentication and Authorization. These distrubutes system performs asyncronous communication using Kafka.
