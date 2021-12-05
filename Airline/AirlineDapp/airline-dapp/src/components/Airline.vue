<template>
  <div class="container">
    <div class="alert alert-danger" role="alert" v-if="notification.length >0">
      {{ notification }}
    </div>
    <div class="row">
      <div class="col-lg-4">
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
      <div class="col-lg-8">
        <h1 class="section-title">Airline Consortium</h1>
        <div class="todo functions">
          <div class="todo-search">
            <p>Registration</p>
          </div>
          <ul class="list">
            <li class="">
              <div class="todo-icon">
                <font-awesome-icon icon="plane"/>
              </div>
              <div class="todo-content">
                <h4 class="todo-name">
                  <strong>Airlines Registration</strong>
                </h4>
                <button id="register" class="btn btn-info" v-on:click="handleRegister">
                  Register
                </button>
                <div class="form-group d-inline" style="float: right; margin-right: 20px;">
                  <input type="text" class="form-control" placeholder="Enter deposit"
                         id="airlineDeposit"
                         v-model="airlineDeposit"
                         style="margin-right: 5px;"/>
                </div>
              </div>
            </li>
            <li class="">
              <div class="todo-icon">
                <font-awesome-icon icon="user"/>
              </div>
              <div class="todo-content">
                <h4 class="todo-name">
                  <strong>Chairperson can unregister</strong>
                </h4>
                <button id="unregister" class="btn btn-info" v-on:click="handleUnregister">
                  Unregister
                </button>
                <div class="form-group d-inline" style="float: right; margin-right: 20px;">
                  <input type="text" class="form-control" placeholder="Enter airline address"
                         id="airlineAddress"
                         v-model="airlineAddress"
                         style="margin-right: 5px;"/>
                </div>
              </div>
            </li>
          </ul>
        </div>
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
.container {
  width: 96%;
}

.form-group.focus .form-control, .form-group.focus .select2-search input[type="text"], .select2-search .form-group.focus input[type="text"], .form-control:focus, .select2-search input[type="text"]:focus {
  border-color: #3498db;
}

.section-title {
  font: 900 32px/40px "Helvetica Neue", Helvetica, Arial, sans-serif;
  margin: 50px 0;
  text-align: center;
}

.todo-search {
  background: #fff;
  color: #34495e;
}

.todo-search p {
  font-weight: 600;
  font-size: 20px;
  margin: 0;
}

.todo ul.list {
  margin: 0;
  padding: 0;
  list-style-type: none;
  border-radius: 0 0 6px 6px;
}

.todo ul.list > li {
  background: #34495e;
  background-size: 20px 20px;
  cursor: pointer;
  font-size: 14px;
  line-height: 1.214;
  margin-top: 2px;
  padding: 18px 25px 21px 25px;
  position: relative;
  transition: .25s;
}

.todo ul.list > li {
  color: #34495e;
  margin-top: 0;
  background: #fff;
  border-top: 1px solid #ddd;
}

.todo ul.list > li.todo-done:after, .todo li:after, .todo-search::before {
  content: none;
}

.todo-icon {
  padding: 0 22px 0 0;
  margin-top: 13px;
}

h4.todo-name {
  display: inline-block;
  margin-top: 13px;
  margin-bottom: 0;
  color: #34495e;
  font-weight: 500;
}

.todo-content button, .todo-search button, .todo-content .btn-group {
  float: right;
  display: inline-block;
  margin: 0;
  width: 150px;
}

.todo ul.list > li.start-arrow:after {
  content: "\f175";
  font-family: 'FontAwesome';
  text-align: center;
  font-size: 36px;
  line-height: 21px;
  font-style: normal;
  font-weight: normal;
  font-variant: normal;
  text-transform: none;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background: none;
  color: #3498db;
  width: auto;
  height: auto;
  left: 2.5%;
  right: auto;
  top: 90%;
  z-index: 99;
  margin-top: auto;
}

.todo-search .btn-group button:after {
  content: none;
}

.todo-search .btn-group {
  position: absolute;
  right: 17%;
}

.todo .dropdown-menu li:last-child {
  border-radius: 0 0 6px 6px;
  padding-bottom: 0;
}

.todo-content .btn-group.show .btn {
  margin-top: -35px;
}

.todo-search p {
  display: inline-block;
}

li .todo-name span {
  width: 80px;
  display: inline-block;
  text-align: left;
}

li .todo-name span:first-child {
  width: 40px;
}

li .todo-name span:nth-child(3), li .todo-name span:nth-child(4) {
  width: 60px;
}

li .todo-name span:last-child {
  text-align: center;
  font-weight: 900;
}

#flights li:first-child {
  border-radius: 6px 6px 0 0;
}

.d-inline {
  display: inline-block;
}

.form-group {
  margin-bottom: 0;
}

.form-group .form-control {
  width: auto;
}

.todo.functions ul.list > li {
  padding: 10px 25px;
}
</style>
