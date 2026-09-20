# 🚛 LogiFleet – Commercial Fleet Telematics & Vehicle Maintenance Platform

## 📌 Project Overview

LogiFleet is a microservices-based fleet management platform designed for commercial logistics operations.

The system helps organizations manage vehicles, dispatch trips, track trip progress, monitor vehicle availability, and handle vehicle maintenance operations through independent and scalable microservices.

---

## 🎯 Problem Statement

Commercial fleet operations involve multiple activities such as:

- Vehicle registration and management
- Vehicle availability tracking
- Trip assignment and dispatch
- Driver trip progress tracking
- Vehicle maintenance management
- Repair and maintenance history
- Secure access to fleet APIs

Managing these operations in a single tightly coupled application can make the system difficult to maintain and scale.

LogiFleet addresses this problem using a **Microservices Architecture**.

---

## 🏗️ System Architecture

```text
                         ┌─────────────────┐
                         │     Client      │
                         │  Postman/Talend │
                         └────────┬────────┘
                                  │
                                  ▼
                       ┌────────────────────┐
                       │    API Gateway     │
                       │      :8080         │
                       │ JWT Authentication │
                       └─────────┬──────────┘
                                 │
                 ┌───────────────┼────────────────┐
                 │               │                │
                 ▼               ▼                ▼
        ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
        │   Vehicle   │  │    Trip     │  │ Maintenance │
        │   Service   │  │   Service   │  │   Service   │
        │    :8082    │  │    :8083    │  │    :8084    │
        └──────┬──────┘  └──────┬──────┘  └──────┬──────┘
               │                 │                │
               └─────────────────┼────────────────┘
                                 │
                                 ▼
                       ┌────────────────────┐
                       │   Eureka Server    │
                       │       :8762        │
                       └────────────────────┘

                       ┌────────────────────┐
                       │       MySQL        │
                       │      fleetdb       │
                       └────────────────────┘

🧩 Microservices

1. 🔐 Auth Service
Port: 8081
Responsibilities:
- User authentication
- Username/password validation
- BCrypt password encryption
- JWT token generation
- Role information in JWT

2. 🚚 Vehicle Fleet Administration
Port: 8082
Responsibilities:
- Add vehicles
- View vehicles
- View vehicle details
- Update vehicle status
- Delete vehicles
Vehicle states include:
AVAILABLE
ON_TRIP
UNDER_MAINTENANCE

3. 📦 Trip Dispatch Lifecycle
Port: 8083
Responsibilities:
- Assign trips
- Validate vehicle availability
- Start trips
- Track trip progress
- Update current location
- Complete trips
- Trigger vehicle status updates
- Create maintenance requests
Trip lifecycle:
ASSIGNED
    ↓
IN_PROGRESS
    ↓
COMPLETED

4. 🔧 Maintenance Work-order Tracking
Port: 8084
Responsibilities:
- Create maintenance records
- Track maintenance issues
- View maintenance history
- Complete maintenance work orders
- Update vehicle availability after maintenance
Maintenance lifecycle:
OPEN
 ↓
COMPLETED

5. 🔎 Eureka Service Registry
Port: 8762
Eureka provides service registration and discovery between the microservices.
Services register themselves with Eureka and can discover other services using their service names.

6. 🌐 API Gateway
Port: 8080
The API Gateway acts as the single entry point for client requests.
It provides:
- API routing
- JWT validation
- Service discovery integration
- Load balancing
- Centralized API access

🔄 Inter-Service Communication
The project uses Spring Cloud OpenFeign for communication between services.
Example:
Trip Service
     │
     ├──► Vehicle Service
     │       └── Check vehicle availability
     │
     └──► Maintenance Service
             └── Create maintenance request
OpenFeign provides a declarative REST client for communication between Spring Boot services. Home
🔐 JWT Authentication
The system uses JSON Web Tokens for API protection.

Authentication flow:
User
 ↓
POST /auth/login
 ↓
Auth Service
 ↓
JWT Token
 ↓
Client
 ↓
API Gateway
 ↓
JWT Validation
 ↓
Microservice
Protected APIs require:
Authorization: Bearer <JWT_TOKEN>

<img width="795" height="861" alt="image" src="https://github.com/user-attachments/assets/08217c07-7fd7-46fe-a8e2-91fe9c664379" />

👥 Team Project
Project: LogiFleet – Commercial Fleet Telematics & Vehicle Maintenance Operations
Team Size: 3 Members

2400030813 - AVUTHU DURGA BHAVANI

2400031087 - KATAKAM YOSHITHA

2400033341 - KARNAM NANDINI


The project was developed as a team-based distributed systems project focusing on microservices, security, service discovery, API management and database integration.
🌟 Future Enhancements
- Real-time GPS tracking
- Live fleet dashboard
- Driver management
- Automated preventive maintenance alerts
- Docker containerization
- Kubernetes deployment
- Cloud deployment
- Monitoring and logging
- Role-based authorization
- Real-time notifications

👩‍💻 Developed By
LogiFleet Team
Technologies
Java Spring Boot Spring Cloud Eureka Gateway OpenFeign JWT MySQL Maven Git
