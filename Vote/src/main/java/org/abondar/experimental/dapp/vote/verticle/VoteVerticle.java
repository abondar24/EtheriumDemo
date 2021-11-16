package org.abondar.experimental.dapp.vote.verticle;


import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import org.abondar.experimental.dapp.vote.exception.ContractException;
import org.abondar.experimental.dapp.vote.service.EthereumServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VoteVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(VoteVerticle.class);

    @Override
    public Completable rxStart() {
        var options = List.of("Alex", "Bob", "Wilhelm", "Mark", "Max", "David", "Anton");
        var ethereumService = new EthereumServiceImpl(options);

        try {
            ethereumService.init();

        } catch (ContractException ex){
            logger.error(ex.getMessage());
            Completable.error(ex)
                    .blockingAwait();
        }

        var handler = new Handler(ethereumService);

        var router = Router.router(vertx);

        router.post().handler(handler.bodyHandler());
        router.put().handler(handler.bodyHandler());

        router.post("/register").handler(handler::handleRegister);
        router.put("/vote/:option").handler(handler::handleVote);
        router.get("/winner").handler(handler::handleWinner);
        router.get("/options").handler(rc -> handler.handleOptions(rc, options));


        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(8080)
                .ignoreElement();

    }
}
