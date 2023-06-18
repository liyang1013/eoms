<template>
  <el-menu :collapse="isSidebarNavCollapse" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
           :default-active="currentMenu" :unique-opened="true">
    <DynamicMenu :menuList="menuList"></DynamicMenu>
  </el-menu>
</template>

<script>
import DynamicMenu from './dynamic-menu.vue'
import {mapState} from 'vuex'
import {menu} from '@/router/menu'

export default {
  data() {
    return {
      isCollapse: true,
      menuList: menu
    }
  },
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['currentMenu'])
  },
  components: {
    DynamicMenu
  }
}
</script>
