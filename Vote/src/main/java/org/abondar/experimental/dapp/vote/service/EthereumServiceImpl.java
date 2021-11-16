package org.abondar.experimental.dapp.vote.service;

import io.reactivex.Flowable;
import org.abondar.experimental.dapp.vote.contract.Vote;
import org.abondar.experimental.dapp.vote.exception.ContractException;
import org.abondar.experimental.dapp.vote.exception.VoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

public class EthereumServiceImpl implements EthereumService {

    private static final String BLOCKCHAIN_URL = "http://localhost:7545";

    private static final String CONTRACT_ADDRESS = "0x6e3A8Aa6c2204406dd896599cd6aa273b14167D9";

    private static final Logger logger = LoggerFactory.getLogger(EthereumServiceImpl.class);

    private Vote contract;

    private final List<String> voteOptions;

    public EthereumServiceImpl(List<String> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public void init() {
        Web3j web3 = Web3j.build(new HttpService(BLOCKCHAIN_URL));

        try {
            var credentials = Credentials.create(Keys.createEcKeyPair());
            var provider = new DefaultGasProvider();
            contract = Vote.load(CONTRACT_ADDRESS, web3, credentials, provider);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ContractException(ex.getMessage());
        }


    }

    @Override
    public Flowable<TransactionReceipt> registerVoter(String address) {
        return contract.register(address).flowable();

    }

    @Override
    public Flowable<TransactionReceipt> vote(String voteOption) {
        if (!voteOptions.contains(voteOption)) {
            logger.error("Member not exists");
            return Flowable.error(new VoteException("Member not exists"));

        }

        var proposal = voteOptions.indexOf(voteOption);

        return contract.vote(BigInteger.valueOf(proposal)).flowable();
    }

    @Override
    public Flowable<BigInteger> getWinner() {
        return contract.calcWinner().flowable();
    }

}
