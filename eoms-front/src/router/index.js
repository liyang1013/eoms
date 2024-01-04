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
                component: () => import('@/views/report/CarMapView')
            }
        ]
    },
    {
        path: '/authorityReview',
        name: 'authorityReview',
        component: () => import('@/views/authority/ReviewIndex'),
        meta: {
            title:'authority-review',
        },
    },
    {
        path: '/eoms',
        name: 'eoms',
        component: () => import('@/views/Eoms')
    },
    {
        path: '/invoice',
        name: 'invoice',
        redirect: 'invoice/identify',
        meta: {
            title:'invoice-tools',
        },
        component: () => import('@/views/invoice/InvoiceIndex.vue'),
        children: [
            {
                path: 'identify',
                name: '发票识别',
                icon: 'el-icon-s-check',
                component: () => import('@/views/invoice/OcrView.vue')
            },
            {
                path: 'records',
                name: '发票管理',
                icon: 'el-icon-s-check',
                component: () => import('@/views/invoice/InvoiceView.vue')
            },
        ]
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
    if(to.meta.title) {
        document.title = to.meta.title
    }
    next();
})

export default router
