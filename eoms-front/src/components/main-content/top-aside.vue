<template>
  <aside class="aside__top">
        <span class="iconfont icon-nav toggleNavCollapse" :class="{ active: isSidebarNavCollapse }"
              @click="toggleNavCollapse">
        </span>
    <el-tag key="home" @click="openMenu('home')" size="small" :effect="currentMenu === 'home' ? 'dark' : 'plain' ">首页</el-tag>
    <el-tag :key="tag.path" @click="openMenu(tag.path)" size="small" v-for="tag in menuList" :closable="true" :disable-transitions='false' :effect="currentMenu === tag.path ? 'dark' : 'plain' " @close="handleClose(tag)">
      {{tag.name}}
    </el-tag>
    <div class="aside__top--right">
      <div class="user-msg">
        <img class="user-img" src="@/image/avatar.jpg" alt="">&nbsp
        <span class="user-name">{{ username }}</span>
      </div>
    </div>
  </aside>
</template>

<script>
import {mapState} from 'vuex'

export default {
  data() {
    return {}
  },
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['username']),
    ...mapState(['menuList']),
    ...mapState(['currentMenu'])
  },
  methods: {
    toggleNavCollapse() {
      this.$store.commit('toggleNavCollapse')
    },
    handleClose(tag){
      this.$store.commit('deleteMenu',tag);
    },
    openMenu(path){
      this.$store.commit('SET_CURRENT_MENU',path)
      this.$router.push(path)
    }
  },
}
</script>

<style lang="scss" scoped>
.aside__top {
  border-bottom: 1px solid #e5e5e5;
  height: 50px;
  line-height: 50px;
  position: fixed;
  left: 200px;
  top: 0;
  right: 0;
  background: #fff;
  z-index: 1000;
  transition: left 0.25s;

  .el-tag {
    margin-left: 10px;
  }

  .toggleNavCollapse {
    display: inline-block;
    margin-left: 8px;
    padding: 0 10px;
    font-size: 26px;
    vertical-align: middle;
    color: #333;
    cursor: pointer;
    transition: all 0.5s;

    &.active {
      transform: rotate(90deg);
    }
  }

  .aside__top--right {
    position: absolute;
    right: 10px;
    top: -1px;
    bottom: 0px;

    > div {
      position: relative;
      display: inline-block;
      text-align: center;
      vertical-align: middle;
      margin-left: 10px;
      padding: 0 15px;

      &.user-msg {
        .user-img {
          width: 34px;
          height: 34px;
          border-radius: 50%;
          vertical-align: middle;
        }

        .user-name {
          color: #758eb5;
          padding: 0 4px;
        }
      }

      .iconfont {
        position: relative;
        font-size: 24px;
        color: #758eb5;
      }
    }
  }
}

.breadcrumb-enter,
.breadcrumb-leave-active {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-enter-active,
.breadcrumb-leave-active {
  transition: all 0.6s;
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>
