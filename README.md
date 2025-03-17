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

### **3. Install and Configure Lombok**
Lombok is used to reduce boilerplate code by automatically generating getters, setters, constructors, and more.

#### **Step 1: Add Lombok Dependency in `pom.xml`**
If Lombok is not already included, add the following dependency in your `pom.xml` file:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

#### **Step 2: Enable Lombok in IntelliJ IDEA**
1. Open **IntelliJ IDEA**.
2. Navigate to **File > Settings** (or **Preferences** on macOS).
3. Go to **Plugins**, search for **Lombok**, and install it.
4. Restart IntelliJ IDEA.

#### **Step 3: Enable Annotation Processing**
1. Open **IntelliJ IDEA**.
2. Go to **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors**.
3. Check the box **Enable annotation processing**.
4. Click **Apply** and **OK**.


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
| `GET` | `/api/tasks/stats` | Get Statistics |

#### **Example Task Submission (Query Parameters)**
```sh
curl -X POST "http://localhost:8080/tasks?name=Task1&payload=SampleData"
```

#### **Example Task Status Check**
```sh
curl -X GET "http://localhost:8080/tasks/1"
```

#### **Example To Check Statistics*
```sh
curl -X GET "http://localhost:8080/api/tasks/stats"
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
### **8. Dockerizing the Application**
1. Create a Dockerfile
Create a Dockerfile in the root directory of the project
```dockerfile
# Use a base Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

2. Build the Docker Image
Run the following command to build the image:

```sh

mvn clean package -DskipTests
docker build -t async-data-processing .
```

3. Run the Application in Docker

- Start RabbitMQ (if not already running):
	
```sh
	docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```
- Run the Spring Boot application container:
```sh
	docker run -d -p 8080:8080 --name async-data-processing --link rabbitmq async-data-processing
```
	
-  Docker Compose to run containers separately
  - - Create a docker-compose.yml file:
        
            version: '3.8'
            
            services:
              rabbitmq:
                image: rabbitmq:management
                container_name: rabbitmq
                ports:
                  - "5672:5672"
                  - "15672:15672"
            
              async-data-processing:
                build: .
                container_name: async-data-processing
                ports:
                  - "8080:8080"
                depends_on:
                  - rabbitmq
             
  - -  Run the application using:
    ```sh
        docker-compose up -d
    ```
  - -  To stop the containers:
    ```sh
         docker-compose down
     ```
  
4. Verify the Setup
  
          Access the application at: http://localhost:8080
          
          Access RabbitMQ Management UI: http://localhost:15672/

5. Check logs:
       
        docker logs -f async-data-processing

6. Stopping and Removing Containers

    ```sh
    docker stop async-data-processing rabbitmq docker rm async-data-processing rabbitmq
    ```

### **9. Contributing**

Contributions are welcome! Please fork the repository and create a pull request with your enhancements.

### **10. License**
This project is licensed under the MIT License.
