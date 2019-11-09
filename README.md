# Microservice application

A Java microservice applicaton to order virrtual machines

## Getting started

### Prerequisites

Below are the softwares that needs to be installed as a dependency

```
Java
Maven
Docker (to build Docker image)
NodeJs
Jhipster (npm install generator-jhipster -g)
```

## Running the application

To create application from scratch and to build each\
microservices individually navigate to section\
```Generating and building microservices```

1 . Git clone the repo to any machine

2 . Build the entire project using
```bash
mvn -Pprod verify com.google.cloud.tools:jib-maven-plugin:dockerBuild -DskipTests
```

3 . Start all the docker containers
```bash
cd docker
docker-compose up -d
```

4 . Access the Rabbitmq ui by navigating to
```html
http://localhost:15672
```
Login using ```guest/guest``` as the credentials

5 . Access the Jhipster registry in
```http
http://localhost:8761
```

6 . Access the admin dashboard in
```html
http://localhost:8080
```
Login using ```admin/admin``` or ```user/user```

7 . Access the customer dashboard in
```html
http://localhost:8081
```

## Generating and building microservices

Steps to create the application

1 . Create the app.jdl with proper contents

2 . Generate the application using
```bash
jhipster import-jdl apps.jh
```

3 . Generate the docker meta files
```bash
mkdir docker-compose && cd docker-compose
jhipster docker-compose
```

3 . Build the docker image in each of the component using
```bash
./mvnw -Pprod verify jib:dockerBuild -DskipTests
```

4 . Once the all the docker images are ready, start them
```bash
cd docker-compose
docker-compose up -d
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors
Rakesh Venkatesh