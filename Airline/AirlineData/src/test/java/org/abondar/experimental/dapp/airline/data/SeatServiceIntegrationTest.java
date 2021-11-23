package org.abondar.experimental.dapp.airline.data;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.reactivex.core.Vertx;
import org.abondar.experimental.dapp.airline.data.service.SeatService;
import org.abondar.experimental.dapp.airline.data.service.SeatServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({VertxExtension.class})
public class SeatServiceIntegrationTest {

    private static SeatService seatService;

    @BeforeAll
    public static void init(Vertx vertx) {
        seatService = new SeatServiceImpl(vertx);
    }

    @BeforeEach
    public void initData() throws Exception {

        seatService.insertData();
    }

    @AfterEach
    public void clean() {
        seatService.cleanDb();
    }


    @Test
    public void fetchFlightsTest(VertxTestContext testContext) throws Exception {
        Thread.sleep(2000);
        var res = seatService.fetchFlights();

        res.subscribe(
                flights -> {
                    var data = flights.getJsonArray("flights");
                    assertEquals(6, data.size());
                    testContext.completeNow();
                },
                testContext::failNow
        );

    }

    @Test
    public void updateSeatsTest(VertxTestContext testContext) throws Exception {
        Thread.sleep(2000);
        var res = seatService.updateSeats(1, 7);

        res.subscribe(
                testContext::completeNow,
                testContext::failNow);

    }
}
