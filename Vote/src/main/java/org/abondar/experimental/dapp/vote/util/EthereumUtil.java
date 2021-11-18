package org.abondar.experimental.dapp.vote.util;

import java.math.BigInteger;

public class EthereumUtil {

    private EthereumUtil(){

    }

    public static final String BLOCKCHAIN_URL = "http://localhost:7545";


    //based on ganache workspace setup
    public static final BigInteger GAS_PRICE_GN = BigInteger.valueOf(10);

    public static final BigInteger GAS_LIMIT_GN =BigInteger.valueOf(800000000000L);

    public static final String CHAIRPERSON_ADDRESS = "0xCE26baaa956FB1C1548285D17DAF10A00c5B2F47";
}
