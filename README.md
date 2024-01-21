# 🛒 Awesome Commerce

This project involves designing and implementing a microservices-based 
e-commerce application for a rapidly expanding online retail company. 
The objectives include achieving scalability, maintainability, and reliability 
to handle a large number of concurrent users. The solution is Dockerized 
for easy deployment in a Kubernetes environment, and an API Gateway manages 
requests. The approach combines microservices architecture, Docker 
containerization, Kubernetes orchestration, and an API Gateway to create 
a cutting-edge e-commerce platform tailored for the challenges of a growing 
online retail landscape.

This project has a Eureka Server to discover all services, an API Gateway to do 
load balancing, routing, and request transformation. It also has an order service, 
a product service, a stock service, a user service to handle all the business.


## 🔍 System Architecture Diagram

![SystemArchitectureDiagram](https://i.ibb.co/TrvLDrq/Screenshot-at-Jan-22-00-49-07.png)

## 🛠 Technology Stacks

Java 21, Spring Boot 3, PostgreSQL, Redis, Kafka, Docker, Swagger, Keycloak, 
Redpanda, Prometheus, Grafana, Redis-Insight

## Project Tree

```
📦 awesome-commerce
.github
   |-- workflows
   |   |-- build.yml
.gitignore
README.md
commands.txt
compose.yaml
eureka-server
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- eurekaserver
   |   |   |   |   |   |   |-- EurekaServerApplication.java
   |   |   |-- resources
   |   |   |   |-- application.properties
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- eurekaserver
   |   |   |   |   |   |   |-- EurekaServerApplicationTests.java
gateway
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- grafana
   |   |-- provisioning
   |   |   |-- datasources.yml
   |-- prometheus
   |   |-- prometheus.yml
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- gateway
   |   |   |   |   |   |   |-- GatewayApplication.java
   |   |   |-- resources
   |   |   |   |-- application-dev.yml
   |   |   |   |-- application-test.yml
   |   |   |   |-- application.yml
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- gateway
   |   |   |   |   |   |   |-- GatewayApplicationTests.java
kompose
   |-- deployment.yaml
   |-- eureka-server-deployment.yaml
   |-- eureka-server-service.yaml
   |-- gateway-deployment.yaml
   |-- gateway-service.yaml
   |-- grafana-claim0-persistentvolumeclaim.yaml
   |-- grafana-deployment.yaml
   |-- grafana-service.yaml
   |-- kafka-deployment.yaml
   |-- kafka-service.yaml
   |-- keycloak-db-deployment.yaml
   |-- keycloak-db-service.yaml
   |-- keycloak-deployment.yaml
   |-- keycloak-service.yaml
   |-- order-db-deployment.yaml
   |-- order-db-service.yaml
   |-- order-service-deployment.yaml
   |-- order-service-service.yaml
   |-- product-db-deployment.yaml
   |-- product-db-service.yaml
   |-- product-service-deployment.yaml
   |-- product-service-service.yaml
   |-- prometheus-claim0-persistentvolumeclaim.yaml
   |-- prometheus-data-persistentvolumeclaim.yaml
   |-- prometheus-deployment.yaml
   |-- prometheus-service.yaml
   |-- redis-deployment.yaml
   |-- redis-insight-deployment.yaml
   |-- redis-insight-service.yaml
   |-- redis-service.yaml
   |-- redpanda-deployment.yaml
   |-- redpanda-service.yaml
   |-- stock-db-deployment.yaml
   |-- stock-db-service.yaml
   |-- stock-service-deployment.yaml
   |-- stock-service-service.yaml
   |-- user-db-deployment.yaml
   |-- user-db-service.yaml
   |-- user-service-deployment.yaml
   |-- user-service-service.yaml
order-service
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- order
   |   |   |   |   |   |   |-- OrderServiceApplication.java
   |   |   |   |   |   |   |-- client
   |   |   |   |   |   |   |   |-- StockServiceClient.java
   |   |   |   |   |   |   |   |-- model
   |   |   |   |   |   |   |   |   |-- QuantityType.java
   |   |   |   |   |   |   |   |   |-- StockDTO.java
   |   |   |   |   |   |   |-- config
   |   |   |   |   |   |   |   |-- KafkaConsumerConfig.java
   |   |   |   |   |   |   |   |-- KafkaProducerConfig.java
   |   |   |   |   |   |   |   |-- RedisConfig.java
   |   |   |   |   |   |   |-- controller
   |   |   |   |   |   |   |   |-- OrderController.java
   |   |   |   |   |   |   |-- dao
   |   |   |   |   |   |   |   |-- OrderRepository.java
   |   |   |   |   |   |   |-- mapper
   |   |   |   |   |   |   |   |-- CoreMapper.java
   |   |   |   |   |   |   |-- messaging
   |   |   |   |   |   |   |   |-- consumer
   |   |   |   |   |   |   |   |   |-- OrderStatusUpdateConsumer.java
   |   |   |   |   |   |   |   |   |-- StockUpdateFailedConsumer.java
   |   |   |   |   |   |   |   |-- message
   |   |   |   |   |   |   |   |   |-- OrderCancelledMessage.java
   |   |   |   |   |   |   |   |   |-- OrderCreatedMessage.java
   |   |   |   |   |   |   |   |   |-- OrderStatusUpdateMessage.java
   |   |   |   |   |   |   |   |   |-- OrderUpdatedMessage.java
   |   |   |   |   |   |   |   |   |-- StockUpdateFailedMessage.java
   |   |   |   |   |   |   |   |-- producer
   |   |   |   |   |   |   |   |   |-- OrderProducer.java
   |   |   |   |   |   |   |-- model
   |   |   |   |   |   |   |   |-- dto
   |   |   |   |   |   |   |   |   |-- OrderDTO.java
   |   |   |   |   |   |   |   |   |-- OrderItemDTO.java
   |   |   |   |   |   |   |   |   |-- ProductDTO.java
   |   |   |   |   |   |   |   |-- entity
   |   |   |   |   |   |   |   |   |-- BaseEntity.java
   |   |   |   |   |   |   |   |   |-- Order.java
   |   |   |   |   |   |   |   |   |-- OrderItem.java
   |   |   |   |   |   |   |   |-- enums
   |   |   |   |   |   |   |   |   |-- OrderStatus.java
   |   |   |   |   |   |   |-- service
   |   |   |   |   |   |   |   |-- LockService.java
   |   |   |   |   |   |   |   |-- OrderService.java
   |   |   |   |   |   |   |   |-- OrderServiceImpl.java
   |   |   |-- resources
   |   |   |   |-- application-dev.yml
   |   |   |   |-- application-test.yml
   |   |   |   |-- application.yml
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- order
   |   |   |   |   |   |   |-- OrderServiceApplicationTests.java
product-service
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- product
   |   |   |   |   |   |   |-- ProductServiceApplication.java
   |   |   |   |   |   |   |-- config
   |   |   |   |   |   |   |   |-- AnalysisConfigurer.java
   |   |   |   |   |   |   |-- controller
   |   |   |   |   |   |   |   |-- ProductController.java
   |   |   |   |   |   |   |-- dao
   |   |   |   |   |   |   |   |-- ProductRepository.java
   |   |   |   |   |   |   |-- exception
   |   |   |   |   |   |   |   |-- GlobalExceptionHandler.java
   |   |   |   |   |   |   |   |-- ProductAlreadyExistsException.java
   |   |   |   |   |   |   |   |-- ProductNotFoundException.java
   |   |   |   |   |   |   |-- mapper
   |   |   |   |   |   |   |   |-- CoreMapper.java
   |   |   |   |   |   |   |-- model
   |   |   |   |   |   |   |   |-- dto
   |   |   |   |   |   |   |   |   |-- ProductDto.java
   |   |   |   |   |   |   |   |-- entity
   |   |   |   |   |   |   |   |   |-- BaseEntity.java
   |   |   |   |   |   |   |   |   |-- Product.java
   |   |   |   |   |   |   |   |-- enums
   |   |   |   |   |   |   |   |   |-- QuantityType.java
   |   |   |   |   |   |   |-- service
   |   |   |   |   |   |   |   |-- ProductService.java
   |   |   |   |   |   |   |   |-- ProductServiceImpl.java
   |   |   |-- resources
   |   |   |   |-- application-dev.yml
   |   |   |   |-- application-test.yml
   |   |   |   |-- application.yml
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- product
   |   |   |   |   |   |   |-- service
   |   |   |   |   |   |   |   |-- ProductServiceTest.java
   |   |   |   |   |   |   |   |-- ProductServiceTestUtil.java
stock-service
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- stock
   |   |   |   |   |   |   |-- StockServiceApplication.java
   |   |   |   |   |   |   |-- config
   |   |   |   |   |   |   |   |-- KafkaConsumerConfig.java
   |   |   |   |   |   |   |   |-- KafkaProducerConfig.java
   |   |   |   |   |   |   |   |-- RedisConfig.java
   |   |   |   |   |   |   |-- controller
   |   |   |   |   |   |   |   |-- StockController.java
   |   |   |   |   |   |   |-- dao
   |   |   |   |   |   |   |   |-- StockRepository.java
   |   |   |   |   |   |   |-- exception
   |   |   |   |   |   |   |   |-- GlobalExceptionHandler.java
   |   |   |   |   |   |   |   |-- StockAlreadyExistsException.java
   |   |   |   |   |   |   |   |-- StockNotFoundException.java
   |   |   |   |   |   |   |-- mapper
   |   |   |   |   |   |   |   |-- CoreMapper.java
   |   |   |   |   |   |   |-- messaging
   |   |   |   |   |   |   |   |-- consumer
   |   |   |   |   |   |   |   |   |-- OrderCancelledConsumer.java
   |   |   |   |   |   |   |   |   |-- OrderCreatedConsumer.java
   |   |   |   |   |   |   |   |   |-- OrderUpdatedConsumer.java
   |   |   |   |   |   |   |   |-- message
   |   |   |   |   |   |   |   |   |-- OrderCancelledMessage.java
   |   |   |   |   |   |   |   |   |-- OrderCreatedMessage.java
   |   |   |   |   |   |   |   |   |-- OrderUpdatedMessage.java
   |   |   |   |   |   |   |   |   |-- StockUpdateFailedMessage.java
   |   |   |   |   |   |   |   |-- producer
   |   |   |   |   |   |   |   |   |-- StockProducer.java
   |   |   |   |   |   |   |-- model
   |   |   |   |   |   |   |   |-- dto
   |   |   |   |   |   |   |   |   |-- OrderDTO.java
   |   |   |   |   |   |   |   |   |-- OrderItemDTO.java
   |   |   |   |   |   |   |   |   |-- StockDTO.java
   |   |   |   |   |   |   |   |-- entity
   |   |   |   |   |   |   |   |   |-- BaseEntity.java
   |   |   |   |   |   |   |   |   |-- Stock.java
   |   |   |   |   |   |   |   |-- enums
   |   |   |   |   |   |   |   |   |-- OrderStatus.java
   |   |   |   |   |   |   |   |   |-- QuantityType.java
   |   |   |   |   |   |   |-- service
   |   |   |   |   |   |   |   |-- LockService.java
   |   |   |   |   |   |   |   |-- StockService.java
   |   |   |   |   |   |   |   |-- StockServiceImpl.java
   |   |   |-- resources
   |   |   |   |-- application-dev.yml
   |   |   |   |-- application-test.yml
   |   |   |   |-- application.yml
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- stock
   |   |   |   |   |   |   |-- StockServiceApplicationTests.java
user-service
   |-- .gitignore
   |-- Dockerfile
   |-- build.gradle
   |-- gradle
   |   |-- wrapper
   |   |   |-- gradle-wrapper.jar
   |   |   |-- gradle-wrapper.properties
   |-- gradlew
   |-- gradlew.bat
   |-- settings.gradle
   |-- src
   |   |-- main
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- user
   |   |   |   |   |   |   |-- UserServiceApplication.java
   |   |   |   |   |   |   |-- model
   |   |   |   |   |   |   |   |-- dto
   |   |   |   |   |   |   |   |   |-- UserDTO.java
   |   |   |   |   |   |   |   |-- entity
   |   |   |   |   |   |   |   |   |-- BaseEntity.java
   |   |   |   |   |   |   |   |   |-- User.java
   |   |   |   |   |   |   |   |-- enums
   |   |   |   |   |   |   |   |   |-- UserRole.java
   |   |   |-- resources
   |   |   |   |-- application-dev.yml
   |   |   |   |-- application-test.yml
   |   |   |   |-- application.yml
   |   |-- test
   |   |   |-- java
   |   |   |   |-- com
   |   |   |   |   |-- commerce
   |   |   |   |   |   |-- user
   |   |   |   |   |   |   |-- UserServiceApplicationTests.java```
```

## Run Locally

Clone the project

```bash
  git clone https://github.com/muslumcanozata/awesome-commerce
```

Go to project file

```bash
  cd awesome-commerce
```

Checkout to dev branch

```bash
  git checkout dev
```

Run Docker Compose file
```bash
  docker-compose up -d --build
```

## 📚 References

- [Eureka Server and Discovery Client with Spring Boot 3.1.1](https://seyhmusaydogdu.medium.com/eureka-server-and-discovery-client-with-spring-boot-3-1-1-ece0d9ea5296)
- [API gateway in Spring boot](https://medium.com/@ankithahjpgowda/api-gateway-in-spring-boot-3ea804003021)
- [Kompose](https://kompose.io/getting-started/)
- [Spring Boot 3.0 Search API using Hibernate Search](https://medium.com/@elijahndungu30/spring-boot-3-0-search-api-using-hibernate-search-5fafad506b69)
- [Monitoring Made Simple: Empowering Spring Boot Applications with Prometheus and Grafana](https://medium.com/simform-engineering/revolutionize-monitoring-empowering-spring-boot-applications-with-prometheus-and-grafana-e99c5c7248cf)
- [Spring Cloud — Netflix Eureka](https://acokgungordu.medium.com/spring-cloud-netflix-eureka-857144561d0c/)
- [Auditing with JPA, Hibernate, and Spring Data JPA](https://www.baeldung.com/database-auditing-jpa)


