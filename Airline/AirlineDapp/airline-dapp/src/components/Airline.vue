<template>
  <div class="container">
    <div class="alert alert-danger" role="alert" v-if="notification.length >0">
      {{ notification }}
    </div>
    <div class="row">
      <div class="col align-self-start">
        <h1 class="section-title"> Available Seats</h1>
        <div class="todo">

          <table class="table">
            <thead>
            <tr>
              <th>FID</th>
              <th>Airline</th>
              <th>From</th>
              <th>To</th>
              <th class="depTime">DepTime</th>
              <th style="font-weight: 700;">AvailSeats</th>
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
      </div>
      <div class="col align-self-start">
        <h1 class="section-title">Airline Consortium</h1>
        <div>
          <div class="row g-3 align-items-center">
            <h4>
             Airlines Registration
            </h4>
            <div class="col-auto">
              <font-awesome-icon icon="plane"/>
            </div>
            <div class="col-auto">
              <input type="text" class="form-control" placeholder="Enter deposit"
                     id="airlineDeposit"
                     v-model="airlineDeposit"
                     style="margin-right: 5px;"/>
            </div>
            <div class="col-auto">
              <button id="register" class="btn btn-info" v-on:click="handleRegister">
                Register
              </button>
            </div>
          </div>
          <div class="row g-3 align-items-center" style="margin-top: 10px">
            <h4>
             Airline Unregister
            </h4>
            <p>Only chairperson can do it</p>
            <div class="col-auto">
              <font-awesome-icon icon="user"/>
            </div>
            <div class="col-auto">
              <input type="text" class="form-control" placeholder="Enter airline address"
                     id="airlineAddress"
                     v-model="airlineAddress"
                     style="margin-right: 5px;"/>
            </div>
            <div class="col-auto">
              <button id="unregister" class="btn btn-info" v-on:click="handleUnregister">
                Unregister
              </button>
            </div>
          </div>
        </div>

        <!--        TODO: change-->
        <div class="todo functions">
          <div class="todo-search">
            <p>Change seats record</p>
          </div>
          <ul class="list">
            <li class="">
              <div class="todo-icon">
                <font-awesome-icon icon="share"/>
              </div>
              <div class="todo-content">
                <h4 class="todo-name">
                  <strong>Request</strong>
                </h4>
                <button id="request" class="btn btn-info" v-on:click="handleRequest">
                  Request
                </button>
                <div style="float: right; margin-right: 20px;">
                  <div class="form-group">
                    <input type="text" class="form-control d-inline" placeholder="Enter request id"
                           id="reqId" style="margin-right: 5px;" v-model="reqId">
                    <input type="text" class="form-control d-inline" placeholder="Enter flight id"
                           id="flId" style="margin-right: 5px;" v-model="reqFlightId">
                    <input type="text" class="form-control d-inline" placeholder="Enter passenger id"
                           id="psgId" style="margin-right: 5px;" v-model="reqPassId">
                  </div>
                  <div class="form-group" style="margin-top: 10px;">
                    <input type="text" class="form-control d-inline" placeholder="Number of seats"
                           id="seatNum" style="margin-right: 5px;" v-model="reqNumSeats">
                    <input type="text" class="form-control d-inline" placeholder="Destination Airline Address"
                           id="dstAddr" style="margin-right: 5px;" v-model="reqDstAddr">
                  </div>
                </div>
              </div>
            </li>
            <li class="">
              <div class="todo-icon">
                <font-awesome-icon icon="reply"/>
              </div>
              <div class="todo-content">
                <h4 class="todo-name">
                  <strong>
                    Response
                  </strong>
                </h4>
                <button id="response" class="btn btn-info" v-on:click="handleResponse">
                  Response
                </button>
                <div class="form-group d-inline" style="float: right; margin-right: 20px;">
                  <input type="text" class="form-control d-inline" placeholder="Enter request id"
                         id="respId" style="margin-right: 5px;" v-model="respReqId">
                  <input type="text" class="form-control d-inline" placeholder="Is success? (true/false)"
                         id="sccId" style="margin-right: 5px;" v-model="respSuccess">
                  <input type="text" class="form-control d-inline" placeholder="Source Airline address"
                         id="srcAddr" style="margin-right: 5px;" v-model="respSrcAddr">
                </div>
              </div>
            </li>
            <li class="">
              <div class="todo-icon">
                <font-awesome-icon icon="money-bill"/>
              </div>
              <div class="todo-content">
                <h4 class="todo-name">
                  <strong>Settle</strong>
                </h4>
                <button id="settle" class="btn btn-info" v-on:click="handleSettle">
                  Settle payment
                </button>
                <div class="form-group d-inline" style="float: right; margin-right: 20px;">
                  <input type="text" class="form-control d-inline" placeholder="Enter request id"
                         id="stReqId" style="margin-right: 5px;" v-model="stReqId">
                  <input type="text" class="form-control d-inline" placeholder="Number of seats"
                         id="stSeats" style="margin-right: 5px;" v-model="stSeatsNum">
                  <input type="text" class="form-control d-inline" placeholder="Destination airline address"
                         id="stAddr" style="margin-right: 5px;" v-model="stAddr">
                </div>
              </div>
            </li>

          </ul>
        </div>
        <div class="todo-functions">
          <div class="todo-search" style="border-radius: 6px; padding: 15px 25px;">
            <div class="todo-icon" style="margin-top: 5px;">
              <font-awesome-icon icon="handshake"/>
            </div>
            <p style="display: inline-block;">Replenish Escrow</p>
            <button id="replenish" class="btn btn-info" style="margin-top: -4px;" v-on:click="handleReplenish">
              Replenish
            </button>
            <div class="form-group d-inline" style="float: right; margin-right: 20px; margin-top: -4px;">
              <input type="text" class="form-control d-inline" placeholder="Enter the amount"
                     id="repl" style="margin-right: 5px;" v-model="rplAmt">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import Web3 from 'web3'
