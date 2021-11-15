package org.abondar.experimental.dapp.vote.service;

import io.reactivex.Maybe;
import io.vertx.core.json.JsonObject;
import org.abondar.experimental.dapp.vote.exception.VoteException;

import java.util.Set;

public class VoteService {


    private final EthereumService ethereumService;
    Set<String> voteOptions;

    public VoteService(Set<String> voteOptions, EthereumService ethereumService) {
        this.voteOptions = voteOptions;
        this.ethereumService = ethereumService;
    }

    public Maybe<JsonObject> makeVote(String option) {
        if (!voteOptions.contains(option)) {
            return Maybe.error(new VoteException("Member not exists"));

        }

        var resp = new JsonObject();
        resp.put("msg","Vote has been made");
        return Maybe.just(resp);
        //todo call ethereum
    }

    public Maybe<JsonObject> registerVoter(String voter) {
        var resp = new JsonObject();
        resp.put("msg","Voter registered");
        return Maybe.just(resp);

    }

    public Maybe<JsonObject> calcWinner() {
        var resp = new JsonObject();
        resp.put("winner","");
        resp.put("votes",0);
        return Maybe.just(resp);
    }
}
