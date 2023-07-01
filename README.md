# Biding distributed application using Spring Boot, React, PostgreSQL and Kafka

This application is a humble copy of the e-bay system, where a user can post different products that have a starting price, and have other users bid for it. 
The prices are updated in real-time so the user doesn't need to refresh the page to constantly see the actual bidding prices. This feature was implemented using web sockets.

The application consists of two distributes servers: a main server, where the application's logic is implemented, and a server dedicated to the products whose bidding time has expired. 
The communication between these two servers is made with Kafka.

## Technologies used:
- Spring Boot v2.7.5 with Java 11 for building the backend (server) and the REST API (including JWT authentication and authorization)
- React JS for building the frontend (client)
- PostgreSQL for the two databases 
- Web sockets for implementing the real-time bidding system for products
- Kafka for sending information about expired products to another server, with its own database
