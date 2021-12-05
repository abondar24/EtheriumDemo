# Airline Data

Service saving and updating non-blockchain data for Airline dapp

## API

```
GET http://localhost:8020/flights?offset=0&limit=1 - fetch existing flights

Response: 
  200 - flights
  {"flights": [
   {
    "FlightID": 1,
    "Airline": "AirlineA",
    "FromCity": "BUF",
    "ToCity": "NYC",
    "DepTime": "6:00 AM",
    "SeatsAvail": 8
  }]
   }
   
  400 - wrong query param
  500 - server error 
  
PUT http://localhost:8020/seats - update seats number for flight

Body: {
   "flightId": 1,
   "seats": 7
  }


Response: 
  200 - update successfull
  400 - wrong body
  500 - server error 
  
```
## Build and run

```
../gradlew clean build -x test

java -jar build/libs/AirlineData-1.0-SNAPSHOT-all.jar 
```

### Run  tests
```
../gradlew clean test
```

## Import data to database

```
mongoimport --username <username> --password <password> --authenticationDatabase admin --db airline --collection flight --drop --type json  --file src/main/resources/data.json --jsonArray

```
