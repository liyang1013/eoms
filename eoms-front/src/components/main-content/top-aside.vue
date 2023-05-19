<template>
  <aside class="aside__top">
        <span class="iconfont icon-nav toggleNavCollapse" :class="{ active: isSidebarNavCollapse }"
              @click="toggleNavCollapse">
        </span>
    <el-tag key="home" @click="openMenu('home')" size="small" :effect="currentMenu === 'home' ? 'dark' : 'plain' ">
      首页
    </el-tag>
    <el-tag :key="tag.path" @click="openMenu(tag.path)" size="small" v-for="tag in menuList" :closable="true"
            :disable-transitions='false' :effect="currentMenu === tag.path ? 'dark' : 'plain' "
            @close="handleClose(tag)">
      {{ tag.name }}
    </el-tag>
    <div class="aside_top_right">
      <el-dropdown trigger="click" size="medium" @command="handleCommand" placement="bottom-start">
        <el-avatar :src="avatar" :size="32"></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="exit" icon="el-icon-switch-button">退出登入</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span class="userinfo">{{ username() }}</span>
    </div>

  </aside>
</template>

<script>
import {mapState} from 'vuex'
import avatar from '@/image/avatar.jpg'

export default {
  data() {
    return {
      avatar: avatar
    }
  },
  computed: {
    ...mapState(['isSidebarNavCollapse']),
    ...mapState(['menuList']),
    ...mapState(['currentMenu'])
  },
  methods: {
    toggleNavCollapse() {
      this.$store.commit('toggleNavCollapse')
    },
    handleClose(tag) {
      this.$store.commit('deleteMenu', tag);
    },
    openMenu(path) {
      this.$store.commit('SET_CURRENT_MENU', path)
      this.$router.push(path)
    },
    username() {
      return localStorage.getItem('username')
    },
    handleCommand(command){
      switch (command){
        case 'exit': localStorage.removeItem("username");localStorage.removeItem("token");this.$router.push('/login'); break
      }
    }
  },
}
</script>

<style lang="scss" scoped>
.aside__top {
  border-bottom: 1px solid #e5e5e5;
  height: 45px;
  line-height: 45px;
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

  .aside_top_right {
    height: 45px;
    float: right;
    margin-right: 20px;

    ::v-deep .el-dropdown {
      height: 45px;

      .el-avatar {
        margin-top: 5px;

        img {
          width: 40px;
        }
      }
    }

    .userinfo {
      line-height: 45px;
      margin-left: 10px;
      float: right;
      display: block;
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
