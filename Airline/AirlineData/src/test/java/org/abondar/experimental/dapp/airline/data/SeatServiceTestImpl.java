package org.abondar.experimental.dapp.airline.data;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.abondar.experimental.dapp.airline.data.service.SeatService;

public class SeatServiceTestImpl implements SeatService {
    @Override
    public Flowable<JsonObject> fetchFlights() {
        var json = new JsonObject();
        json.put("flights",new JsonArray());
        return Flowable.just(json);
    }

    @Override
    public Completable updateSeats(int flightId, int seats) {
        return Completable.complete();
    }

    @Override
    public void insertData() {

    }

    @Override
    public void cleanDb() {

    }
}
