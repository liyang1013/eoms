import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

//重复点击报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err)
}

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    route: {
        path: '/',
        redirect: {
            name: '/home'
        }
    },
})

/**
 * 路由守护
 */
router.beforeEach((to, from, next) => {
    next();
})

export default router
