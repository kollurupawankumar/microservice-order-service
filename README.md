# microservice-order-service

This service calls the product service and stores the data in MongoDB.

This is a sample order microservice. This does the following tasks.
1. Register with the eureka server (HA)
2. Use the microservice-product-service to get some info. Communication between the microservices is done.
3. Load Balancing 
4. Establishing the security -- Inprogress

How to run?
---------------------------------
1. Create a jar using mvn clean package
2. run java -jar target/order-service-0.0.1-SNAPSHOT.jar
3. Services will be up and running.



