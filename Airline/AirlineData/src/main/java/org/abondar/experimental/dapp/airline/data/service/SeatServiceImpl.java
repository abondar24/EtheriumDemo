package org.abondar.experimental.dapp.airline.data.service;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.mongo.MongoClient;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.DATABASE_NAME;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.FLIGHT_COLLECTION;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.FLIGHT_ID;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.MONGO_HOST;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.MONGO_PASS;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.MONGO_PORT;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.MONGO_USER;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.SEATS_AVAILABLE;
import static org.abondar.experimental.dapp.airline.data.util.MongoUtil.SET;

public class SeatServiceImpl implements SeatService {

    private final MongoClient mongoClient;

    public SeatServiceImpl(Vertx vertx) {
        var config = new JsonObject();
        config.put("host", MONGO_HOST);
        config.put("port", MONGO_PORT);
        config.put("username", MONGO_USER);
        config.put("password", MONGO_PASS);

        config.put("db_name", DATABASE_NAME);

        this.mongoClient = MongoClient.createShared(vertx, config);
    }

    @Override
    public Flowable<JsonObject> fetchFlights(int skip,int limit) {

        var findOptions = new FindOptions();
        findOptions.setLimit(limit);
        findOptions.setSort(new JsonObject().put("_id", 1));
        findOptions.setSkip(skip);

        return mongoClient.rxFindWithOptions(FLIGHT_COLLECTION, new JsonObject(),findOptions)
                .map(res -> {
                    var flights = new JsonObject();
                    flights.put("flights", new JsonArray(res));
                    return flights;
                })
                .toFlowable();

    }

    @Override
    public Completable updateSeats(int flightId, int seats) {
        var query = new JsonObject();
        query.put(FLIGHT_ID,flightId);

        var updates = new JsonObject();
        updates.put(SEATS_AVAILABLE,seats);

        var updateSet = new JsonObject();
        updateSet.put(SET, updates);

        return mongoClient.rxFindOneAndUpdate(FLIGHT_COLLECTION,query,updateSet).ignoreElement();
    }

    @Override
    public void cleanDb() {
        mongoClient.dropCollection(FLIGHT_COLLECTION);
    }

    @Override
    public void insertData() throws Exception{
        var buf = SeatServiceImpl.class.getClassLoader().getResource("data.json");
        var json = Files.readString(Path.of(buf.toURI()));


        var data = new JsonArray(json);
        data.stream().forEach(dt->{
            mongoClient.insert(FLIGHT_COLLECTION,(JsonObject) dt);
        });
    }
}
