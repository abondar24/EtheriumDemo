<template>
  <div class="container">
    <div class="alert alert-danger alert-dismissible " role="alert" v-if="notification.length >0">
      {{ notification }}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <div class="row" id="ctr">
      <div class="col-md-7">
        <h1 class="section-title"> Available Seats</h1>
        <table class="table table-responsive-md">
          <thead>
          <tr>
            <th>FID</th>
            <th>Airline</th>
            <th>From</th>
            <th>To</th>
            <th class="depTime">Departure Time</th>
            <th style="font-weight: 700;">Available Seats</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="flight in flights" :key="flight.FlightID">
            <td>
              <strong>
                {{ flight.FlightID }}
              </strong>
            </td>
            <td>
              <strong>
                {{ flight.Airline }}
              </strong>
            </td>
            <td>
              {{ flight.FromCity }}
            </td>
            <td>
              {{ flight.ToCity }}
            </td>
            <td class="depTime">
              {{ flight.DepTime }}
            </td>
            <td class="seats">
              {{ flight.SeatsAvail }}
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-md-5">
        <h1 class="section-title">Airline Consortium</h1>
        <div class="row">
          <h4>
            Airlines Registration
          </h4>
          <div class="input-group mb-3 ">
                <span class="input-group-text">
                  <font-awesome-icon icon="address-card"/>
                </span>
            <input type="text" class="form-control" placeholder="Enter address"
                   id="address"
                   v-model="senderAddress"/>
          </div>

        </div>
        <div class="row form-row">
          <div class="input-group mb-3 ">
                <span class="input-group-text">
                  <font-awesome-icon icon="plane"/>
                </span>
            <input type="text" class="form-control" placeholder="Enter deposit"
                   id="airlineDeposit"
                   v-model="airlineDeposit"/>
            <button id="register" class="btn btn-dark" v-on:click="handleRegister">
              Register
            </button>
          </div>
        </div>
        <div class="row form-row">
          <h4>
            Airline Unregister(only chairperson can do it)
          </h4>
          <div class="input-group mb-3">
                <span class="input-group-text">
                  <font-awesome-icon icon="user"/>
                </span>
            <input type="text" class="form-control" placeholder="Enter airline address"
                   id="airlineAddress"
                   v-model="airlineAddress"
            />
            <button id="unregister" class="btn btn-dark" v-on:click="handleUnregister">
              Unregister
            </button>
          </div>

        </div>
        <div class="row form-row">
          <h4 class="todo-name">
            Seat change request
          </h4>
          <div class="input-group mb-3">
                <span class="input-group-text">
                  <font-awesome-icon icon="share"/>
                </span>
            <input type="text" class="form-control d-inline" placeholder="Enter request id"
                   id="reqId" v-model="reqId">

            <input type="text" class="form-control d-inline" placeholder="Enter flight id"
                   id="flId" v-model="reqFlightId">
            <input type="text" class="form-control d-inline" placeholder="Enter passenger id"
                   id="psgId" v-model="reqPassId">
          </div>
          <div class="input-group mb-3">
            <input type="text" class="form-control d-inline" placeholder="Number of seats"
                   id="seatNum" v-model="reqNumSeats">
            <input type="text" class="form-control d-inline" placeholder="Destination Address"
                   id="dstAddr" v-model="reqDstAddr">
            <button id="request" class="btn btn-dark" v-on:click="handleRequest">
              Request
            </button>
          </div>
        </div>
        <div class="row form-row">
          <h4 class="todo-name">
            Seat change response
          </h4>
          <div class="input-group mb-3">
              <span class="input-group-text">
                   <font-awesome-icon icon="reply"/>
                </span>
            <input type="text" class="form-control d-inline" placeholder="Enter request id"
                   id="respId" v-model="respReqId">
            <input type="text" class="form-control d-inline" placeholder="Is success?"
                   id="sccId" v-model="respSuccess">
          </div>
          <div class="input-group mb-3">
            <input type="text" class="form-control d-inline" placeholder="Source Airline address"
                   id="srcAddr" v-model="respSrcAddr">
            <button id="response" class="btn btn-dark" v-on:click="handleResponse">
              Response
            </button>
          </div>

        </div>
        <div class="row form-row">
          <h4 class="todo-name">
            Settle payment
          </h4>
          <div class="input-group mb-3">
               <span class="input-group-text">
                    <font-awesome-icon icon="money-bill"/>
                </span>
            <input type="text" class="form-control d-inline" placeholder="Enter request id"
                   id="stReqId" v-model="stReqId">
            <input type="text" class="form-control d-inline" placeholder="Number of seats"
                   id="stSeats" v-model="stSeatsNum">
          </div>
          <div class="input-group mb-3">
            <input type="text" class="form-control d-inline" placeholder="Destination airline address"
                   id="stAddr" v-model="stAddr">
            <button id="settle" class="btn btn-dark" v-on:click="handleSettle">
              Settle
            </button>
          </div>
        </div>
        <div class="row form-row">
          <h4>
            Replenish Escrow
          </h4>
          <div class="input-group mb-3">
             <span class="input-group-text">
                    <font-awesome-icon icon="handshake"/>
                </span>
            <input type="text" class="form-control d-inline" placeholder="Enter the amount"
                   id="repl" v-model="rplAmt">
            <button id="replenish" class="btn btn-dark" v-on:click="handleReplenish">
              Replenish
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Web3 from 'web3'
import data from '../../Airline.json'
import TruffleContract from 'truffle-contract'
//import Airline from "@/components/Airline";

