<template>
  <el-menu :collapse="isSidebarNavCollapse" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
           :default-active="currentMenu" :unique-opened="true">
    <DynamicMenu :menuList="fullMenu"></DynamicMenu>
  </el-menu>
</template>

<script>
import DynamicMenu from './dynamic-menu'
import {mapState} from 'vuex'

export default {
  data() {
    return {
      isCollapse: true,
      fullMenu: null
    }
  },
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['currentMenu'])
  },
  components: {
    DynamicMenu
  },
  mounted() {
    this.$http.get('/api/menu/getAllMenu').then(res => {
      this.fullMenu = res.data.result
    })
  }
}
</script>
