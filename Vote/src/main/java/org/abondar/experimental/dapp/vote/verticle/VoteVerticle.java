package org.abondar.experimental.dapp.vote.verticle;


import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import org.abondar.experimental.dapp.vote.service.EthereumService;
import org.abondar.experimental.dapp.vote.service.EthereumServiceImpl;

import java.util.List;

import static org.abondar.experimental.dapp.vote.util.ApiUtil.PROPOSAL_ENDPOINT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.SERVER_PORT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.VOTE_ENDPOINT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.REGISTER_ENDPOINT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.WINNER_ENDPOINT;

public class VoteVerticle extends AbstractVerticle {


    private final EthereumService ethereumService;

    private final List<String> options = List.of("Alex", "Bob", "Wilhelm", "Mark", "Max", "David", "Anton");


    public VoteVerticle(EthereumService ethereumService) {
        this.ethereumService = ethereumService;
    }

    public VoteVerticle() {
        this.ethereumService = new EthereumServiceImpl(options);
    }

    @Override
    public Completable rxStart() {

        var handler = new Handler(ethereumService);
        var router = Router.router(vertx);

        router.post().handler(handler.bodyHandler());
        router.put().handler(handler.bodyHandler());

        router.post(REGISTER_ENDPOINT).handler(handler::handleRegister);
        router.put(VOTE_ENDPOINT).handler(handler::handleVote);
        router.get(WINNER_ENDPOINT).handler(handler::handleWinner);
        router.get(PROPOSAL_ENDPOINT).handler(rc -> handler.handleProposals(rc, options));


        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(SERVER_PORT)
                .ignoreElement();

    }
}
