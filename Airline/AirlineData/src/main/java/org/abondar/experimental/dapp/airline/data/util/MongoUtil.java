package org.abondar.experimental.dapp.airline.data.util;

public class MongoUtil {

    private MongoUtil(){}

    public static final String MONGO_HOST = "localhost";

    public static final int MONGO_PORT = 27017;

    public static final String MONGO_USER = "admin";

    public static final String MONGO_PASS = "admin123";

    public static final String DATABASE_NAME = "airline";

    public static final String FLIGHT_COLLECTION = "flight";

    public static final String ID = "_id";

    public static final String SET = "$set";

    public static final String FLIGHT_ID = "FlightID";

    public static final String SEATS_AVAILABLE = "SeatsAvail";
}
