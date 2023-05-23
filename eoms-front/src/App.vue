<template>
  <div id="app">
    <router-view ></router-view>
  </div>
</template>

<script>
import store from "@/store";

export default {
  created() {
    const token = localStorage.getItem('token');
    let index = this.$router.options.routes.findIndex(item => {return item.path === '/'})
    let children = this.$router.options.routes[index].children
    let path = document.location.pathname
    let flag = children.findIndex(item => {return '/'+item.path === path});
    if(token && flag > 0){
        this.$router.replace('home')
    }
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
