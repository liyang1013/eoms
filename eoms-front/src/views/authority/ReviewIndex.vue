<template>
  <div :class="{ navCollapsed: isSidebarNavCollapse }">
    <sidebarNav class="sidebar" :menu="menuList" />
    <div class="main-container">
      <TopAside/>
      <el-card class="box-card" shadow="always">
        <keep-alive :max="8">
          <router-view class="content"></router-view>
        </keep-alive>
      </el-card>
    </div>
  </div>
</template>

<script>

import sidebarNav from '@/components/sidebar/sidebar-nav'
import TopAside from '@/components/main-content/top-aside'
import {mapState} from 'vuex'
import {menu} from "@/router/authorityReviewMenu";
import router from "@/router";

export default {
  name: "review",
  data(){
    return{
      menuList: menu
    }
  },
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['activeMenuList'])
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
          path: '/authorityReview/'+view.path,
          component: view.component
        }
        router.addRoute('authorityReview',temp)
      })
    }
  },
  mounted() {
    this.routesData(this.filterRoute(menu))
    this.$router.push('/authorityReview/home')
  }
}
</script>

<style scoped lang="scss">
.main-container {
  height: 100vh;
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