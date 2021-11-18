
http://localhost:8080/options
{
"options": [
"Alex",
"Bob",
"Wilhelm",
"Mark",
"Max",
"David",
"Anton"
]
}

http://localhost:8080/register
{
"voter": "abondar101",
"address": "0xc973cCD2f5f52d1843A3a202E35CC6e7A3e50295"
}

{
"msg": "Voter abondar01 registered",
"block": 25,
"hash": "0xd593a54082c0d3b88605e458cacdec6090044c6dc15665a098ad2a88c28e8c66"
}

localhost:8080/vote/Alex
{
"address": "0xc973cCD2f5f52d1843A3a202E35CC6e7A3e50295"
}

{
"msg": "Vote has been made",
"block": 29,
"hash": "0x89539575281bcdd7daba1abb085d3f426678be5105b3c5d0dd36247f03fba31b"
}

http://localhost:8080/winner
{
"winner": "Alex"
}
