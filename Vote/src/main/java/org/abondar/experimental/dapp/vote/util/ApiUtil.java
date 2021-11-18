package org.abondar.experimental.dapp.vote.util;

public class ApiUtil {

    private ApiUtil(){}

    public static final int SERVER_PORT = 8080;

    public static final String REGISTER_ENDPOINT = "/register";

    public static final String PROPOSAL_PARAM = "proposal";

    public static final String VOTE_ENDPOINT = "/vote/:"+ PROPOSAL_PARAM;

    public static final String WINNER_ENDPOINT = "/winner";

    public static final String PROPOSAL_ENDPOINT = "/proposals";

    public static final String MSG_FIELD = "msg";

    public static final String BLOCK_FIELD = "block";

    public static final String HASH_FIELD = "hash";

    public static final String ADDRESS_FIELD = "address";

    public static final String VOTER_FIELD = "voter";

    public static final String WINNER_FIELD = "winner";

    public static final String PROPOSALS_FIELD = "proposals";

    public static final String MSG_VOTE = "Vote has been made";

    public static final String MSG_REGISTER = "Voter %s registered";

}
