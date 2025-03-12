# **Async Task Processor (Spring Boot + RabbitMQ + H2)**

## **Overview**
This is a Spring Boot backend application that handles asynchronous task processing using RabbitMQ. Tasks can be submitted via a REST API and are processed asynchronously, with their status being updated in an H2 database.

## **Features**
- **Task Submission**: Submit a new task with a name and payload.
- **Task Status Tracking**: Retrieve the status of any task.
- **Asynchronous Processing**: Tasks are queued and processed asynchronously using RabbitMQ.
- **Data Persistence**: Task metadata is stored in an H2 database.
- **Error Handling**: Graceful handling of failed tasks.

## **Tech Stack**
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- RabbitMQ
- Lombok (to reduce boilerplate code)

## **Setup Instructions**

### **1. Prerequisites**
Ensure you have the following installed:
- Java 17+
- Maven
- RabbitMQ (Installed and Running)
- IntelliJ IDEA (optional but recommended)

### **2. Clone the Repository**
```sh
git clone https://github.com/your-repo/async-task-processor.git
cd async-task-processor
```

### **3. Install Lombok and Enable It in IntelliJ**
Lombok is used to reduce boilerplate code for getters, setters, and constructors.

#### **Add Lombok to `pom.xml` (if not already included)**
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

#### **Enable Lombok in IntelliJ IDEA**
1. Open **IntelliJ IDEA** and go to **File > Settings** (or **Preferences** on macOS).
2. Navigate to **Plugins**, search for **Lombok**, and install the plugin.
3. Restart IntelliJ IDEA.
4. Enable annotation processing:
    - Go to **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors**.
    - Check **Enable annotation processing**.

### **4. Start RabbitMQ**
Make sure RabbitMQ is installed and running on your machine.  
If RabbitMQ is running locally, it should be available at:
- **Broker URL**: `amqp://localhost:5672`
- **Management UI**: `http://localhost:15672` (User: `guest`, Password: `guest`)

### **5. Build and Run the Application**
```sh
mvn clean install
mvn spring-boot:run
```

### **6. API Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/tasks?name={name}&payload={payload}` | Submit a new task |
| `GET` | `/tasks` | Retrieve all tasks |
| `GET` | `/tasks/{id}` | Get task status by ID |

#### **Example Task Submission (Query Parameters)**
```sh
curl -X POST "http://localhost:8080/tasks?name=Task1&payload=SampleData"
```

#### **Example Task Status Check**
```sh
curl -X GET "http://localhost:8080/tasks/1"
```

### **7. H2 Database Console**
H2 Database Console is available at:
```
http://localhost:8080/h2-console
```
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave blank)*

### **8. Running Tests**
```sh
mvn test
```

### **9. Contributing

Contributions are welcome! Please fork the repository and create a pull request with your enhancements.

### **10. License**
This project is licensed under the MIT License.
