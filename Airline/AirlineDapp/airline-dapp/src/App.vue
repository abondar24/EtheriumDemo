<template>
  <div id="app">
    <Airline></Airline>
  </div>
</template>
<script>
import Airline from './components/Airline.vue'

export default {
  name: 'App',
  components: {
    Airline
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
