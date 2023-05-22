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

export default {
  name: 'index',
  computed: {
    ...mapState(['isSidebarNavCollapse'])
  },
  components: {
    sidebarNav,
    TopAside
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