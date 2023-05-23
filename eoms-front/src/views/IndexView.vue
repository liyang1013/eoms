<template>
  <div :class="{ navCollapsed: isSidebarNavCollapse }">
    <sidebarNav class="sidebar"/>
    <div class="main-container">
      <TopAside/>
      <el-card class="box-card" shadow="always">
        <keep-alive :max="8">
          <router-view class="content" ></router-view>
        </keep-alive>
      </el-card>
    </div>
  </div>
</template>
<script>

import sidebarNav from '@/components/sidebar/sidebar-nav'
import TopAside from '@/components/main-content/top-aside'
import {mapState} from 'vuex'
import store from "@/store";

export default {
  name: 'index',
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['fullMenu'])
  },
  components: {
    sidebarNav,
    TopAside
  },
  methods: {
    filterRouter(routers) {
      return routers.filter((router) => {
        if(router.children && router.children.length) {
          router.children = this.filterRouter(router.children)
        }
        return true
      })
    }
  },
  mounted() {
    this.$http.get('/api/menu/getAllMenu').then(res => {
      store.commit('setFullMenu',res.data.result)

      // let routerMaps = this.filterRouter(this.fullMenu);
      // console.log(routerMaps)
      //
      // routerMaps.forEach(item => {
      //   this.$router.getRoutes(). addRoute(item);
      // })

    })
  }
}
</script>

<style lang="scss" scoped>
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
</style>