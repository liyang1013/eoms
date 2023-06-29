import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/index.scss'
import http from './http/httpConfig'
import * as globalFilter from './filters/filters'
import '@/styles/base.scss'
import VCharts from 'v-charts'
import 'v-charts/lib/style.css'

Vue.prototype.$http = http
Vue.config.productionTip = false
Vue.use(ElementUI,{ size: 'mini'})
Vue.use(VCharts)

for (var key in globalFilter) {
  Vue.filter(key, globalFilter[key])
}

Vue._watchers = Vue.prototype._watchers = []

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
