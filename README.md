### requirements:

+ jdk 11 (or higher)
+ maven 3.8.6 (or higher)

Two profiles and append a -P parameter to switch which Maven profile will be applied:
* dev (default)
* prod

How to run a local app, run following commands:

* mvn clean compile -U
* mvn flyway:migrate
* mvn spring-boot:run

How to (target folder):

* build - mvn clean package -Pdev
* run local - java -jar <jarname>.jar

Api Documentation (Swagger)

* http://localhost:8080/api/swagger-ui/index.html (local host)
* http://host/context-path/api/swagger-ui/index.html

Change db parameters

* open pom.xml
* change following properties for maven plugin

```xml
<spring.datasource.schema>postgres</spring.datasource.schema>
<spring.datasource.url>jdbc:postgresql://localhost:5432/${spring.datasource.schema}</spring.datasource.url>
<spring.datasource.username>postgres</spring.datasource.username>
<spring.datasource.password>postgres</spring.datasource.password>
```

* rebuild and run an app


Change backend server port

* open file src/main/resources/application.properties
* change following property

```clojure
server.port=8080
```
