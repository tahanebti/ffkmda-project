# ffkmda-project
the goal of this project is parse all data from ffkmda extranet service and exctract necessary information, save the clubs and sieges data in database and expose theme throught a REST API

## Applications
- ### clubs-service
  | Endpoints                | Description                                                  |
  |--------------------------|--------------------------------------------------------------|
  | `GET /api/clubs/search[?query]` | returns all clubs information or filtered by fields with pagination |
  | `GET /api/clubs/{id}`    | returns a specific club score filtered by id                 |
  | `GET /api/clubs/{code}`    | returns a specific club score filtered by code                 |
  | `GET /api/clubs/address{?queryr}`    | returns a specific club address score filtered by request params {code_postal, commune, departmeent} with pagination                 |

- ### extranet-service : 
- | Endpoints                | Description                                                  |
  |--------------------------|--------------------------------------------------------------|
  | `GET /api/structures` | returns all clubs information or filtered by fields with pagination |
  | `GET /api/structures/{code}`    | returns a specific club score filtered by code        |
  

## Prerequisites
- [`Java 17+`](https://www.oracle.com/java/technologies/downloads/#java17)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## tools
    backend : Spring boot / Spring Batch / MySQL / Redis / ElasticSearch
    frontend : Angular
    
## Start Environment
    - In a terminal, make sure you are inside root folder
    ```
    docker-compose up -d
    ```
    
## Start Backend Project
    - In a terminal, make sure you are inside `ffkmda-back` root folder
    ```
    mvn spring-boot:run
    mvn spring-boot:run --DskipTests
    ```    
    
## Start Frontent Project
    - In a terminal, make sure you are inside `ffkmda-front` root folder    
        ```
    npm install or yarn install
    
    #compilation
    npm run build
    ``` 
    
