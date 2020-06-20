package com.radikatz;

import io.vertx.core.*;

public class HttpServer {

    private final Vertx vertx = Vertx.vertx();

    private Future<Boolean> future;
    public static void main(String[] args){

        try {
            new HttpServer().start();
        }
        catch (Throwable t){
            System.out.println("Oops, sorry: main failed...");
        }
    }

    private void start() {

        System.out.println("Vertx application is running in full swing!");

        final Handler<AsyncResult<String>> WebVerticleHandler =
                (AsyncResult<String> promise) -> {
                    if (promise.failed()) {
                        System.out.println("Oops, sorry! Web verticle worker failed...");
                        future.fail(promise.cause());
                        return;
                    }
                };

        vertx.deployVerticle(WebVerticle.class.getName(), new DeploymentOptions()
                        .setInstances(4)
                        .setWorkerPoolSize(1)
                        .setWorker(true),WebVerticleHandler);


        final Handler<AsyncResult<String>> RedisVerticleHandler =
                (AsyncResult<String> promise) -> {
                    if (promise.failed()) {
                        System.out.println("Oops, sorry! Redis verticle worker failed...");
                        future.fail(promise.cause());
                        return;
                    }
                };

        vertx.deployVerticle(RedisVerticle.class.getName(), new DeploymentOptions()
                .setInstances(4)
                .setWorkerPoolSize(4)
                .setMultiThreaded(true)
                .setWorker(true)
                .setWorker(true),RedisVerticleHandler);
    }
}
