package org.abondar.experimental.dapp.airline.data.handler;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.abondar.experimental.dapp.airline.data.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.FLIGHT_ID_FIELD;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SEATS_FIELD;

public class Handler {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);


    private final SeatService seatService;


    public Handler(SeatService seatService) {
        this.seatService = seatService;
    }


    public BodyHandler bodyHandler() {
        return BodyHandler.create();
    }

    public void fetchFlights(RoutingContext context) {
        seatService.fetchFlights()
                .subscribe(
                        json -> sendFetched(context, json),
                        err -> sendFetchError(context, err)
                );
    }

    public void updateSeats(RoutingContext context) {
        var body = context.getBodyAsJson();
        if (body==null || !body.containsKey(FLIGHT_ID_FIELD) || !body.containsKey(SEATS_FIELD)) {
            sendBadRequest(context);
        }

        var flightId = body.getInteger(FLIGHT_ID_FIELD);
        var seats = body.getInteger(SEATS_FIELD);

        seatService.updateSeats(flightId, seats)
                .subscribe(
                        () -> sendSuccess(context),
                        err -> sendServerError(context, err)
                );
    }

    private void sendFetched(RoutingContext rc, JsonObject json) {
        rc.response()
                .putHeader("Content-Type", "application/json")
                .end(json.encode());
    }

    private void sendSuccess(RoutingContext rc) {
        rc.response()
                .setStatusCode(200)
                .end();
    }

    private void sendFetchError(RoutingContext rc, Throwable err) {
        if (err instanceof NoSuchFieldException) {
            rc.fail(404);
        } else {
            sendServerError(rc, err);
        }
    }

    private void sendBadRequest(RoutingContext rc) {
        rc.fail(400);
    }

    private void sendServerError(RoutingContext rc, Throwable err) {
        logger.error("Server err {}", err.getMessage());
        rc.fail(500);
    }


}
