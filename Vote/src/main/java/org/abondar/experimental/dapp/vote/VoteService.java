package org.abondar.experimental.dapp.vote;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

import java.util.Set;

public class VoteService {


    private final EthereumService ethereumService;
    Set<String> voteMembers;

    public VoteService(Set<String> voteMembers, EthereumService ethereumService) {
        this.voteMembers = voteMembers;
        this.ethereumService = ethereumService;
    }

    public Maybe<JsonObject> makeVote(String member) {
        if (!voteMembers.contains(member)) {
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
