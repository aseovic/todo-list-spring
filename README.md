# Course materials for Coherence To Do List (Helidon) workshop

>This is a complete, final implementation of the application created during the workshop.
 > 
 >To switch to the starting point for the workshop, check out the `start` branch:
 > 
 >```bash
 > git checkout start
 > ```
 >        
>Open the project in the IDE of your choice, and follow the [lab instructions](https://aseovic.medium.com/coherence-to-do-list-spring-8d5c268556e0).  

## Instructions
  
### Build the project

```bash
mvn clean package
```

### Run the Application

```bash  
mvn exec:exec
```
### Build a Docker Image

```bash
mvn clean install
mvn package -P docker 
```

### Run the Docker Container

```bash
docker run -d -p 3001:3001 -P 3002:3002 ghcr.io/coherence-community/todo-list-spring-server
```

> NOTE: `3001` is the HTTP port, and `3002` is the metrics port.

### Access the Web UI

Access via http://localhost:3001/

![To Do List - React Client](assets/react-client.png)

### Query the GraphQL Endpoint

The GraphQL Endpoint is available at http://localhost:3001/graphiql.html.

To retrieve a collection of tasks, use the following query:

```graphql
query {
  tasks(completed: false) {
    id
    description
    completed
    createdAt
    createdAtDate
  }
}
```
 
To create a new task, type:
```graphql
mutation {
  createTask(description: "My GraphQL Task") {
    id
    description
    completed
    createdAt
    createdAtDate
  } 
}
```

## References

* [Coherence CE](https://coherence.community/)
* [Spring](https://spring.io/)



