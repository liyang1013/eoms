import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import Contacts from '@/views/system/ContactsView.vue'

Vue.use(VueRouter)

//重复点击报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err)
}

const routes = [
  {
    path: '/login',
    name: '/login',
    component: () => import('@/views/LoginView'),
  },
  {
    path: '/',
    name: '/',
    component: () => import('@/views/index'),
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/HomeView')
      },
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
        component: () => import('@/views/stock/StockView.vue')
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
      // {
      //   path: 'contacts',
      //   name: 'contacts',
      //   component: () => import("@/views/system/ContactsView")
      // }
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
  store.commit('SET_CURRENT_MENU', to.name);
  if(to.name === '/') router.addRoute('/',{ path: '/contacts', name: 'contacts',component: Contacts })
  next()
})

export default router
