<template>
  <div class='menu-container'>
    <template v-for='v in menuList'>
      <el-submenu :index='v.name' v-if='v.children && v.children.length > 0' :key='v.name'>
        <template slot='title'>
          <i class="icon" v-if="v.meta && v.meta.icon" :class="v.meta.icon"></i>
          <span>{{ v.meta.name }}</span>
        </template>
        <el-menu-item-group>
          <my-nav :menuList='v.children'></my-nav>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item :key='v?.name' :index='v.name' @click='gotoRoute(v)' v-else>
        <i class="icon" v-if="v.meta && v.meta.icon" :class="v.meta.icon"></i>
        <span slot='title'>{{ v.meta.name }}</span>
      </el-menu-item>
    </template>
    
  </div>
</template>

<script>
import store from "@/store";

export default {
  name: 'my-nav',
  props: {
    menuList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  methods: {
    gotoRoute(item) {
      this.$router.push( item.name )
      store.commit('SET_CURRENT_MENU', item.name)
      store.commit('addMenu', {name: item.meta.name,path: item.name})
    }
  }
}
</script>

<style lang='scss'>
.menu-container {
  .icon {
    margin-right: 10px;
  }
}
</style>
