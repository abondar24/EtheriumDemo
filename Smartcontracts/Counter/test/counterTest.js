const Counter = artifacts.require('../contracts/Counter.sol')
const truffleAssert = require('truffle-assertions')

contract('Counter',function (){

    let counter

    beforeEach('Setup contract',async function (){
         counter = await Counter.new()
         await counter.init(100)
    })

    it ('Successful init', async function (){
        assert.equal(await counter.get(),100)
    })

    it ('Counter decrement', async function (){
        await counter.decr(5)
        assert.equal(await counter.get(),95)
    })

    it ('Counter increment', async function (){
        await counter.incr(5)
        assert.equal(await counter.get(),105)
    })


})
