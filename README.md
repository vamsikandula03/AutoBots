# AutoBots

## Project Title
Automated Meeting Room Booking System

## Overview
AutoBots is an Automated Meeting Room Booking System built using Java, HTML, CSS, JavaScript, JUnit Testing (Mock testing), Bootstrap, and JDBC (SQL). The system allows users to book meeting rooms, manage meeting requests, and handle room availability with various roles such as Admin, Manager, and Employee.

## Java Patterns
- **Proxy Pattern:** Used to provide authorization and authentication before accessing the service layer.
- **Factory Pattern:** Used between the Service Layer and DAO Layer to enhance loose coupling and improve maintainability.

## Exceptions
- **ResourceNotFoundException:** Thrown when a requested resource is not found.
- **ResourceAlreadyExistsException:** Thrown when a resource that already exists is being created again.
- **NotAuthorizedException:** Thrown when a user attempts to perform an action they are not authorized for.
- **CredentialsException:** Thrown when login credentials are incorrect.

## Project Structure

```plaintext
java
├── exceptions
│   ├── ResourceNotFoundException.java
│   ├── ResourceAlreadyExistsException.java
│   ├── NotAuthorizedException.java
│   └── CredentialsException.java
├── interfaces
│   ├── ServiceLayer.java
│   └── DAOLayer.java
├── layers
│   ├── serviceLayer
│   │   ├── ServiceLayerFactory.java
│   │   └── ServiceLayerImpl.java
│   └── daoLayer
│       ├── DaoLayerImpl.java
│       └── DaoLayerFactory.java
├── utils
│   ├── Amenity.java
│   ├── MeetingRoomRequestStatus.java
│   ├── MeetingRoomType.java
│   └── Roles.java
└── models
    ├── MeetingRoom.java
    ├── MeetingRoomRequest.java
    ├── MeetingRoomTypeRequired.java
    └── User.java
```

### Technologies Used
- Java: Programming language for the backend logic.
- HTML/CSS/JavaScript: Frontend technologies for creating and styling the user interface.
- Bootstrap: CSS framework for responsive design and UI components.
- JUnit: Framework for unit testing, including mock testing using Mockito.
- JDBC: Java API for connecting and executing SQL queries on the database.

### Prerequisites
- Java JDK 11 or higher
- Maven for dependency management
- A relational database (e.g., MySQL)

