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
    name: 'login',
    component: () => import('@/views/LoginView'),
  },
  {
    path: '/',
    name: '/',
    redirect: '/home',
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
        component: () => import('@/views/stock/ImaView')
      },
      {
        path: 'workOrder',
        name: 'workOrder',
        component: () => import('@/views/produce/ProduceView')
      },
      {
        path: 'currStock',
        name: 'currStock',
        component: () => import('@/views/stock/ImgView')
      },
      {
        path: 'workOrderEdit',
        name: 'workOrderEdit',
        component: () => import('@/views/produce/SfbView')
      },
      {
        path: 'chatgpt',
        name: 'chatgpt',
        component: () => import('@/views/lab/ChatGPTView')
      },
      {
        path: 'rva',
        name: 'rva',
        component: () => import('@/views/purchase/RvaView')
      },
      {
        path: 'invTransfer',
        name: 'invTransfer',
        component: () => import("@/views/flux/InvTransferView")
      },
      {
        path: 'operation',
        name: 'operation',
        component: () => import("@/views/system/OperationView")
      },
      {
        path: 'import',
        name: 'import',
        component: () => import("@/views/system/ImportView")
      },
      {
        path: 'contacts',
        name: 'contacts',
        component: () => import("@/views/system/ZxView")
      },
      {
        path: 'robotStates',
        name: 'robotStates',
        component: () => import("@/views/flux/robotStatesView")
      },
      {
        path: 'wdi',
        name: 'wdi',
        component: () => import("@/views/sale/WdiView")
      },
      {
        path: 'menu',
        name: 'menu',
        component: () => import("@/views/system/MenuView")
      }
    ]
  },
  {
    path: '*',
    name: '404',
    component: () => import("@/views/exception/404")
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

  const token = localStorage.getItem('token');
  const staticRouterMap = store.getters.getStaticRouterMap;
  if(staticRouterMap.indexOf(to.name) < 0){
    if(!token) next({name: 'login'})
  }

  next();

})

export default router
