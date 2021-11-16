package org.abondar.experimental.dapp.vote.verticle;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.abondar.experimental.dapp.vote.exception.VoteException;
import org.abondar.experimental.dapp.vote.service.VoteService;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class Handler {

    private final VoteService voteService;

    public Handler(VoteService voteService) {
        this.voteService = voteService;
    }

    public BodyHandler bodyHandler() {
        return BodyHandler.create();
    }


    public void handleVote(RoutingContext rc) {
        var member = rc.pathParam("member");

        voteService.makeVote(member)
                .subscribe(resp -> sendSuccess(rc, resp),
                        err -> {
                            if (err instanceof VoteException) {
                                sendBadRequest(rc, err);
                            } else {
                                sendBadGateway(rc, err);
                            }
                        });


    }

    public void handleRegister(RoutingContext rc) {
        var voter = rc.getBodyAsJson();

        voteService.registerVoter(voter)
                .subscribe(resp -> sendSuccess(rc, resp),
                        err -> {
                            if (err instanceof VoteException) {
                                sendBadRequest(rc, err);
                            } else {
                                sendBadGateway(rc, err);
                            }
                        });
    }

    public void handleWinner(RoutingContext rc) {
        voteService.calcWinner()
                .subscribe(resp -> sendSuccess(rc, resp),
                        err -> {
                            if (err instanceof VoteException) {
                                sendBadRequest(rc, err);
                            } else {
                                sendBadGateway(rc, err);
                            }
                        });
    }

    public void handleOptios(RoutingContext rc, Set<String> options) {
        var body = new JsonObject();
        var arr = new JsonArray(new ArrayList<>(options));
        body.put("options",arr);

        sendSuccess(rc, body);
    }

    private void sendBadGateway(RoutingContext rc, Throwable err) {
        rc.fail(502, err);
    }

    private void sendBadRequest(RoutingContext rc, Throwable err) {
        rc.fail(400, err);
    }

    private void sendSuccess(RoutingContext ctx, JsonObject body) {
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .end(body.encode());
    }

}
