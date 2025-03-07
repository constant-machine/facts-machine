# facts-machine

Ktor based web service for discovering useless facts and sharing them. Not product-ready, for educational purposes only.

To try it out locally just run `gradle run` and access it at "http://localhost:8080/facts" like that:

`curl --location --request POST 'http://localhost:8080/facts'`

### Design decisions:
- dependency injection ktor module (koin) to address unneeded app initialization complexity
- use ConcurrentHashMap to store facts thread-safe in-memory
- reasonably decoupled services easy to extend, change and substitute
- compute short urls and not store them in case of possible hostname changes
- custom id gen logic makes the service independent of the facts provider, tradeoff is a possibility of storing fact duplicates(addressable)
- Base64 url safe encoding of the counter value for id generation - no collisions, predictable but fine for our case

### Configuration:
- provide base url for a link shortener in ktor.baseurl property in application.yaml file
- set ADMIN_USERNAME and ADMIN_HASHED_PASSWORD ([hash it here for example](https://bcrypt.online/)) env properties for the statistics section security

________________________________________________________________________

This project was created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

### Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
| -------------------------------|---------------------------------------------------------------------- |
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

