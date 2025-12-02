# User Service - Microservice

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.4-brightgreen)

![Java](https://img.shields.io/badge/Java-11-orange)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)

![Keycloak](https://img.shields.io/badge/Keycloak-OAuth2-red)

![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR8-blue)

![License](https://img.shields.io/badge/License-MIT-yellow)

Enterprise-grade **User Management Microservice** built with Spring Boot, featuring OAuth2 authentication via Keycloak, inter-service communication with Feign Client, service discovery with Eureka, and production-ready architecture.

> **Architecture:** This is a **microservice** component of the Ticketing Application ecosystem, designed for independent deployment, scalability, and distributed system capabilities.

---

## Table of Contents

- [Overview](#overview)

- [Key Features](#key-features)

- [Technology Stack](#technology-stack)

- [Architecture](#architecture)

- [API Documentation](#api-documentation)

- [Getting Started](#getting-started)

- [Configuration](#configuration)

- [Security](#security)

- [Inter-Service Communication](#inter-service-communication)

- [Database Schema](#database-schema)

- [API Endpoints](#api-endpoints)

- [Microservices Ecosystem](#microservices-ecosystem)

---

## Overview

The **User Service** is a dedicated microservice responsible for managing users within the Ticketing Application ecosystem. It provides comprehensive user lifecycle management, including creation, updates, deletion, and integration with Project Service and Task Service for user-related operations.

### Use Cases

- **User Management:** Create, update, and manage users with detailed tracking

- **Role Management:** Assign roles (Admin, Manager, Employee) to users

- **User Validation:** Check user existence and validate user credentials

- **Project Integration:** Verify user connections with projects before deletion

- **Task Integration:** Verify user connections with tasks before deletion

- **Keycloak Integration:** Synchronize user data with Keycloak identity provider

### Service Responsibilities

- **User CRUD Operations:** Full lifecycle management of users

- **Role-Based User Management:** Link users to roles and manage role assignments

- **Keycloak Synchronization:** Create, update, and delete users in Keycloak

- **Project Service Integration:** Communicate with Project Service for project-related operations

- **Task Service Integration:** Communicate with Task Service for task-related operations

- **Access Control:** Enforce role-based access restrictions

---

## Key Features

### Security & Authentication

- **OAuth2 Integration** with Keycloak for enterprise-grade authentication

- **Role-Based Access Control (RBAC)** with Admin, Manager, and Employee roles

- **JWT Token Validation** for stateless authentication

- **Token Propagation** via Feign Client Interceptor for inter-service calls

- **Access Control Enforcement** ensuring only authorized roles can perform operations

### Architecture & Design

- **Microservice Architecture** with independent deployment capabilities

- **RESTful API Design** following industry best practices

- **Clean Architecture** with clear separation of concerns

- **DTO Pattern** for decoupling domain models from API contracts

- **Global Exception Handling** for consistent error responses

- **Entity Auditing** with BaseEntity pattern

- **Soft Delete Pattern** for data retention

### Inter-Service Communication

- **Feign Client** for declarative REST client communication

- **Service Discovery** via Eureka for dynamic service location

- **Load Balancing** with Spring Cloud LoadBalancer

- **Token Propagation** for authenticated inter-service calls

- **Project Service Integration** for project count retrieval

- **Task Service Integration** for task count retrieval

### Business Features

- **User Management:** Complete CRUD operations for users

- **User Validation:** Check user existence by username

- **Role Assignment:** Assign roles to users (Admin, Manager, Employee)

- **Keycloak Synchronization:** Automatic synchronization with Keycloak identity provider

- **User Deletion Protection:** Prevent deletion of users with active projects or tasks

- **User Listing:** Retrieve all users with sorting capabilities

### Technical Excellence

- **Spring Cloud OpenFeign** for service-to-service communication

- **Eureka Client** for service discovery

- **Swagger/OpenAPI 3.0** interactive API documentation

- **Bean Validation** for input validation

- **ModelMapper** for object mapping

- **Log4j2** for advanced logging capabilities

- **Spring Boot Actuator** for health checks and monitoring

---

## Technology Stack

### Core Framework

- **Spring Boot 2.3.4.RELEASE** - Application framework

- **Spring Web** - REST API development

- **Spring Data JPA** - Data persistence

- **Spring Security** - Security framework

- **Spring Cloud Hoxton.SR8** - Microservices framework

### Microservices & Communication

- **Spring Cloud OpenFeign** - Declarative REST client

- **Spring Cloud Netflix Eureka Client** - Service discovery

- **Spring Cloud LoadBalancer** - Client-side load balancing

### Database

- **PostgreSQL** - Primary database

- **Hibernate** - ORM framework

### Security & Authentication

- **Keycloak 18.0.0** - Identity and Access Management

- **Keycloak Spring Boot Adapter** - Keycloak integration

- **Keycloak Admin Client** - Keycloak administration

- **JWT** - Token-based authentication

### Documentation & Utilities

- **SpringDoc OpenAPI 3 (1.6.6)** - API documentation

- **Lombok 1.18.30** - Boilerplate code reduction

- **ModelMapper 3.1.0** - Object mapping

- **Log4j2** - Advanced logging

### Build & Development

- **Maven** - Dependency management

- **Java 11** - Programming language

---

## Architecture

### Microservice Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway                              │
│              (Spring Cloud Gateway)                         │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────┴──────────────┐
        │                             │
┌───────▼────────┐          ┌─────────▼──────────┐
│  User Service  │          │  Project Service    │
│ (This Service) │          │                     │
└───────┬────────┘          └─────────────────────┘
        │
        │ 
        │
┌───────▼────────┐
│   PostgreSQL   │
│   (user-db)    │
└────────────────┘
        ┌──────────────┐
        │   Eureka     │
        │  Discovery   │
        └──────────────┘
        ┌──────────────┐
        │   Keycloak   │
        │  Auth Server │
        └──────────────┘
```

### Service Layer Architecture

```
┌─────────────────────────────────────────────┐
│         Controller Layer                    │
│      (UserController)                       │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│      Service Layer (Interface)              │
│      (UserService, KeycloakService)        │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│   Service Implementation Layer              │
│   (UserServiceImpl, KeycloakServiceImpl)    │
│   • Business Logic                          │
│   • Keycloak Integration                    │
│   • Project Service Communication           │
│   • Task Service Communication              │
└─────────────────┬───────────────────────────┘
                  │
        ┌─────────┴─────────┐
        │                   │
┌───────▼────────┐  ┌───────▼────────┐
│   Repository   │  │  Feign Clients │
│    (JPA)       │  │  (ProjectClient,│
│                │  │   TaskClient)  │
└───────┬────────┘  └────────────────┘
        │
┌───────▼───────┐
│  PostgreSQL   │
└───────────────┘
         Cross-Cutting Concerns
┌─────────────────────────────────────────────┐
│  • Global Exception Handling                │
│  • Security (Keycloak Integration)          │
│  • Token Propagation (Feign Interceptor)    │
│  • Entity Auditing                          │
│  • Logging (Log4j2)                         │
└─────────────────────────────────────────────┘
```

### Project Structure

```
com.cydeo
├── client                    # Feign clients
│   ├── interceptor
│   │   └── FeignClientInterceptor
│   ├── ProjectClient
│   └── TaskClient
├── config                    # Configuration classes
│   ├── KeycloakProperties
│   ├── OpenAPI3Configuration
│   └── SecurityConfig
├── controller                # REST controllers
│   └── UserController
├── dto                       # Data Transfer Objects
│   ├── UserDTO
│   ├── RoleDTO
│   ├── response
│   │   ├── ProjectResponse
│   │   └── TaskResponse
│   └── wrapper
│       ├── ExceptionWrapper
│       ├── ResponseWrapper
│       └── ValidationExceptionWrapper
├── entity                    # JPA entities
│   ├── BaseEntity
│   ├── User
│   └── Role
├── enums                     # Enumerations
│   └── Gender
├── exception                 # Exception handling
│   ├── GlobalExceptionHandler
│   ├── ProjectCountNotRetrievedException
│   ├── RoleNotFoundException
│   ├── TaskCountNotRetrievedException
│   ├── UserAlreadyExistsException
│   ├── UserCanNotBeDeletedException
│   └── UserNotFoundException
├── repository                # Data repositories
│   ├── UserRepository
│   └── RoleRepository
├── service                   # Business logic
│   ├── KeycloakService
│   ├── RoleService
│   ├── UserService
│   └── impl
│       ├── KeycloakServiceImpl
│       ├── RoleServiceImpl
│       └── UserServiceImpl
└── util                      # Utilities
    ├── MapperUtil
    ├── RoleDTODeserializer
    └── SwaggerExamples
```

---

## API Documentation

The API is fully documented using **Swagger/OpenAPI 3.0** with interactive documentation.

### Accessing Swagger UI

Once the application is running, navigate to:

```
http://localhost:8081/swagger-ui.html
```

### Features of API Documentation

- **Interactive Testing:** Try out API endpoints directly from the browser

- **OAuth2 Integration:** Authenticate using Keycloak within Swagger UI

- **Complete Schema Documentation:** View all request/response models

- **Role-Based Filtering:** See which endpoints are accessible to each role

---

## Getting Started

### Prerequisites

1. **Java 11** or higher

   ```bash
   java -version
   ```

2. **Maven 3.6+**

   ```bash
   mvn -version
   ```

3. **PostgreSQL 12+**

   ```bash
   psql --version
   ```

4. **Keycloak Server** (Running on port 9090)

   - Download from: https://www.keycloak.org/downloads

   - Or use Docker:

   ```bash
   docker run -p 9090:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:18.0.0 start-dev
   ```

5. **Eureka Server** (Running on port 8761)

   - Service discovery server must be running

   - Other microservices in the ecosystem should be registered

6. **Project Service** (Running and registered with Eureka)

   - Required for inter-service communication

   - Must be accessible via service discovery

7. **Task Service** (Running and registered with Eureka)

   - Required for inter-service communication

   - Must be accessible via service discovery

### Installation Steps

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd user-service-master
   ```

2. **Create PostgreSQL Database**

   ```sql
   CREATE DATABASE user-db;
   ```

3. **Configure Keycloak**

   - Access Keycloak Admin Console: `http://localhost:9090`

   - Create a new realm: `cydeo-dev`

   - Create a client: `ticketing-app`

   - Configure client settings:

     - Client Protocol: `openid-connect`

     - Access Type: `confidential`

     - Valid Redirect URIs: `http://localhost:8081/*`

   - Create client roles: `Admin`, `Manager`, `Employee`

   - Note the client secret for configuration

4. **Update Application Configuration**

   Edit `src/main/resources/application.yml`:

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/user-db
       username: your_postgres_username
       password: your_postgres_password
   
   keycloak:
     realm: cydeo-dev
     auth-server-url: http://localhost:9090/auth
     resource: ticketing-app
     credentials:
       secret: your_client_secret
   
   eureka:
     client:
       service-url:
         defaultZone: http://localhost:8761/eureka
   ```

5. **Build the project**

   ```bash
   mvn clean install
   ```

6. **Run the application**

   ```bash
   mvn spring-boot:run
   ```

   Or run the JAR file:

   ```bash
   java -jar target/user-service-0.0.1-SNAPSHOT.jar
   ```

7. **Verify Installation**

   - Application: `http://localhost:8081`

   - Swagger UI: `http://localhost:8081/swagger-ui.html`

   - Health Check: `http://localhost:8081/actuator/health`

   - Eureka Registration: Check Eureka dashboard at `http://localhost:8761`

---

## Configuration

### Application Properties

| Property | Description | Default Value |

|----------|-------------|---------------|

| `server.port` | Application port | 8081 |

| `spring.application.name` | Service name for Eureka | user-service |

| `spring.datasource.url` | PostgreSQL connection URL | jdbc:postgresql://localhost:5432/user-db |

| `spring.jpa.hibernate.ddl-auto` | Hibernate DDL mode | create |

| `spring.jpa.show-sql` | Show SQL queries | true |

| `keycloak.realm` | Keycloak realm name | cydeo-dev |

| `keycloak.auth-server-url` | Keycloak server URL | http://localhost:9090/auth |

| `keycloak.resource` | Keycloak client ID | ticketing-app |

| `eureka.client.service-url.defaultZone` | Eureka server URL | http://localhost:8761/eureka |

### Environment-Specific Configuration

For production environments, consider:

- Using environment variables for sensitive data

- Setting `spring.jpa.hibernate.ddl-auto=validate`

- Enabling HTTPS

- Using external configuration management (Spring Cloud Config Server)

- Configuring proper Eureka instance settings for production

---

## Security

### Authentication Flow

1. **User Authentication**

   - Users authenticate via Keycloak

   - Keycloak issues JWT access token

   - Client includes JWT in Authorization header: `Bearer <token>`

2. **Token Validation**

   - Spring Security validates JWT signature

   - Extracts user roles from token claims

   - Enforces role-based access control

3. **Inter-Service Authentication**

   - Feign Client Interceptor automatically adds JWT token to requests

   - Token propagation ensures authenticated communication with Project Service and Task Service

### Role-Based Access Control

| Role | Permissions |

|------|-------------|

| **Admin** | Full access to all user operations, can create, read, update, and delete users |

| **Manager** | Can check user existence by username |

| **Employee** | No direct access to user management endpoints |

### Access Control Rules

- **Admins** have full access to all user management operations

- **Managers** can only check user existence

- **Employees** are denied access to user management endpoints

- Access is enforced at both method level (`@RolesAllowed`) and business logic level

### Secured Endpoints

```java
// Admin Only
POST   /api/v1/user/create
GET    /api/v1/user/read/{userName}
GET    /api/v1/user/read/all
PUT    /api/v1/user/update/{username}
DELETE /api/v1/user/delete/{userName}

// Admin or Manager
GET    /api/v1/user/check/{username}
```

---

## Inter-Service Communication

### Feign Client Integration

The service communicates with **Project Service** and **Task Service** using Spring Cloud OpenFeign:

```java
@FeignClient(name = "project-service")
public interface ProjectClient {
    @GetMapping("/api/v1/project/count/manager/{assignedManager}")
    ResponseEntity<ProjectResponse> getNonCompletedCountByAssignedManager(@PathVariable String assignedManager);
}

@FeignClient(name = "task-service")
public interface TaskClient {
    @GetMapping("/api/v1/task/count/employee/{assignedEmployee}")
    ResponseEntity<TaskResponse> getNonCompletedCountByAssignedEmployee(@PathVariable String assignedEmployee);
}
```

### Service Discovery

- **Eureka Client** enables automatic service discovery

- Service registers itself with Eureka on startup

- Feign Client uses service name (`project-service`, `task-service`) instead of hardcoded URLs

- Load balancing is handled automatically by Spring Cloud LoadBalancer

### Token Propagation

- **FeignClientInterceptor** automatically adds JWT token to all Feign requests

- Ensures authenticated communication between services

- Token is extracted from current security context

### Communication Flow

```
User Service Request
         │
         ├─► Extract JWT Token
         │
         ├─► FeignClientInterceptor adds token to header
         │
         ├─► Eureka resolves service name to actual URL
         │
         ├─► Project Service receives authenticated request
         │
         └─► Task Service receives authenticated request
```

---

## Database Schema

### Entity Relationship

```
┌─────────────────────────────────────┐
│           User Entity               │
├─────────────────────────────────────┤
│ id (PK)                             │
│ firstName                           │
│ lastName                            │
│ userName (UNIQUE)                   │
│ password                            │
│ enabled                             │
│ phone                               │
│ gender (ENUM)                       │
│ role_id (FK)                        │
│                                     │
│ (BaseEntity fields)                 │
│ insertDateTime                      │
│ lastUpdateDateTime                  │
│ insertUserId                        │
│ lastUpdateUserId                    │
│ isDeleted                           │
└─────────────────────────────────────┘
              │
              │ Many-to-One
              │
              ▼
┌─────────────────────────────────────┐
│           Role Entity                │
├─────────────────────────────────────┤
│ id (PK)                             │
│ description                         │
│                                     │
│ (BaseEntity fields)                 │
│ insertDateTime                      │
│ lastUpdateDateTime                  │
│ insertUserId                        │
│ lastUpdateUserId                    │
│ isDeleted                           │
└─────────────────────────────────────┘
```

### Key Entities

1. **User**: Core entity representing a user

   - Unique username (email format)

   - Password with validation rules

   - Role assignment (Admin, Manager, Employee)

   - Gender enumeration

   - Soft delete support

2. **Role**: Entity representing user roles

   - Description field (Admin, Manager, Employee)

   - Soft delete support

### Audit Fields (BaseEntity)

All entities inherit audit fields:

- `insertDateTime`: Record creation timestamp

- `lastUpdateDateTime`: Last modification timestamp

- `insertUserId`: Creator user ID

- `lastUpdateUserId`: Last modifier user ID

- `isDeleted`: Soft delete flag

### Gender Enumeration

```java
public enum Gender {
    MALE("Male"),
    FEMALE("Female");
}
```

---

## API Endpoints

### User Management

| Method | Endpoint | Description | Role Required |

|--------|----------|-------------|---------------|

| POST | `/api/v1/user/create` | Create a new user | Admin |

| GET | `/api/v1/user/read/{userName}` | Get user by username | Admin |

| GET | `/api/v1/user/read/all` | Get all users | Admin |

| GET | `/api/v1/user/check/{username}` | Check if user exists | Admin, Manager |

| PUT | `/api/v1/user/update/{username}` | Update a user | Admin |

| DELETE | `/api/v1/user/delete/{userName}` | Delete a user | Admin |

### Response Format

All endpoints return a consistent response wrapper:

**Success Response:**

```json
{
  "success": true,
  "statusCode": 200,
  "message": "User is successfully retrieved.",
  "data": { /* response data */ }
}
```

**Error Response:**

```json
{
  "success": false,
  "message": "User does not exist.",
  "httpStatus": "NOT_FOUND",
  "localDateTime": "2024-01-05T10:30:00"
}
```

**Validation Error Response:**

```json
{
  "success": false,
  "message": "Invalid Input(s)",
  "httpStatus": "BAD_REQUEST",
  "localDateTime": "2024-01-05T10:30:00",
  "errorCount": 2,
  "validationExceptions": [
    {
      "errorField": "userName",
      "rejectedValue": "invalid",
      "reason": "Username should be in valid email format."
    }
  ]
}
```

---

## Microservices Ecosystem

### Architecture Overview

This service is part of a larger microservices ecosystem:

```
                    ┌─────────────────────┐
                    │   API Gateway        │
                    │ (Spring Cloud Gateway)│
                    └──────────┬───────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
┌───────▼────────┐    ┌────────▼────────┐    ┌────────▼────────┐
│  User Service  │    │ Project Service │    │  Task Service   │
│  (This Service)│    │                 │    │                 │
└────────────────┘    └────────┬────────┘    └─────────────────┘
                               │
                    ┌──────────┴──────────┐
                    │                     │
            ┌───────▼────────┐    ┌───────▼────────┐
            │   PostgreSQL   │    │   PostgreSQL   │
            │   (user-db)    │    │  (project-db)  │
            └────────────────┘    └────────────────┘
                    ┌─────────────────────┐
                    │   Eureka Server     │
                    │  (Service Discovery)│
                    └─────────────────────┘
                    ┌─────────────────────┐
                    │   Keycloak Server   │
                    │  (Authentication)  │
                    └─────────────────────┘
```

### Service Dependencies

- **Eureka Server**: Required for service discovery

- **Project Service**: Required for project-related operations

  - Project count retrieval for managers

  - Validation before user deletion

- **Task Service**: Required for task-related operations

  - Task count retrieval for employees

  - Validation before user deletion

- **Keycloak Server**: Required for authentication and authorization

### Service Communication Patterns

1. **Synchronous Communication**

   - REST API calls via Feign Client

   - Used for immediate data retrieval and operations

2. **Service Discovery**

   - Eureka-based service location

   - Dynamic service resolution

3. **Load Balancing**

   - Client-side load balancing via Spring Cloud LoadBalancer

   - Automatic distribution of requests

### Benefits of Microservices Architecture

- **Independent Scalability:** Scale user service based on user management load

- **Technology Flexibility:** Use different tech stacks per service

- **Fault Isolation:** Failures in one service don't affect others

- **Independent Deployment:** Deploy user service without affecting other services

- **Team Autonomy:** Different teams can own different services

- **Database per Service:** Own PostgreSQL database for user data

---

## Project Status

**Current Version:** 0.0.1-SNAPSHOT

**Status:** Active Development

**Architecture:** Microservice (Part of Ticketing Application Ecosystem)

### Recent Updates

- OAuth2 integration with Keycloak

- Comprehensive API documentation with Swagger

- Role-based access control implementation

- Global exception handling

- Entity auditing system

- Feign Client integration with Project Service and Task Service

- Eureka service discovery integration

- Token propagation for inter-service calls

- User deletion protection with project and task validation

- Keycloak user synchronization

### Upcoming Features

- Integration tests suite

- Docker containerization

- CI/CD pipeline setup

- Distributed tracing (Spring Cloud Sleuth)

- Circuit breaker pattern (Resilience4j)

- Event-driven communication (Kafka/RabbitMQ)

- Caching layer implementation (Redis)

---

## Troubleshooting

### Common Issues

1. **Service Not Registering with Eureka**

   - Verify Eureka server is running on port 8761

   - Check `eureka.client.service-url.defaultZone` configuration

   - Ensure network connectivity between services

2. **Project Service or Task Service Communication Failures**

   - Verify services are running and registered with Eureka

   - Check service names match in Feign Clients (`project-service`, `task-service`)

   - Verify JWT token propagation in FeignClientInterceptor

3. **Keycloak Authentication Issues**

   - Verify Keycloak server is running

   - Check realm and client configuration

   - Verify client secret matches configuration

   - Ensure Keycloak admin credentials are correct

4. **Database Connection Issues**

   - Verify PostgreSQL is running

   - Check database credentials

   - Ensure database `user-db` exists

5. **User Deletion Failures**

   - Check if user has active projects (for Managers)

   - Check if user has active tasks (for Employees)

   - Verify Project Service and Task Service are accessible

### Health Checks

Access actuator endpoints for service health:

- Health: `http://localhost:8081/actuator/health`

- Info: `http://localhost:8081/actuator/info`

---
