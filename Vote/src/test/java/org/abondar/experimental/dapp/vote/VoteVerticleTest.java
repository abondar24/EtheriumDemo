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
import static org.abondar.experimental.dapp.vote.util.ApiUtil.ADDRESS_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.BLOCK_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.HASH_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.MSG_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.OPTIONS_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.OPTION_ENDPOINT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.REGISTER_ENDPOINT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.SERVER_PORT;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.VOTER_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.VOTES_FIELD;
import static org.abondar.experimental.dapp.vote.util.ApiUtil.WINNER_ENDPOINT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@ExtendWith({VertxExtension.class})
public class VoteVerticleTest {

    private static RequestSpecification spec;

    @BeforeAll
    public static void prepare(Vertx vertx) {
        spec = new RequestSpecBuilder()
                .addFilters(List.of(new ResponseLoggingFilter(), new RequestLoggingFilter()))
                .setBaseUri("http://localhost:"+SERVER_PORT)
                .build();

        var verticle = new VoteVerticle(new EthereumServiceTestImpl());
        vertx.deployVerticle(verticle);
    }

    @Test
    public void registerTest() {
        var json = new JsonObject()
                .put(VOTER_FIELD,"Alex")
                .put(ADDRESS_FIELD, "test");

        given(spec)
                .contentType(ContentType.JSON)
                .body(json.toString())
                .post(REGISTER_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(200)
                .body(MSG_FIELD,is("Voter Alex registered"))
                .body(BLOCK_FIELD,is(1))
                .body(HASH_FIELD,is("Test"));

    }


    @Test
    public void voteTest() {
        var json = new JsonObject()
                .put(ADDRESS_FIELD, "test");

        given(spec)
                .contentType(ContentType.JSON)
                .body(json.toString())
                .put("/vote/Armen")
                .then()
                .assertThat()
                .statusCode(200)
                .body(MSG_FIELD,is("Vote has been made"))
                .body(BLOCK_FIELD,is(1))
                .body(HASH_FIELD,is("Test"));

    }


    @Test
    public void winnerTest() {

        given(spec)
                .contentType(ContentType.JSON)
                .get(WINNER_ENDPOINT+"?address=test")
                .then()
                .assertThat()
                .statusCode(200)
                .body(VOTES_FIELD,is(1));

    }

    @Test
    public void optionsTest() {

        given(spec)
                .contentType(ContentType.JSON)
                .get(OPTION_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(200)
                .body(OPTIONS_FIELD,hasSize(7));

    }
}
