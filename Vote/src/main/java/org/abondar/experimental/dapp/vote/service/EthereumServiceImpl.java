package org.abondar.experimental.dapp.vote.service;

import io.reactivex.Flowable;
import org.abondar.experimental.dapp.vote.contract.Vote;
import org.abondar.experimental.dapp.vote.exception.ContractException;
import org.abondar.experimental.dapp.vote.exception.VoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.List;

import static org.abondar.experimental.dapp.vote.util.EthereumUtil.BLOCKCHAIN_URL;
import static org.abondar.experimental.dapp.vote.util.EthereumUtil.CHAIRPERSON_ADDRESS;
import static org.abondar.experimental.dapp.vote.util.EthereumUtil.GAS_LIMIT_GN;
import static org.abondar.experimental.dapp.vote.util.EthereumUtil.GAS_PRICE_GN;

public class EthereumServiceImpl implements EthereumService {


    private static final Logger logger = LoggerFactory.getLogger(EthereumServiceImpl.class);
    private final List<String> voteProposals;

    private final Web3j web3;

    public EthereumServiceImpl(List<String> voteProposals) {
        this.voteProposals = voteProposals;
        this.web3 = Web3j.build(new HttpService(BLOCKCHAIN_URL));
    }

    @Override
    public Flowable<TransactionReceipt> registerVoter(String address) {
        var contract = loadContract(CHAIRPERSON_ADDRESS);
        return contract.register(address).flowable();

    }

    @Override
    public Flowable<TransactionReceipt> vote(String voteOption, String address) {
        if (!voteProposals.contains(voteOption)) {
            logger.error("Member not exists");
            return Flowable.error(new VoteException("Member not exists"));

        }

        var proposal = voteProposals.indexOf(voteOption);
        var contract = loadContract(address);
        return contract.vote(BigInteger.valueOf(proposal)).flowable();
    }

    @Override
    public Flowable<String> getWinner(String address) {
        var contract = loadContract(address);
        return contract.calcWinner()
                .flowable()
                .flatMap(winner-> Flowable.just(voteProposals.get(winner.intValue())));

    }

    private Vote loadContract(String address) {
        try {
            var tm = new ClientTransactionManager(web3, address);
            var provider = new StaticGasProvider(GAS_PRICE_GN, GAS_LIMIT_GN);

            return Vote.load(Vote.getPreviouslyDeployedAddress("5777"), web3, tm, provider);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ContractException(ex.getMessage());
        }
    }

}
