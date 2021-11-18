package org.abondar.experimental.dapp.vote.verticle;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.abondar.experimental.dapp.vote.exception.BlockhainConnectException;
import org.abondar.experimental.dapp.vote.exception.VoteException;
import org.abondar.experimental.dapp.vote.service.EthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.exceptions.TransactionException;

import java.util.ArrayList;
import java.util.List;

import static org.abondar.experimental.dapp.vote.util.ApiUtil.ADDRESS_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.BLOCK_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.HASH_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.MSG_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.MSG_REGISTER;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.MSG_VOTE;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.PROPOSALS_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.PROPOSAL_PARAM;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.VOTER_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.WINNER_FIELD;

public class Handler {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    private final EthereumService ethereumService;

    public Handler(EthereumService voteService) {
        this.ethereumService = voteService;
    }

    public BodyHandler bodyHandler() {
        return BodyHandler.create();
    }


    public void handleRegister(RoutingContext rc) {
        var voter = rc.getBodyAsJson();
        var address = voter.getString(ADDRESS_FIELD);

        ethereumService.registerVoter(address)
                .subscribe(tr -> {
                            var resp = new JsonObject();
                            resp.put(MSG_FIELD, String.format(MSG_REGISTER, voter.getString(VOTER_FIELD)));
                            resp.put(BLOCK_FIELD, tr.getBlockNumber());
                            resp.put(HASH_FIELD, tr.getBlockHash());

                            sendSuccess(rc, resp);
                        },
                        err -> {
                           if (err instanceof BlockhainConnectException) {
                                sendBadGateway(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });
    }

    public void handleVote(RoutingContext rc) {
        var proposals = rc.pathParam(PROPOSAL_PARAM);
        var address = rc.getBodyAsJson().getString(ADDRESS_FIELD);

        ethereumService.vote(proposals, address)
                .subscribe(tr -> {
                            var resp = new JsonObject();
                            resp.put(MSG_FIELD, MSG_VOTE);
                            resp.put(BLOCK_FIELD, tr.getBlockNumber());
                            resp.put(HASH_FIELD, tr.getBlockHash());
                            sendSuccess(rc, resp);
                        },
                        err -> {
                            if (err instanceof VoteException) {
                                sendBadRequest(rc, err);
                            } else if (err instanceof BlockhainConnectException) {
                                sendBadGateway(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });


    }


    public void handleWinner(RoutingContext rc) {
        var address = rc.queryParam(ADDRESS_FIELD).get(0);
        ethereumService.getWinner(address)
                .subscribe(winner -> {
                            var resp = new JsonObject();
                            resp.put(WINNER_FIELD, winner);
                            sendSuccess(rc, resp);

                        },
                        err -> {
                             if (err instanceof BlockhainConnectException) {
                                sendBadGateway(rc, err);
                            } else {
                                sendServerError(rc, err);
                            }
                        });
    }

    public void handleProposals(RoutingContext rc, List<String> options) {
        var body = new JsonObject();
        var arr = new JsonArray(new ArrayList<>(options));
        body.put(PROPOSALS_FIELD, arr);

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
