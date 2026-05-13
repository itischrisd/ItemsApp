<h1 align="center">
    ItemsApp
</h1>

## About

ItemsApp is a side project, which has two main goals in mind:

- help catalog and organize personal book collection and other belongings,
- to be a training ground for modern web app development in Spring ecosystem.

## Details

Fulfilling app's first, conventional business is done through implementation of a plain old CRUD app with a few thought
out entities. Catalogues objects are split into `Books` and `Items`, with a loose intention to generalize them in the
future in case use of the app warrants it. Both types of those object can be assigned to multiple `Categories`. For the
`Books`, in order to facilitate better colelction management, additional data can be stored, such as `Authors` and
`Publishers`. The app helps in maintenace of the personal belonging throguh `Stoage` objects, which can store either
`Books`, `Items` or other `Storages` recursively.

Defining main "business goal" as such helps both in accomplishing the first goal of the app, as well as in providing a
sufficient techincal space to meticulously pursue the second goal. While a simple CRUD app implementation could be
viewed as trivial, it can provide much room and ample opportunities to use and refine elements such as different
dependencies and libraries, Spring tools and web app development principles, patterns and ideas.

## Used technologies

- JDK 25
- Spring Boot 4.x
- Spring Data JPA, WebMVC, Validation
- Hibernate 7.x
- H2, PostgreSQL
- Flyway
- Logback, Slf4j
- Lombok
- Jackson
- JUnit 6.x
- MapStruct
- Swagger

## Installation and configuration

_TO DO_

## User's guide

_TO DO_

## Planned functions and elements

Planned user functionality and technical elements has been split into categories following MoSCoW model.  
✅ - _completed_  
❌ - _incomplete_

### Must have

✅ H2 db for development (with concurrent TCP access)  
✅ Entities for all planned classes    
✅ Timestamps (creation and update), version for entities  
✅ JPA repositories  
✅ Relations between entities  
✅ JPQL queries (to fetch object with associated objects)    
✅ DTOs (request and response)  
✅ Lombok annotations for entities and DTOs (proper choice)  
✅ MapStruct mappers     
✅ Reference translator for MapStruct (with validation)  
✅ Services  
✅ REST controllers  
✅ Endpoints for standard CRUD  
✅ Endpoints to support associations (view, create, remove)  
✅ Validation annotation in DTOs  
✅ Custom validation  
✅ Validation on REST controllers  
✅ Validation on Services  
✅ @Column annotations in entites (with suitable constraints)  
✅ Exception handling (through Controller Advice)  
✅ Exceptions for Service layer  
✅ Swagger interface  
❌ Postman collection  
✅ Test data for H2  
✅ Project documentation with MoSCoW model  
❌ PostgreSQL for testing and prod  
❌ Test data for Postgres  
❌ Data ingest for prod

### Should have

❌ Normalized DB schema, with documentation    
❌ Response headers (Content-Length, Location for POST)  
❌ Pagination  
❌ Filtering  
❌ Sorting  
❌ Partial responses (GraphQL-like)  
❌ Dockerization for backend  
❌ JMS (for future integration)  
✅ Spring Profiles  
✅ Maven Profiles  
❌ Data for dev, test, prod environments  
❌ .env files  
❌ Flyway migrations  
❌ generalize Books, Storage and Items into one supertype  
❌ coverType as enumeration  
❌ ETag and Last-Modified (for client-side caching)  
❌ Indices for db  
❌ OpenAPI documentation

### Could have

❌ Idempotency-Key for POST  
❌ Frontend with DaisyUI  
❌ Dockerization for full stack  
❌ Redis for local caching  
❌ Security (jwt, OAuth)  
❌ l18n  
❌ Tests: unit, module, integration, system (JUnit, AssertJ, Mockito, MockMvc, repositories, WireMock, RestAssured)  
❌ JaCoCo  
❌ Mobile app

### Won't have

❌ HATEOAS  
❌ PATCH endpoints  
❌ Thymeleaf / jsp / templating  
❌ Session-based UI  
❌ Frontend with JS framework

### To consider in the future

Additional, fifth category of items not currently planned (mainly due to lack of applicable use or compelling idea) but
good to consider in the future:

- RestClient (for external API)
- MongoDB
- Scheduled
- Async
- VPS / cloud hosting / home lab
- CI / CD pipeline(s)
- trivy
