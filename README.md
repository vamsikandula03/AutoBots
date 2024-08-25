# AutoBots

![image](https://github.com/user-attachments/assets/7c2139d6-02dc-4db0-ae33-dc6d0c92bb97)

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

![image](https://github.com/user-attachments/assets/a8d46b5b-5a7a-4bc8-bf9e-4d29db95e8ce)

![image](https://github.com/user-attachments/assets/0ac50bff-c34a-465e-925b-59408c0f2a37)

![image](https://github.com/user-attachments/assets/f4341e85-67e1-4286-9b28-65601a3a2a5c)

![image](https://github.com/user-attachments/assets/f0d6af7d-e241-4965-9dbf-a0dd29073f9e)

![image](https://github.com/user-attachments/assets/cae86bf5-c82e-4f83-a3e7-c491cef63f2a)

![image](https://github.com/user-attachments/assets/9a7100a5-3c67-4cfa-880b-ea531804f65c)

![image](https://github.com/user-attachments/assets/a5b1a3e0-3a15-4faf-b8ea-2ad9448238d9)
