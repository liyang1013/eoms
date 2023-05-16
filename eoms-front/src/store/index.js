import Vue from 'vue'
import Vuex from 'vuex'
import configJson from '../static/config.json'
import router from "@/router";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        centres: configJson.centre,
        isSidebarNavCollapse: false,
        crumbList: [],
        sidebarMenu: configJson.menu,
        currentMenu:  'home',
        username: '',
        menuList: []
    },
    getters: {},
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
        SET_USERNAME(state, username) {
            state.username = username
        },
        addMenu(state, menu) {
            let index = state.menuList.findIndex(item => {
                if (item.name === menu.name) {
                    return true;
                } else {
                    return false;
                }
            });
            if (index === -1 && menu.path !== 'home') {
                state.menuList.push({name: menu.name, path: menu.path})
            }
        },
        deleteMenu(state, menu){
            state.menuList.splice(state.menuList.indexOf(menu), 1);
            let path = 'home'
            if(state.menuList.length === 0 ){
                state.currentMenu = 'home'
            }
            else{
                path = state.menuList.at( state.menuList.length - 1).path
                state.currentMenu = path
            }
            router.push(path);
        }
    },
    actions: {},
    modules: {}
})
