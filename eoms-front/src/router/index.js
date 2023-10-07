import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

//重复点击报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err)
}

const routes = [
    {
        path: '/',
        redirect: {
            name: '403'
        }
    },
    {
        path: '/report',
        name: 'report',
        component: () => import('@/views/report/ReportIndex'),
        children: [
            {
                path: 'carMap',
                name: 'carMap',
                component: () => import('@/views/report/CarMapView.vue')
            }
        ]
    },
    {
        path: '/eoms',
        name: 'eoms',
        component: () => import('@/views/Eoms')
    },
    {
        path: '*',
        name: '404',
        component: () => import('@/views/exception/404')
    },
    {
        path: '/403',
        name: '403',
        component: () => import('@/views/exception/403')
    },
    {
        path: '/500',
        name: '500',
        component: () => import('@/views/exception/500')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

/**
 * 路由守护
 */
router.beforeEach((to, from, next) => {
    next();
})

export default router
