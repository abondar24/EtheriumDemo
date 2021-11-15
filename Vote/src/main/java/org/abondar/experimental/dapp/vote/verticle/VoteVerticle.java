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
        var members = Set.of("Alex", "Bob", "Wilhelm");
        var voteService = new VoteService(members, new EthereumService());
        var handler = new Handler(voteService);


        var router = Router.router(vertx);

        router.post().handler(handler.bodyHandler());
        router.post("/register/:voter").handler(handler::handleRegister);
        router.put("/vote/:member").handler(handler::handleVote);
        router.get("/winner").handler(handler::handleWinner);


        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(8080)
                .ignoreElement();

    }
}
