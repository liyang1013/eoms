import Vue from 'vue'
import Vuex from 'vuex'
import configJson from '../static/config.json'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    centres: configJson.centre,
    isSidebarNavCollapse: false,
    crumbList: [],
    sidebarMenu: configJson.menu,
    currentMenu: 'home'
  },
  getters: {
  },
  mutations: {
    toggleNavCollapse(state) {
      state.isSidebarNavCollapse = !state.isSidebarNavCollapse
    },
    setCrumbList(state, list) {
      state.crumbList = list
    },
    SET_CURRENT_MENU(state, currentMenu) {
      state.currentMenu = currentMenu
  },
  },
  actions: {
  },
  modules: {
  }
})
