# Requirements Task 7
## Overview

For this project I implemented a RESTful service using the MVC (Model-View-Controller) architectural pattern. 

1. **Model**: Shows the application's data structure. The `Accreditation` and `User` entities are used as models in this project, containing the attributes and actions related to user data and accreditation statuses.

2. **View**: The JSON replies that are given to the clients in a RESTful service context serve as the view. Client programs, such web or mobile apps, can use the structured data returned by the API endpoints (like JSON).

3. **Controller**: Incoming HTTP requests are handled by the Controller, which also assigns them to the relevant service methods. Coordinating the data flow between the model and the service levels, the `AccreditationController` handles requests for creating, changing, and retrieving accreditation statuses.

4. **Service**: Consists of the application's business logic. The integrity of the application's operations is maintained by the `AccreditationService`, which controls the processing of accreditation requests.

5. **Repository**: Performs CRUD actions on the data models by interacting with the database. Spring Data JPA is used by both `AccreditationRepository` and `UserRepository` to offer a practical means of accessing and modifying the database.

This MVC framework improves the application's modularity, maintainability, and testability because each component has a specific role, the code can be organised more clearly and developers can work together more effectively.

---

## Additional Requirement: Audit Log for Accreditation Status Updates

To implement an audit log for historical accreditation status updates, I would create an `AuditLog` Entity to store each accreditation status update with fields such as:
- `id`: to uniquely identify the audit record
- `accreditationId`: the ID of the accreditation this log entry is associated with
- `oldStatus`: the status before the update
- `newStatus`: the updated status
- `timestamp`: the date and time when the status change occurred, 
- `userId`: (Optionally) the ID of the user associated with the accreditation, for easy querying.

   ```java
   @Entity
   public class AuditLog {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       private UUID accreditationId;
       private String oldStatus;
       private String newStatus;
       private LocalDateTime timestamp;
   }
An audit log is needed to keep track of and keep a clear record of all changes to an accreditation state. This makes sure that all user verification processes can be fully tracked.
By writing down every change, like when a state goes from "PENDING" to "CONFIRMED" to "EXPIRED" or "FAILED," the audit log keeps a record of what happened and when. This helps make sure that everyone is responsible and open. This history is valuable not only for compliance with regulatory requirements but also for internal reviews, allowing administrators to track patterns, identify irregularities, or troubleshoot any issues.
In addition to meeting regulatory requirements, this history is useful for internal reviews because it helps administrators spot trends, find problems, or figure out what's wrong.

## Scaling the Service

As the number of users grows and the service's speed suffers, there are a number of ways to make the application bigger:

- Load Balancing: Set up multiple instances of the service and use a load balancer (example Nginx or AWS ELB) to send requests to each server as they come in. This can improve throughput and reduce response times.
- Asynchronous Processing: To improve the responsiveness of the API, message queues (like RabbitMQ or Kafka) can be used to move time-consuming operations to background jobs when needed.
- Horizontal Scaling: The use of container orchestration technologies such as Kubernetes to control the deployment and scaling of application instances.

# Accreditation Service

## Endpoints
* If you are using Postman go to ./docs -> YieldstreetTask.postman_collection.json and import the endpoints.

| Method | Endpoint                              | Description                                                                  |
|--------|---------------------------------------|------------------------------------------------------------------------------|
| POST   | /user/{userId}/accreditation          | Adds an accreditation (NOTE: a raw body in JSON format need to be added)     |
| PUT    | /user/accreditation/{accreditationId} | Sets accreditation status (NOTE: a raw body in JSON format need to be added) |
| GET    | /user/{userId}/accreditation          | Get all user's accreditations                                                |
| POST   | /scheduler/run                        | Runs the scheduler manually (comment out @Scheduler annotation first)        |

* Add an accreditation raw JSON body example:
  ```java
  {
    "user_id": "g8NlYJnk7zK9BlB1J2Ebjs0AkhCTpE1V",
    "accreditation_type": "BY_INCOME",
    "document": {
      "name": "2018.pdf",
      "mime_type": "application/pdf",
      "content": "ICAiQC8qIjogWyJzcmMvKiJdCiAgICB9CiAgfQp9Cg=="
    }
  }
  ```

* Set accreditation status raw JSON body example:
  ```java
  {
    "outcome": "CONFIRMED"
  }
  ```
## Prerequisites
- Java 17
- Maven (latest version)
- Postman used for http request calls
- H2 database. To access tables paste in a browser http://localhost:8080/h2-console
```java
  JDBC URL: jdbc:h2:mem:YieldstreetTaskDB
  User Name: yieldstreet
  Password: password