export default {
  name: 'Airline',
  data() {
    return {
      notification: '',
      airlineDeposit: '',
      airlineAddress: '',
      reqId: '',
      reqFlightId: '',
      reqPassId: '',
      reqNumSeats: null,
      reqDstAddr: '',
      respReqId: '',
      respSuccess: '',
      respSrcAddr: '',
      stReqId: '',
      stSeatsNum: null,
      stAddr: '',
      rplAmt: null,
      ethUrl: "http://127.0.0.1:7545",
      dataUrl: "http://127.0.0.1:8020",
      web3: null,
      contract: null,
      flights: [],
      requests: [],
      senderAddress: '',
      chairperson: ''
    }
  },
  mounted() {
    const web3Provider = new Web3.providers.HttpProvider(this.ethUrl);

    this.web3 = this.initWeb3(web3Provider)
    this.contract = this.initContract(web3Provider);
    this.fetchChairperson()

    this.fetchAirlineData(0, 6)
  },
  methods: {
    initWeb3(provider) {
      return new Web3(provider)
    },
    initContract(provider) {
      let contract = TruffleContract(data);
      contract.setProvider(provider);

      return contract;
    },
    fetchChairperson() {
      this.web3.eth.getAccounts()
          .then(acc => this.chairperson = acc[0]);
    },
    fetchAirlineData(offset, limit) {
      axios.get(this.dataUrl + "/flights?offset=" + offset + "&limit=" + limit)
          .then(response => {
            this.flights = response.data.flights;
          })
          .catch(err => this.notification = err.message);

    },
    handleRegister() {
      const deposit = this.airlineDeposit;

      const w3 = this.web3;
      const frAddr = this.senderAddress;
      this.contract.deployed()
          .then(function (instance) {
            return instance.register({value: w3.utils.toWei(deposit, "ether"), from: frAddr});
          })
          .catch(err => this.notification = err.message)
    },
    handleUnregister() {
      const addr = this.airlineAddress;
      const chair = this.chairperson;
      this.contract.deployed()
          .then(function (instance) {

            return instance.unregister(addr,{from:chair});
          })
          .then(function (result) {
            console.log(result)
          })
          .catch(err => {
            this.notification = err.message
          })
    },


    handleRequest() {
      const reqId = this.reqId;
      const flightId = this.reqFlightId;
      const passId = this.reqPassId;
      const numSeats = this.reqNumSeats;
      const dstAddr = this.reqDstAddr;
      const chair = this.chairperson;

      this.contract.deployed()
          .then(function (instance) {
            return instance.request(reqId, flightId, passId, numSeats, dstAddr,{from:chair});
          })
          .catch(err => this.notification = err.message)

      this.requests.push({
        id: reqId,
        flightId: flightId
      })
    },
    handleResponse() {
      const reqId = this.respReqId;
      const success = this.respSuccess;
      const srcAddr = this.respSrcAddr;
      const chair = this.chairperson;

      if (success==='true'){
        this.contract.deployed()
            .then(function (instance) {
              return instance.response(reqId, success, srcAddr,{from:chair});
            })
            .catch(err => this.notification = err.message)
      }
    },
    handleSettle() {
      // const reqId = this.stReqId;
      // const seats = this.stSeatsNum;
      // const airline = this.stAddr;
      //
      // const flightId = this.requests[reqId].flightId;
      //
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.settlePayment(reqId, airline, seats);
      //     })
      //     .then(function () {
      //           Airline.methods.updateSeats(seats,flightId)
      //     })
      //     .catch(err => this.notification = err.message)
    },
    handleReplenish() {
      //  const amount = this.rplAmt;
      //
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.replenishEscrow({ value: this.web3.toWei(amount, "ether") });
      //     })
      //     .catch(err => this.notification = err.message)
    },
    // updateSeats(seats, flightId) {
    //   const updFlights = this.flights;
    //   axios.put(this.dataUrl + "/seats", {
    //     "flightId": flightId,
    //     "seats": seats
    //   }).then(response => {
    //     if (response.status === 200) {
    //       updFlights[flightId].seats = seats;
    //     }
    //   })
    //       .catch(err => this.notification = err.message);
    //
    //   this.flights = updFlights;
    // }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.btn {
  width: 100px;
}

.form-row {
  margin-top: 10px;

}

#ctr {
  background-color: #ebeff2;
}


</style>
