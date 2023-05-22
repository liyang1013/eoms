import Vue from 'vue'
import Vuex from 'vuex'
import router from "@/router";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        isSidebarNavCollapse: false,
        currentMenu: 'home',
        menuList: [],
        staticRouterMap: [
            'login',
            'register',
            '404',
            '403',
            '401'
        ]
    },
    getters: {
        getStaticRouterMap(state){
            return state.staticRouterMap;
        }
    },
    mutations: {
        toggleNavCollapse(state) {
            state.isSidebarNavCollapse = !state.isSidebarNavCollapse
        },
        SET_CURRENT_MENU(state, currentMenu) {
            state.currentMenu = currentMenu
        },
        addMenu(state, menu) {
            let index = state.menuList.findIndex(item => {
                return item.name === menu.name;
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
