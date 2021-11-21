package org.abondar.experimental.dapp.airline;

import io.vertx.reactivex.core.Vertx;
import org.abondar.experimental.dapp.airline.verticle.AirlineVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.rxDeployVerticle(new AirlineVerticle())
                .subscribe(
                        ok -> logger.info("server is up"),
                        err -> logger.error("Error",err)
                );
    }
}
