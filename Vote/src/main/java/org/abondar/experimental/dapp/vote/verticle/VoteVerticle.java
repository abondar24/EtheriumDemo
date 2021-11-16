package org.abondar.experimental.dapp.vote.verticle;


import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import org.abondar.experimental.dapp.vote.service.EthereumService;
import org.abondar.experimental.dapp.vote.service.VoteService;

import java.util.Set;

public class VoteVerticle extends AbstractVerticle {

    @Override
    public Completable rxStart() {
        var options = Set.of("Alex", "Bob", "Wilhelm","Mark","Max","David","Anton");
        var voteService = new VoteService(options, new EthereumService());
        var handler = new Handler(voteService);


        var router = Router.router(vertx);

        router.post().handler(handler.bodyHandler());
        router.put().handler(handler.bodyHandler());

        router.post("/register").handler(handler::handleRegister);
        router.put("/vote").handler(handler::handleVote);
        router.get("/winner").handler(handler::handleWinner);
        router.get("/options").handler(rc-> handler.handleOptions(rc,options));


        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(8080)
                .ignoreElement();

    }
}
