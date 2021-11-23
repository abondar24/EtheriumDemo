package org.abondar.experimental.dapp.airline.data.handler;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import org.abondar.experimental.dapp.airline.data.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.FLIGHT_ID_FIELD;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.LIMIT_PARAM;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.OFFSET_PARAM;
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
        var skip = context.queryParam(OFFSET_PARAM).get(0);
        var limit = context.queryParam(LIMIT_PARAM).get(0);

        try {
            seatService.fetchFlights(Integer.parseInt(skip),Integer.parseInt(limit))
                    .subscribe(
                            json -> sendFetched(context, json),
                            err -> sendServerError(context, err)
                    );
        } catch (NumberFormatException ex){
            logger.error(ex.getMessage());
            sendBadRequest(context);
        }


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



    private void sendBadRequest(RoutingContext rc) {
        rc.fail(400);
    }

    private void sendServerError(RoutingContext rc, Throwable err) {
        logger.error("Server err {}", err.getMessage());
        rc.fail(500);
    }


}
