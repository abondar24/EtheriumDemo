package org.abondar.experimental.dapp.airline.verticle;

import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;

public class AirlineVerticle extends AbstractVerticle {

    @Override
    public Completable rxStart(){
        var router = Router.router(vertx);

        router.route()
                .handler(StaticHandler.create("webroot/assets"));
        router.get("/*")
                .handler(ctx -> ctx.reroute("/index.html"));

        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(8080)
                .ignoreElement();
    }
}
