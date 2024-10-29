# Accreditation Service

## Overview

This project implements a RESTful service for managing users' accreditation statuses using the MVC (Model-View-Controller) architectural pattern. The main components of the architecture are as follows:

1. **Model**: Represents the data structure of the application. In this project, the `Accreditation` and `User` entities serve as the models, encapsulating the properties and behaviors associated with the accreditation statuses and user information.

2. **View**: In a RESTful service context, the view is represented by the JSON responses sent to the clients. The API endpoints return data in a structured format (e.g., JSON) that can be consumed by client applications, such as web or mobile apps.

3. **Controller**: Handles incoming HTTP requests and maps them to the appropriate service methods. The `AccreditationController` processes requests for creating, updating, and retrieving accreditation statuses, coordinating the flow of data between the model and the service layers.

4. **Service**: Contains the business logic of the application. The `AccreditationService` manages the validation and processing of accreditation requests, ensuring the integrity of the application's operations.

5. **Repository**: Interacts with the database to perform CRUD operations on the data models. Both `AccreditationRepository` and `UserRepository` use Spring Data JPA to provide a convenient way to access and manipulate the database.

This MVC structure separates concerns, making the application more modular, maintainable, and testable. Each component has a distinct responsibility, allowing for clearer organization of code and better collaboration among developers.

---

## Additional Requirement: Audit Log for Accreditation Status Updates

To implement an audit log for historical accreditation status updates, I would create an `AuditLog` Entity to store each accreditation status update with fields such as:
- 1). `id`: to uniquely identify the audit record
- 2). `accreditationId`: the ID of the accreditation this log entry is associated with
- 3). `oldStatus`: the status before the update
- 4). `newStatus`: the updated status
- 5). `timestamp`: the date and time when the status change occurred, 
- 6). `userId`: (Optionally) the ID of the user associated with the accreditation, for easy querying.

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
An audit log is essential for tracking and maintaining a clear history of all accreditation status changes, providing full traceability of user verification processes. 
By capturing every update—when statuses move from “PENDING” to “CONFIRMED” to “EXPIRED” or “FAILED”—the audit log serves as a chronological record that helps ensure transparency and accountability. 
This history is valuable not only for compliance with regulatory requirements but also for internal reviews, allowing administrators to track patterns, identify irregularities, or troubleshoot any issues.

## Scaling the Service

As traffic increases and the service faces performance degradation, several strategies can be employed to scale the application:

- Load Balancing: Deploy multiple instances of the service and use a load balancer (e.g., Nginx, AWS ELB) to distribute incoming requests among these instances. This can improve throughput and reduce response times.
- Asynchronous Processing: If applicable, move long-running processes to background tasks using message queues (e.g., RabbitMQ, Kafka) to improve the responsiveness of the API.
- Horizontal Scaling: Consider using container orchestration platforms like Kubernetes to manage the deployment and scaling of application instances dynamically based on traffic.