# Land Route Service

A Spring Boot application that calculates a land route between two countries
based on their border information.

---

## Requirements

- Tested with Java 11
- Java 11 or higher
- Maven 3 or higher

---

## Build Instructions

From the project root directory, run:

```bash
mvn clean package
```

## This will generate the JAR file in the target/ directory.

## Run Instructions

After a successful build, run:

```bash
java -jar target/routes-0.0.1-SNAPSHOT.jar
```

---

The application will start on port 8080.

## API Usage

To calculate a land route between two countries, use the following endpoint:

HIT URL : http://localhost:8080/routing/CZE/ITA

## Example

url :http://localhost:8080/routing/CZE/ITA

Response :
{"route" : "CZE","AUT","ITA"}

when no land route found:

Response:
{"status code":"400","error":"No land route found"}
