package org.abondar.experimental.dapp.airline.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.reactivex.core.Vertx;
import org.abondar.experimental.dapp.airline.data.verticle.AirlineDataVerticle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.FLIGHTS_ENDPOINT;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.FLIGHT_ID_FIELD;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.OFFSET_PARAM;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SEATS_ENDPOINT;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SEATS_FIELD;
import static org.abondar.experimental.dapp.airline.data.util.ApiUtil.SERVER_PORT;

@ExtendWith({VertxExtension.class})
public class AirlineDataApiTest {


    private static RequestSpecification spec;

    @BeforeAll
    public static void initMockServer(Vertx vertx) throws Exception {
        spec = new RequestSpecBuilder()
                .addFilters(List.of(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                .setBaseUri("http://localhost:" + SERVER_PORT)
                .build();

        var seatService = new SeatServiceTestImpl();
        var verticle = new AirlineDataVerticle(seatService);
        vertx.deployVerticle(verticle);
    }

    @Test
    public void fetchFlightsTest() {
        given(spec)
                .contentType(ContentType.JSON)
                .get(FLIGHTS_ENDPOINT+"?offset=0&limit=2")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void fetchFlightsBadRequestTest() {
        given(spec)
                .contentType(ContentType.JSON)
                .get(FLIGHTS_ENDPOINT+"?offset=test&limit=2")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void updateSeatsTest() {
        var body = new JsonObject();
        body.put(FLIGHT_ID_FIELD, 1);
        body.put(SEATS_FIELD, 2);

        given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .put(SEATS_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void updateSeatsBadRequestTest() {
        given(spec)
                .contentType(ContentType.JSON)
                .put(SEATS_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(400);
    }
}
