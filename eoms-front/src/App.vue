<template>
  <div id="app">
    <div :class="{ navCollapsed: isSidebarNavCollapse }">
      <sidebarNav class="sidebar"/>
      <div class="main-container">
        <TopAside/>
        <el-card class="box-card" shadow="always">
          <keep-alive :max="8">
            <router-view class="content"></router-view>
          </keep-alive>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import sidebarNav from '@/components/sidebar/sidebar-nav'
import TopAside from '@/components/main-content/top-aside'
import {mapState} from 'vuex'
import {menu} from "@/router/menu";
import router from "@/router";

export default {
  name: 'app',
  computed: {
    ...mapState(['isSidebarNavCollapse']),
  },
  components: {
    sidebarNav,
    TopAside
  },
  methods: {
    filterRoute(arr) {
      let route = [];
      arr.forEach(e => {
        if (e.component !== null) {
          route.push({...e})
        } else if (e.children && e.children.length)
          route = route.concat(this.filterRoute(e.children))
      });
      return route;
    },
    routesData(menuList) {
      menuList.forEach(view => {
        const temp = {
          name: view.path,
          path: '/'+view.path,
          component: view.component
        }
        router.addRoute(temp)
      })
    }
  },
  mounted() {
    this.$router.push('/home')
    this.routesData(this.filterRoute(menu))
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: rgb(240, 243, 245);
  height: 100vh;
  .main-container {
    background-color: rgb(240 242 245);
    .box-card {
      padding: 0;
      .content {
        width: 100%;
        height: calc(100vh - 120px);
        overflow-y: auto;
        overflow-x: hidden;
      }
    }
  }
}
</style>
