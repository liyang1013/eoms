<template>
    <aside class="aside__top">
        <span class="iconfont icon-nav toggleNavCollapse" :class="{ active: isSidebarNavCollapse }"
            @click="toggleNavCollapse">
        </span>
        <!-- <el-tag closable :disable-transitions="false" type="info">首页</el-tag> -->
        
        <div class="aside__top--right">
            <div class="user-msg">
                <img class="user-img" src="@/image/avatar.jpg" alt="">
                <span class="user-name">李阳</span>
            </div>
        </div>
    </aside>
</template>

<script>
import { mapState } from 'vuex'

export default {
    data() {
        return {}
    },
    computed: {
        ...mapState(['isSidebarNavCollapse']),
    },
    methods: {
        toggleNavCollapse() {
            this.$store.commit('toggleNavCollapse')
        }
    }
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

        >div {
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
