package org.abondar.experimental.dapp.vote.service;

import io.reactivex.Flowable;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface EthereumService {


    Flowable<TransactionReceipt> registerVoter(String address);

    Flowable<TransactionReceipt> vote(String voteOption,String address);

    Flowable<BigInteger> getWinner(String address);
}
