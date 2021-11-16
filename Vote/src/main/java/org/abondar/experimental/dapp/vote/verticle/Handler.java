package org.abondar.experimental.dapp.vote.verticle;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.abondar.experimental.dapp.vote.exception.VoteException;
import org.abondar.experimental.dapp.vote.service.EthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.exceptions.TransactionException;

import java.util.ArrayList;
import java.util.List;

public class Handler {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    private final EthereumService ethereumService;

    public Handler(EthereumService voteService) {
        this.ethereumService = voteService;
    }

    public BodyHandler bodyHandler() {
        return BodyHandler.create();
    }


    public void handleVote(RoutingContext rc) {
        var option = rc.pathParam("option");

        ethereumService.vote(option)
                .subscribe(tr -> {
                            var resp = new JsonObject();
                            resp.put("msg", "Vote has been made");
                            resp.put("block", tr.getBlockNumber());
                            resp.put("hash", tr.getBlockHash());
                            sendSuccess(rc, resp);
                        },
                        err -> {
                            if (err instanceof VoteException) {
                                sendBadRequest(rc, err);
                            } else if (err instanceof TransactionException) {
                                sendBadGateway(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });


    }

    public void handleRegister(RoutingContext rc) {
        var voter = rc.getBodyAsJson();
        var address = voter.getString("address");

        ethereumService.registerVoter(address)
                .subscribe(tr -> {
                            var resp = new JsonObject();
                            resp.put("msg", String.format("Voter %s registered", voter.getString("voter")));
                            resp.put("block", tr.getBlockNumber());
                            resp.put("hash", tr.getBlockHash());

                            sendSuccess(rc, resp);
                        },
                        err -> {
                            if (err instanceof TransactionException) {
                                sendBadRequest(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });
    }

    public void handleWinner(RoutingContext rc) {
        ethereumService.getWinner()
                .subscribe(votes -> {
                            var resp = new JsonObject();
                            resp.put("votes", votes.intValue());
                            sendSuccess(rc, resp);

                        },
                        err -> {
                            if (err instanceof TransactionException) {
                                sendBadRequest(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });
    }

    public void handleOptions(RoutingContext rc, List<String> options) {
        var body = new JsonObject();
        var arr = new JsonArray(new ArrayList<>(options));
        body.put("options", arr);

        sendSuccess(rc, body);
    }

    private void sendBadGateway(RoutingContext rc, Throwable err) {
        logger.error(err.getMessage());
        rc.fail(502, err);
    }

    private void sendServerError(RoutingContext rc, Throwable err) {
        logger.error(err.getMessage());
        rc.fail(500, err);
    }

    private void sendBadRequest(RoutingContext rc, Throwable err) {
        logger.error(err.getMessage());
        rc.fail(400, err);
    }

    private void sendSuccess(RoutingContext ctx, JsonObject body) {
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .end(body.encode());
    }

}
