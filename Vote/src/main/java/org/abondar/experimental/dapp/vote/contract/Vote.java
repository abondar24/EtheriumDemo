package org.abondar.experimental.dapp.vote.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Vote extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50604051610a85380380610a858339818101604052810190610032919061017e565b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506002600160008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000018190555060005b8181101561013c57600260405180602001604052806000815250908060018154018082558091505060019003906000526020600020016000909190919091506000820151816000015550508080610134906101da565b9150506100de565b5050610223565b600080fd5b6000819050919050565b61015b81610148565b811461016657600080fd5b50565b60008151905061017881610152565b92915050565b60006020828403121561019457610193610143565b5b60006101a284828501610169565b91505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b60006101e582610148565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff821415610218576102176101ab565b5b600182019050919050565b610853806102326000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80630121b93f146100465780633a6af799146100625780634420e48614610080575b600080fd5b610060600480360381019061005b91906104e9565b61009c565b005b61006a61021c565b6040516100779190610525565b60405180910390f35b61009a6004803603810190610095919061059e565b61029c565b005b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015411610121576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161011890610628565b60405180910390fd5b6000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090508060010160009054906101000a900460ff16156101b6576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101ad90610694565b60405180910390fd5b60018160010160006101000a81548160ff0219169083151502179055508181600201819055508060000154600283815481106101f5576101f46106b4565b5b9060005260206000200160000160008282546102119190610712565b925050819055505050565b6000806000905060005b600280549050811015610297578160028281548110610248576102476106b4565b5b9060005260206000200160000154111561028457600281815481106102705761026f6106b4565b5b906000526020600020016000015491508092505b808061028f90610768565b915050610226565b505090565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146102f457600080fd5b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff1615610384576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161037b90610694565b60405180910390fd5b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015414610409576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610400906107fd565b60405180910390fd5b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001819055506000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548160ff02191690831515021790555050565b600080fd5b6000819050919050565b6104c6816104b3565b81146104d157600080fd5b50565b6000813590506104e3816104bd565b92915050565b6000602082840312156104ff576104fe6104ae565b5b600061050d848285016104d4565b91505092915050565b61051f816104b3565b82525050565b600060208201905061053a6000830184610516565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061056b82610540565b9050919050565b61057b81610560565b811461058657600080fd5b50565b60008135905061059881610572565b92915050565b6000602082840312156105b4576105b36104ae565b5b60006105c284828501610589565b91505092915050565b600082825260208201905092915050565b7f4e6f742072656769737465726564000000000000000000000000000000000000600082015250565b6000610612600e836105cb565b915061061d826105dc565b602082019050919050565b6000602082019050818103600083015261064181610605565b9050919050565b7f416c726561647920766f74656400000000000000000000000000000000000000600082015250565b600061067e600d836105cb565b915061068982610648565b602082019050919050565b600060208201905081810360008301526106ad81610671565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061071d826104b3565b9150610728836104b3565b9250827fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0382111561075d5761075c6106e3565b5b828201905092915050565b6000610773826104b3565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8214156107a6576107a56106e3565b5b600182019050919050565b7f576569676874206e6f7420300000000000000000000000000000000000000000600082015250565b60006107e7600c836105cb565b91506107f2826107b1565b602082019050919050565b60006020820190508181036000830152610816816107da565b905091905056fea26469706673582212201c984307dc47af90c4b93ad744aa9a4b77f6b7b7582538fbf9cd60e1da5148c964736f6c63430008090033";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_CALCWINNER = "calcWinner";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x9924210AFab67aaCC5D5d31fAa40f4f1b20D48d4");
    }

    @Deprecated
    protected Vote(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Vote(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Vote(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Vote(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> register(String voter) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger proposal) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> calcWinner() {
        final Function function = new Function(FUNC_CALCWINNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Vote load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Vote(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Vote load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Vote(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Vote load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Vote(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Vote load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Vote(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Vote> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger proposalsNum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalsNum)));
        return deployRemoteCall(Vote.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Vote> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger proposalsNum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalsNum)));
        return deployRemoteCall(Vote.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Vote> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger proposalsNum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalsNum)));
        return deployRemoteCall(Vote.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Vote> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger proposalsNum) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalsNum)));
        return deployRemoteCall(Vote.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
