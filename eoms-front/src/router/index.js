import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

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
    component: () => import('@/views/IndexView'),
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/HomeView')
      },
      {
        path: 'ima',
        name: 'ima',
        component: () => import('@/views/stock/ImaView.vue')
      },
      {
        path: 'workOrder',
        name: 'workOrder',
        component: () => import('@/views/produce/ProduceView.vue')
      },
      {
        path: 'currStock',
        name: 'currStock',
        component: () => import('@/views/stock/StockView.vue')
      },
      {
        path: 'workOrderEdit',
        name: 'workOrderEdit',
        component: () => import('@/views/produce/WorkOrderEditView.vue')
      },
      {
        path: 'chatgpt',
        name: 'chatgpt',
        component: () => import('@/views/lab/ChatGPTView.vue')
      },
      {
        path: 'rva',
        name: 'rva',
        component: () => import('@/views/purchase/RvaView')
      },
      {
        path: 'contacts',
        name: 'contacts',
        component: () => import("@/views/system/ZxView.vue")
      },
      {
        path: 'operation',
        name: 'operation',
        component: () => import("@/views/system/OperationView.vue")
      },
      {
        path: 'import',
        name: 'import',
        component: () => import("@/views/system/ImportView.vue")
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
 * 路由守护,没token就不允许进入
 */
router.beforeEach((to, from, next) => {
  if(to.name === '/login'){
    if(localStorage.getItem('token')){
      next({name: '/'})
    }
  }else {
    if(!localStorage.getItem('token')){
      next({name: '/login'})
    }
  }
  next();
})

router.afterEach(to => {
  store.commit('SET_CURRENT_MENU', to.name)
})

export default router
