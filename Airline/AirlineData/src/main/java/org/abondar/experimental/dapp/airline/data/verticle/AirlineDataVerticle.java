package org.abondar.experimental.dapp.airline.data.verticle;

public class AirlineDataVerticle extends AbstractVerticle{


    @Override
    public Completable rxStart(){
        return  Completable.complete();
    }
}
