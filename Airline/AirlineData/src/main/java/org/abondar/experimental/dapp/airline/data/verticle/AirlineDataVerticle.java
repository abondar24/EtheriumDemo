package org.abondar.experimental.dapp.airline.data.verticle;

import io.reactivex.Completable;
import io.vertx.reactivex.core.AbstractVerticle;

public class AirlineDataVerticle extends AbstractVerticle {


    @Override
    public Completable rxStart(){
        return  Completable.complete();
    }
}
