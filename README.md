# Spring Boot with Security and JWT Tutorial
**An tutorial application using Spring Boot as REST API with Security and JWT back-end.**

More details about the codes, please read the online **[Spring Boot](https://projects.spring.io/spring-boot).**

Requirements
------
Running in
+ [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.8 or newer
+ [Spring Boot](https://github.com/spring-projects/spring-boot) 1.5.2.RELEASE or newer
+ [Spring Security](https://github.com/spring-projects/spring-security) 1.5.2.RELEASE or newer
+ [Gradle](https://github.com/gradle/gradle) 3.4.1 or newer
+ [Lombok](https://projectlombok.org)
+ [H2](http://www.h2database.com/html/main.html)

Optional
------
+ YAML
+ Logback

Dependencies
------
+ [org.springframework.boot:spring-boot-starter-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
+ [org.springframework.boot:spring-boot-starter-security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
+ [org.apache.commons:commons-lang3:3.5](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3)
+ [io.jsonwebtoken:jjwt:0.7.0](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)
+ [org.springframework.boot:spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
+ [com.h2database:h2](https://mvnrepository.com/artifact/com.h2database/h2)

Latest Update
------
+ 1.0 (Apr 20, 2017)

How to Run
------
+ Clone [this Git repository](https://github.com/warumono-for-develop/spring-boot-security-jwt-tutorial)
+ Build the project gradle build
+ Run the application **./gradlew bootRun**
```command
$ ./gradle bootRun
```

Test accounts
------
**Reference file**
[data.sql](https://github.com/warumono-for-develop/spring-boot-security-jwt-tutorial/blob/master/SpringBootSecurityJwtTutorial/src/main/resources/data.sql)

**ADMIN**
+ username: admin@me.com
+ password: test1234

**STAFF**
+ username: admin@me.com
+ password: test1234

**USER**
+ username: admin@me.com
+ password: test1234

API
------
#### Configuration
By default Spring Boot applications run on port **9090**.
But may vary depending on what ports are in use on your machine (check the terminal after entering the ./gradlew bootRun command).
If you require to change which port the application runs on by default, add the following to:

#### application.yml
```yml
server:
    port: 9090 # --> change other port via. 8080
```

+ ### Request a token
#### Request
**command**
```http
curl -X POST -H "X-Requested-With: XMLHttpRequest" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{"username": "<username>", "password": "<password>"}' "http://localhost:9090/api/auth/login"
```

**command sample**
```http
curl -X POST -H "X-Requested-With: XMLHttpRequest" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{"username": "staff@me.com", "password": "test1234"}' "http://localhost:9090/api/auth/login"
```

#### Response
```http
{"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdGFmZkBtZS5jb20iLCJzY29wZXMiOlsiUk9MRV9TVEFGRiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJzcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwtaXNzdXJlciIsImlhdCI6MTQ5MjU4NjcxMCwiZXhwIjoxNDkyNTg3MDEwfQ.rTNoYxEmdhG7MH6O-xK5rLpCkyzCloHxMuLMtIURVCmF-KGlbVgnC3gQ9gh4dzR2P9JRj2HT9523R1sasn2IGg","refreshToken":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdGFmZkBtZS5jb20iLCJzY29wZXMiOlsiUk9MRV9SRUZSRVNIX1RPS0VOIl0sImlzcyI6InNwcmluZy1ib290LXNlY3VyaXR5LWp3dC10dXRvcmlhbC1pc3N1cmVyIiwiaWF0IjoxNDkyNTg2NzEwLCJleHAiOjE0OTI1ODcwMTAsImp0aSI6ImM4OTU3ZmNhLTA3YjgtNDYzYi1iMDc5LWIwZDBlMGQyYWNlYyJ9.ZstM2QsK_3NmYsiXSiSePHO_ctPPHmaZX-Jxlqj8nFCT-Jy6xIVpe9BFvOahAZK1ajza8huIwNug_-bnIXw2_g"}
```

+ ### Request a user
#### Request
**command**
```http
curl -X GET -H "X-Authorization: Bearer <token>" -H "Cache-Control: no-cache" "http://localhost:9090/api/profile/me"
```

**command sample**
```http
curl -X GET -H "X-Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdGFmZkBtZS5jb20iLCJzY29wZXMiOlsiUk9MRV9TVEFGRiIsIlJPTEVfVVNFUiJdLCJpc3MiOiJzcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwtaXNzdXJlciIsImlhdCI6MTQ5MjU4NjcxMCwiZXhwIjoxNDkyNTg3MDEwfQ.rTNoYxEmdhG7MH6O-xK5rLpCkyzCloHxMuLMtIURVCmF-KGlbVgnC3gQ9gh4dzR2P9JRj2HT9523R1sasn2IGg" -H "Cache-Control: no-cache" "http://localhost:9090/api/profile/me"
```

#### Response
```http
{"username":"staff@me.com","authorities":[{"authority":"ROLE_STAFF"},{"authority":"ROLE_USER"}]}
```

Author
------
**warumono** - <warumono.for.develop@gmail.com>

License
------
**spring-boot-security-jwt-tutorial** is available under the Apache license. See the LICENSE file for more info.
