package org.abondar.experimental.dapp.airline.data.service;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface SeatService {

    Flowable<JsonObject> fetchFlights(int skip,int limit);

    Completable updateSeats(int flightId, int seats);

    void insertData() throws Exception;

    void cleanDb();
}
