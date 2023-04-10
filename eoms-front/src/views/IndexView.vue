<template>
  <div :class="{ navCollapsed: isSidebarNavCollapse }">
    <sidebarNav class="sidebar"/>
    <div class="main-container">
      <TopAside/>
      <el-card class="box-card" shadow="always">
        <KeepAlive>
          <router-view class="content"></router-view>
        </KeepAlive>
      </el-card>
    </div>
  </div>
</template>

<script>

import sidebarNav from '@/components/sidebar-nav.vue'
import TopAside from '@/components/main-content/top-aside'
import {mapState} from 'vuex'

export default {
  name: 'index',
  data() {
    return {}
  },
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
  }

}
</style>