# Smart Service Finder (SSF) 🚀

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Microservices-blue)
![React](https://img.shields.io/badge/React-TypeScript-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Event_Driven-black)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![License](https://img.shields.io/badge/License-MIT-green)

## 🌟 Overview

**Smart Service Finder (SSF)** is a full-stack service marketplace platform built using a **Microservices Architecture**. The platform connects customers with service providers, enabling users to discover local services, book providers, manage bookings, submit reviews, and receive real-time notifications.

The project demonstrates modern backend development practices using **Spring Boot Microservices**, **Spring Cloud**, **Kafka**, **Docker**, **API Gateway**, **Service Discovery**, and **Centralized Configuration Management**, combined with a responsive frontend built using **React, TypeScript, Vite, and Bootstrap**.

---

## 🔗 Live Demo & Links

### Frontend Application

https://smart-service-finder-frontend.vercel.app/

### Backend Repository

https://github.com/RajdeepSinghChouhan/Smart-Service-finder-MICROSERVICES

### Docker Hub

https://hub.docker.com/repositories/rajdeepchouhan

### LinkedIn

https://www.linkedin.com/in/rajdeep-chouhan-5ab5a2328/

---

## 🎯 Problem Statement

Finding reliable local service providers often involves searching across multiple platforms, making calls, and comparing options manually.

Smart Service Finder simplifies this process by providing a centralized platform where:

* Customers can search and book services.
* Service providers can manage their offerings.
* Reviews help users make informed decisions.
* Notifications keep users updated on booking activities.
* The system remains scalable through microservices.

---

## 🏗️ System Architecture

```text
                                      ┌─────────────────┐
                                      │     Client      │
                                      │ React Frontend  │
                                      └────────┬────────┘
                                               │
                                               ▼
                                      ┌─────────────────┐
                                      │   API Gateway   │
                                      └────────┬────────┘
                                               │
                 ┌─────────────────────────────┼─────────────────────────────┐
                 ▼                             ▼                             ▼

       ┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
       │   Auth Service  │         │  User Service   │         │ Provider Service│
       └─────────────────┘         └─────────────────┘         └─────────────────┘

                 ▼                             ▼                             ▼

       ┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
       │ Service Catalog │         │ Booking Service │         │ Review Service  │
       └─────────────────┘         └─────────────────┘         └─────────────────┘

                                               │
                                               ▼
                                      ┌─────────────────┐
                                      │ Kafka Messaging │
                                      └────────┬────────┘
                                               ▼
                                      ┌─────────────────┐
                                      │ Notification API│
                                      └─────────────────┘


          All Services Registered in Eureka Server
          Configuration Managed by Config Server
          Data Stored in MySQL Database
```

---

## ⚙️ Microservices Included

| Service              | Responsibility             |
| -------------------- | -------------------------- |
| Eureka Server        | Service Discovery          |
| Config Server        | Centralized Configuration  |
| API Gateway          | Routing & Security         |
| Auth Service         | Authentication & JWT       |
| User Service         | User Management            |
| Provider Service     | Provider Management        |
| All Services API     | Service Catalog & Search   |
| Booking Service      | Booking Operations         |
| Review Service       | Ratings & Reviews          |
| Notification Service | Notification Management    |
| Kafka                | Event-Driven Communication |

---

## ✨ Key Features

### Customer Features

* User Registration & Login
* JWT Authentication
* Search Services by Category
* Search Providers
* Book Service Providers
* View Booking History
* Manage User Profile
* Receive Notifications
* Submit Reviews & Ratings

### Provider Features

* Provider Registration
* Service Listing Management
* Booking Management
* Availability Tracking
* Profile Management

### Technical Features

* Microservices Architecture
* API Gateway Pattern
* Eureka Service Discovery
* Spring Cloud Config Server
* Kafka Event Streaming
* Resilience4j Circuit Breaker
* Dockerized Deployment
* Centralized Configuration
* Fault Tolerance
* Secure JWT Authentication

---

## 🛠️ Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Spring Cloud Config
* Eureka Discovery Server
* OpenFeign
* Apache Kafka
* Resilience4j
* Maven

### Frontend

* React
* TypeScript
* Vite
* Bootstrap 5
* Axios
* React Router
* Context API

### Database

* MySQL

### DevOps & Tools

* Docker
* Docker Compose
* GitHub
* Docker Hub
* Postman

---

## 📂 Project Structure

```text
Smart-Service-finder-MICROSERVICES

├── 01-Eureka-Server
├── 02-API-Gateway
├── 03-Auth-API
├── 04-User-Service-API
├── 05-Provider-Service-API
├── 06-All-Service-API
├── 07-Booking-API
├── 08-Review-API
├── 09-Notification-API
├── 10-Config-Server
│
├── docker-compose.yml
├── .env
└── README.md
```

### Frontend Repository

```text
src

├── components
├── pages
├── services
├── context
├── routes
├── hooks
├── assets
├── utils
└── types
```

---

## 🔐 Authentication Flow

```text
User Login
    │
    ▼
Auth Service
    │
    ▼
JWT Token Generated
    │
    ▼
Frontend Stores Token
    │
    ▼
API Gateway Validation
    │
    ▼
Authorized Microservice Access
```

---

## 🔔 Event-Driven Notification Flow

```text
Booking Created / Updated
          │
          ▼
     Kafka Producer
          │
          ▼
      Kafka Topic
          │
          ▼
 Notification Service
          │
          ▼
 Notification Stored
          │
          ▼
 Frontend Notification Panel
```

---

## 🚀 Running the Project

### Clone Repository

```bash
git clone https://github.com/RajdeepSinghChouhan/Smart-Service-finder-MICROSERVICES.git

cd Smart-Service-finder-MICROSERVICES
```

### Configure Environment Variables

Create a `.env` file and add required environment variables.

### Start Application

```bash
docker compose up -d
```

### Verify Running Containers

```bash
docker ps
```

---

## 📈 Future Enhancements

* Kubernetes Deployment
* CI/CD Pipeline using Jenkins
* Monitoring with Prometheus & Grafana
* Distributed Tracing with Zipkin
* Redis Caching
* Payment Gateway Integration
* Real-Time Chat System
* Elasticsearch Based Search
* AI-Powered Provider Recommendations

---

# LIVE Link 

https://smart-service-finder-frontend.vercel.app/

## 👨‍💻 Author

### Rajdeep Chouhan

Computer Science Engineering Student | Java Full Stack Developer

📍 Dewas, Madhya Pradesh, India

### Connect With Me

LinkedIn:
https://www.linkedin.com/in/rajdeep-chouhan-5ab5a2328/

GitHub:
https://github.com/RajdeepSinghChouhan

---

## ⭐ Support

If you found this project useful, please consider giving the repository a **Star ⭐**.

It helps showcase the project and motivates future improvements.
