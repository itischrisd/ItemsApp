<h1 align="center">
    ItemsApp
</h1>

## About

ItemsApp is a side project, which aims to serve two purposes: to help catalog and organize my personal book collection
and other belongings, and to be a training ground for modern web app development in Spring ecosystem.

## ToDo

This is an incomplete list of planned work, roughly ordered by priority:

  Prio 1 (MUST):
- validation
- error handling
- business documentation (MoSCoW)
- Swagger
- Postman
- db (PostreSQL)
- migrate previous data to db

  Prio 2 (nice to have BE):
- pagination, sorting, filtering
- BE dockerization
- JMS
- env and profiles
- flyway migrations
- generalize Books, Storage and Items into one supertype
- idempotent POST
- Etag caching

  Prio 3 (long term future, FE):
- DaisyUI interface
- BE+FE dockerization
- tests (JUnit, AssertJ, Mockito, MockMvc, repositories, WireMock, RestAssured)
- JaCoCo
- security (jwt, OAuth)

To consider:
- RestTemplate (ext API)
- MongoDB
- Redis caching
- Scheduled
- Async
- VPS / cloud hosting
- CI/CD for this project
- trivy

  Won't do:
- thymeleaf / jsp
- FE framework
