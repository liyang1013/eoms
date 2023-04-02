import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '/login',
    component: () => import('@/views/LoginView'),
  },
  {
    path: '/',
    name: '/',
    component: () => import('@/views/HomeView'),
    children: [
      {
        path: 'ima',
        name: 'ima',
        component: () => import('@/views/base/ImaView')
      },
      {
        path: 'workOrder',
        name: 'workOrder',
        component: () => import('@/views/ProduceView')
      },
      {
        path: 'currStock',
        name: 'currStock',
        component: () => import('@/views/StockView')
      },
      {
        path: 'workOrderEdit',
        name: 'workOrderEdit',
        component: () => import('@/views/WorkOrderEditView')
      },
      {
        path: 'chatgpt',
        name: 'chatgpt',
        component: () => import('@/views/ChatGPTView')
      },
      {
        path: 'rva',
        name: 'rva',
        component: () => import('@/views/purchase/RvaView')
      },
      {
        path: 'contacts',
        name: 'contacts',
        component: () => import("@/views/system/ContactsView")
      }
    ]
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
  store.commit('SET_CURRENT_MENU', to.name)
  next()
})

export default router
