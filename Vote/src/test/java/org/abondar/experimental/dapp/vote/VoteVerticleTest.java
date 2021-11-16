package org.abondar.experimental.dapp.vote;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.reactivex.core.Vertx;
import org.abondar.experimental.dapp.vote.verticle.VoteVerticle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith({VertxExtension.class})
public class VoteVerticleTest {

    private static RequestSpecification spec;

    @BeforeAll
    public static void prepare(Vertx vertx) {
        spec = new RequestSpecBuilder()
                .addFilters(List.of(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                .setBaseUri("http://localhost:8080")
                .build();

        var verticle = new VoteVerticle(new EthereumServiceTestImpl());
        vertx.deployVerticle(verticle);
    }

    @Test
    public void registerTest() {
        var json = new JsonObject()
                .put("voter","Alex")
                .put("address", "test");

        given(spec)
                .contentType(ContentType.JSON)
                .body(json.toString())
                .post("/register")
                .then()
                .assertThat()
                .statusCode(200)
                .body("msg",is("Voter Alex registered"))
                .body("block",is(1))
                .body("hash",is("Test"));

    }


    @Test
    public void voteTest() {
        given(spec)
                .contentType(ContentType.JSON)
                .put("/vote/Armen")
                .then()
                .assertThat()
                .statusCode(200)
                .body("msg",is("Vote has been made"))
                .body("block",is(1))
                .body("hash",is("Test"));

    }


    @Test
    public void winnerTest() {

        given(spec)
                .contentType(ContentType.JSON)
                .get("/winner")
                .then()
                .assertThat()
                .statusCode(200)
                .body("votes",is(1));

    }

    @Test
    public void optionsTest() {

        given(spec)
                .contentType(ContentType.JSON)
                .get("/options")
                .then()
                .assertThat()
                .statusCode(200)
                .body("options",hasSize(7));

    }
}
