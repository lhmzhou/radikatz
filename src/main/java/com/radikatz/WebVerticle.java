package com.radikatz;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class WebVerticle extends AbstractVerticle {

    // private JsonObject jsonObject;
    private HttpServerResponse httpServerResponse;

    @Override
    public void start(Future<Void> fut) {
        System.out.println("Entering the Web Verticle...");
        Router router = Router.router(vertx); // new router object

        router.route().handler(BodyHandler.create());
        router.post("/api/participant").handler(this::redisHandler);

        // create server, pass accept to handler, default to 8080 
        vertx.createHttpServer().requestHandler(router::accept).listen(config().getInteger("http.port", 8080), 
            result -> {
                if (result.succeeded()) {
                    fut.complete();
                } else {
                    fut.fail(result.cause());
                }
            });
    }

    private void redisHandler(RoutingContext routingContext) {

        vertx.eventBus().send("redis cache", routingContext.getBodyAsJson(), new Handler<AsyncResult<Message<JsonObject>>>() {
            @Override
            public void handle(AsyncResult<Message<JsonObject>> event) {

                JsonObject jsonObject = new JsonObject(event.result().body().toString());
                httpServerResponse = routingContext.response();
                httpServerResponse.putHeader("content-type", "application/json");
                httpServerResponse.end(jsonObject.encodePrettily());
            }
        });
    }
}





