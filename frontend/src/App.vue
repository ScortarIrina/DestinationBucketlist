<template>
  <div id="app">
    <Destinations />
  </div>
</template>

<script>
import Destinations from './components/Destinations.vue'

export default {
  name: 'App',
  components: {
    Destinations
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  table-layout: auto;
  color: #000000;
  margin-top: 60px;
}
</style>