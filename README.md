# radikatz

`radikatz` demonstrates how an example Vert.x API interacts with a thread-safe Redis data-structure server shared across different verticle instances.

## Pregaming

- a Redis server instance configured and running on your network
- JDK8
- Maven

## Usage

To run `radikatz`, you'll need a running Redis instance. You can configure the verticles with the following redis host:

```
{
    "redisConfig": {
        "host": "redis.8080.cache.com",
        "port": 8080,
        "eventBusAddress": "address_where_redis_handler_is_registered"
    }
}
```

## Build

On a Mac, you can choose to install redis by running: `brew install redis`. 

Start up Redis locally and set the REDISTOGO_URL environment variable:

`$ export REDISTOGO_URL="redis://:@localhost:8080"`

Build the sample:

```
$ mvn clean package
$ java -jar target/radikatz-1.0.0-SNAPSHOT-fat.jar
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building radikatz 1.0.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
...
```

## See Also

[Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
</br>
[Accessing Data Reactively with Redis](https://spring.io/guides/gs/spring-data-reactive-redis/)
</br>
[JEDIS — Simple guide to use the Java Redis Library](https://medium.com/@karthikcsridhar/jedis-simple-guide-to-use-the-java-redis-library-18267221797b)
</br>
[vertx-awesome](https://github.com/vert-x3/vertx-awesome)