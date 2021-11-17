package org.abondar.experimental.dapp.vote.util;

import java.math.BigInteger;

public class EthereumUtil {

    private EthereumUtil(){

    }

    public static final String BLOCKCHAIN_URL = "http://localhost:7545";


    //based on ganache workspace setup
    public static final BigInteger GAS_PRICE_GN = BigInteger.valueOf(10);

    public static final BigInteger GAS_LIMIT_GN =BigInteger.valueOf(800000000000L);
}
