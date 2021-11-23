package org.abondar.experimental.dapp.airline.data.verticle;

import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import org.abondar.experimental.dapp.airline.data.handler.Handler;
import org.abondar.experimental.dapp.airline.data.service.SeatService;
import org.abondar.experimental.dapp.airline.data.service.SeatServiceImpl;

import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.FLIGHTS_ENDPOINT;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SEATS_ENDPOINT;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SERVER_PORT;

public class AirlineDataVerticle extends AbstractVerticle {

    private SeatService seatService;

    public AirlineDataVerticle(){
    }

    public AirlineDataVerticle(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public Completable rxStart(){
        if (seatService==null){
            seatService = new SeatServiceImpl(vertx);
        }

        var handler = new Handler(seatService);

        var router = Router.router(vertx);
        router.put()
                .handler(handler.bodyHandler());
        router.put(SEATS_ENDPOINT).handler(handler::updateSeats);
        router.get(FLIGHTS_ENDPOINT).handler(handler::fetchFlights);

        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(SERVER_PORT)
                .ignoreElement();
    }
}
