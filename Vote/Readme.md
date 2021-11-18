# Vote Dapp

Dapp working with vote [smart contract](../Smartcontracts/Readme.md) 

## Api

```
POST http://localhost:8080/register - register vote member

Request

{
"voter": "Some name",
"address": "Member address in blockhain"
}

Response 200 - voter regitered
{
"msg": "Voter Some name registered",
"block": 0,
"hash": "block hash"
}

Response 500 - registration failed (already registered or voted)
Response 502 - error connecting to blockchain

PUT localhost:8080/vote/:proposal - make vote
{
"address": "Member address in blockhain"
}

Response 200 - vote made
{
"msg": "Vote has been made",
"block": 0,
"hash": "block hash"
}

Response 400 - proposal doesn't exist
Response 500 - voting failed (already voted or not registered)
Response 502 - error connecting to blockchain

GET http://localhost:8080/winner?address=<voter address> - fetch user

Response 200
{
"winner": "Some winner"
}

Response 500 - fetching failed (voter not registered voting not happened)
Response 502 - error connecting to blockchain

http://localhost:8080/proposals - fetch list of proposals

Response 200
{
"proposals": [
"Alex",
"Bob",
"Wilhelm",
"Mark",
"Max",
"David",
"Anton"
]
}
```
## Build and run

Smart contract build and deploy check [here](../Smartcontracts/Readme.md) 

```yaml
./gradlew clean build

java -jar build/libs/Vote-1.0-SNAPSHOT-all.jar 
```

Generate smart contract wrapper
```yaml
./gradlew generateWrapper
```









