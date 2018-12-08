# DemoStore
Demo Spring Boot 2.0 application using JPA and REST with Swagger 2.

The application has 2 REST API gateways, `Product API` and `Order API`. They are documented with the help of Swagger 2

### Getting sources
`git clone https://github.com/lblaga/DemoStore.git`
 
### Compile, package, run, test - short version
- `mvn clean package spring-boot:run`
- access [http://localhost:8989/swagger-ui.html](http://localhost:8989/swagger-ui.html) and test endpoints.

**The rest of the document contains details, fine tunings.**

### Compiling, packaging, running sources

The application can be built with maven, and run with maven or as java jar file. All the above maven operations should 
be triggered from the folder when pom.xml is located (/DemoStore/)

1. ####Running with maven
This command will compile, package and run the application.  

`mvn clean package spring-boot:run`
 
This will run also run all the JUnit test cases. 
Aldo it contains integration tests too, usually does not require too much time to perform all the tests.
However, tests can be skipped with the usual:

`mvn clean package spring-boot:run -DskipTests=true`

2. ####Running as java jar file
- `mvn clean package` or `mvn clean package -DskipTests=true` then
- `java -jar target/java -jar target/demostore-0.1-SNAPSHOT.jar`
   
In both cases, after the message _Demo data created, ready._ printed in the output, the application is ready to be 
tested.

The application logs its messages in demostore.log

### Persistence support
The application supports 2 type of relational database:
- in memory **H2 DB** - no additional setting is required. The DB can be accessed with [http://localhost:8989/h2-console](http://localhost:8989/h2-console) 
with `JDBC URL=jdbc:h2:mem:demostoredb`, `User Name=sa`, password blank.
- locally running **mysql DB** at port 3306. In this case the mysql `demostoredb` db, and `demostore` user needs to be 
created before running the application, with the help of the script from `db/mysql-db-create.sql`. 
To enable mysql support, the application must be started with the `mysql` spring profile, with the 
`-Dspring.profiles.active=mysql` option:
  - `mvn clean package spring-boot:run -Dspring.profiles.active=mysql` or
  - `java -Dspring.profiles.active=mysql -jar target/java -jar target/demostore-0.1-SNAPSHOT.jar`
   
For both DB type cases the DB tables are automatically (re) created at application startup. This - of course - can be 
controlled from the application.yml's `spring.jpa.hibernate.ddl-auto` property.
      
For testing purposes, at startup, demo data is created:
- 15 Products with name `Product 1` to `Product 15` with random price within range of 1.00..100.00
- 10 Orders with random number of order items (with random product) in 1..5 range. The ordered amount is between 1..4
      
Creation of demo data can be skipped with the help of `-Ddemostore.demodata=false` parameter, for example
      
`mvn clean package spring-boot:run -Ddemostore.demodata=false -DskipTests=true` (Integration tests *DO* need test data)

### Testing
The server runs at port `8989` and Swagger 2 UI can be accessed with [http://localhost:8989/swagger-ui.html](http://localhost:8989/swagger-ui.html)

Ways to the test the endpoints:
 - the easiest is through the Swagger UI's _Try it out_ functionality. Here also some examples are provided for 
 the needed parameters  
 - with the **curl** scripts located at `/src/test/resources/curl`
 - with any browser, by default the GET endpoints, for ex. [http://localhost:8989/products](http://localhost:8989/products)
 - any other application supporting REST client



