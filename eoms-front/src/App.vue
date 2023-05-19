<template>
  <div id="app">
    <router-view ></router-view>
  </div>
</template>

<script>
export default {
  data() {
      return {}
  },
  created() {
    if (localStorage.getItem('token') !== null && window.location.href !== 'http://localhost/home')
      this.$router.replace("/home")
  }

}
</script>


<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: rgb(240 242 245);
  height: 100vh;
}


</style>
