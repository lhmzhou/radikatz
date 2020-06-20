package com.radikatz;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.sql.Time;
import java.util.*;

public class RedisVerticle extends AbstractVerticle {
    private RedisOptions redisOptions;
    private RedisClient redisClient;
    // private JsonObject jsonObject;
    // private JsonObject jsonObject1;
    // private List<String> keyList;
    // private Set<String> keys;
    private Integer i = 0;
    private static Object staticLock = new Object();

    @Override
    public void start(Future fut){
        RedisClient redisClient = redisClient();

         vertx.eventBus().consumer("redis cache").handler(message -> {

              JsonObject jsonObject = new JsonObject(message.body().toString());
              List<String> keyList = new ArrayList<String>();
              Set<String> keys = jsonObject.fieldNames();

             Iterator<String> iterator = keys.iterator();
             while (iterator.hasNext()){
                keyList.add(jsonObject.getString(iterator.next()));
             }

             long startTime = System.currentTimeMillis();
             redisClient.mgetMany(keyList, res -> {
                 if (res.succeeded()) {
                     JsonObject jsonObject1 = new JsonObject();
                     Iterator<?> iterator1 = res.result().iterator();
                     i =0;
                     while(iterator1.hasNext()){
                     jsonObject1.put("value"+ ++i,iterator1.next());}
                     long endTime = System.currentTimeMillis();
                     System.out.println("Time taken by redis: " + (endTime - startTime));
                     message.reply(jsonObject1);
                 }
             });
        });
    }

    private RedisClient redisClient() {

        if (redisClient == null) {
            synchronized (staticLock) {
                System.out.println("Setting up connection...");
                redisOptions = new RedisOptions()
                        .setHost("redis.8080.cache.com")
                        .setPort(8080)
                        .setAuth("env@dev");

                redisClient = RedisClient.create(vertx, redisOptions);
            }
        }

        return redisClient;
    }
}
