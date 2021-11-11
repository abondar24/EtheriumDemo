package org.abondar.experimental.dapp.vote;

import io.vertx.reactivex.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.rxDeployVerticle(new VoteVerticle())
                .subscribe(
                        ok -> logger.info("server is up"),
                        err -> logger.error("Error",err)
                );
    }
}