import axios from 'axios'
//import data from '../../Airline.json'
//import TruffleContract from 'truffle-contract'
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
      reqNumSeats: 0,
      reqDstAddr: '',
      respReqId: '',
      respSuccess: 'false',
      respSrcAddr: '',
      stReqId: '',
      stSeatsNum: 0,
      stAddr: '',
      rplAmt: 0,
      ethUrl: "http://127.0.0.1:7545",
      dataUrl: "http://127.0.0.1:8020",
      web3: null,
      contract: null,
      flights: [],
      requests: []
    }
  },
  mounted() {
    // const web3Provider = new Web3.providers.HttpProvider(this.ethUrl);
    // this.web3 = new Web3(web3Provider);
    //
    // this.contract = TruffleContract(data);
    // this.contract.setProvider(web3Provider);
    //
    //
    // this.contract.deployed()
    //     .then(function (instance) {
    //       return instance;
    //     })


    axios.get(this.dataUrl + "/flights?offset=0&limit=6")
        .then(response => {
          this.flights = response.data.flights;
        })
        .catch(err => this.notification = err.message);
  },
  methods: {
    handleRegister() {
      // const deposit = this.airlineDeposit;
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.register({value: this.web3.toWei(deposit, "ether")});
      //     })
      //     .catch(err => this.notification = err.message)
    },
    handleUnregister() {
      // const addr = this.airlineAddress;
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.unregister(addr);
      //     })
      //     .catch(err => this.notification = err.message)
    },
    handleRequest() {
      // const reqId = this.reqId;
      // const flightId = this.reqFlightId;
      // const passId = this.reqPassId;
      // const numSeats = this.reqNumSeats;
      // const dstAddr = this.reqDstAddr;
      //
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.request(reqId, flightId, passId, numSeats, dstAddr);
      //     })
      //     .catch(err => this.notification = err.message)
      //
      // this.requests.push({
      //   id: reqId,
      //   flightId: flightId
      // })
    },
    handleResponse() {
      // const reqId = this.respReqId;
      // const success = this.respSuccess;
      // const srcAddr = this.respSrcAddr;
      //
      // this.contract.deployed()
      //     .then(function (instance) {
      //       return instance.response(reqId, success, srcAddr);
      //     })
      //     .catch(err => this.notification = err.message)
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
.btn{
  width: 100px;
}
</style>
