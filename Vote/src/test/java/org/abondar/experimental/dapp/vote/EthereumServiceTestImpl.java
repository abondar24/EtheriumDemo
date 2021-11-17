package org.abondar.experimental.dapp.vote;

import io.reactivex.Flowable;
import org.abondar.experimental.dapp.vote.service.EthereumService;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public class EthereumServiceTestImpl implements EthereumService {


    @Override
    public Flowable<TransactionReceipt> registerVoter(String address) {
        var res = new TransactionReceipt();
        res.setBlockHash("Test");
        res.setBlockNumber("1");

        return Flowable.just(res);
    }

    @Override
    public Flowable<TransactionReceipt> vote(String voteOption, String address) {
        var res = new TransactionReceipt();
        res.setBlockHash("Test");
        res.setBlockNumber("1");

        return Flowable.just(res);
    }

    @Override
    public Flowable<BigInteger> getWinner(String address) {
        return Flowable.just(BigInteger.ONE);
    }
}
