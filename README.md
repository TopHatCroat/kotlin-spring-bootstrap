# Bootstrap: Kotlin + Spring Boot

This is a very simple bootstrap project using Kotlin and Spring Boot 2 with Gradle.

You can run this by running the following inside the project root directory:

`docker-compose up` 

to stop and remove the images running:

`docker-compose rm`

### Developing

1. Create a "Spring Boot" configuration inside "IntelliJ IDEA" and set environmental options as seen inside docker-compose.yml for `app.example`
2. Set up a database:
    * Option 1. Run `docker run -p 5432:5432 postgres` which will start a new Postgres database
    * Option 2. From `src/main/resources/application.yml` comment out the line at `spring.datasource.url`, which will make Spring boot use a in memory database
3. Run the configuration
