# Async Data Processor

## Overview

**Async Data Processor** is a Java-based application designed to handle asynchronous data processing tasks efficiently. Leveraging the power of RabbitMQ for message brokering and Spring Boot for application configuration, this project demonstrates how to implement robust asynchronous processing in a microservices architecture.

## Features

- **Asynchronous Task Handling**: Processes tasks asynchronously to ensure non-blocking operations and improved performance.
- **RabbitMQ Integration**: Utilizes RabbitMQ for reliable message queuing and delivery.
- **Spring Boot Framework**: Employs Spring Boot for simplified configuration and rapid development.
- **Dynamic Configuration**: Reads RabbitMQ settings from `application.yml` for flexible and environment-specific configurations.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure JDK 17 or higher is installed.
- **RabbitMQ Server**: A running instance of RabbitMQ is required. [Installation Guide](https://www.rabbitmq.com/download.html)
- **Maven**: For building and managing project dependencies.

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/lokesh-mawale/async-data-processor.git
cd async-data-processor
```

### 2. Configure RabbitMQ Settings

Modify the `src/main/resources/application.yml` file to set your RabbitMQ configurations:

```yaml
rabbitmq:
  exchange: your_exchange_name
  queue: your_queue_name
  routing-key: your_routing_key
 
```

### 3. Build the Project

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

Execute the application using:

```bash
java -jar target/async-data-processor-0.0.1-SNAPSHOT.jar
```

## Project Structure

```plaintext
async-data-processor/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── asyncdataprocessor/
│   │   │               ├── AsyncDataProcessorApplication.java
│   │   │               ├── config/
│   │   │               │   └── RabbitMQConfig.java
│   │   │               ├── listener/
│   │   │               │   └── TaskListener.java
│   │   │               └── sender/
│   │   │                   └── TaskSender.java
│   │   └── resources/
│   │       ├── application.yml
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

- **AsyncDataProcessorApplication.java**: The main entry point of the Spring Boot application.
- **RabbitMQConfig.java**: Configures RabbitMQ components like queues, exchanges, and bindings.
- **TaskListener.java**: Listens for incoming messages from RabbitMQ and processes them.
- **TaskSender.java**: Sends messages to RabbitMQ for processing.
- **application.yml**: Contains application-specific configurations, including RabbitMQ settings.

## Usage

1. **Sending Tasks**: Utilize the `TaskSender` class to send messages to the configured RabbitMQ exchange.
2. **Processing Tasks**: Implement the `TaskListener` class to define the logic for processing incoming messages.

## Logging

The application uses Logback for logging. Configure logging settings in the `src/main/resources/logback-spring.xml` file.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your enhancements.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or suggestions, please open an issue in this repository.

---

*Note: Ensure RabbitMQ is running and accessible based on the configurations provided in `application.yml` before starting the application.* 
