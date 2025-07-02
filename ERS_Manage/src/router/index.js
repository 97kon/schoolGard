import {createRouter, createWebHashHistory} from 'vue-router';
import store from '@/store';

import echartsOne from "@/components/echartsOne.vue";
// import index from "vuex";
import index from "@/pages/index.vue";
import accountList from "@/components/accountList.vue";
import addAccount from "@/components/addAccount.vue";
import resetPwd from "@/components/resetPwd.vue";
import login from "@/pages/login.vue";
// ERS组件
import allSettings from "@/ERScomponents/allSettings.vue";
import approvalApply from "@/ERScomponents/approvalApply.vue";
import selectEnterRecord from "@/ERScomponents/selectEnterRecord.vue";

//将路由暴露出来
const routes = [

    {
        path: '/index',
        name: 'index',
        component: index
    },
    {
        path: '/echartsOne',
        name: 'echartsOne',
        component: echartsOne
    },

    {
        path: '/accountList',
        name: 'accountList',
        component: accountList
    },
    {
        path: '/addAccount',
        name: 'addAccount',
        component: addAccount
    },
    {
        path: '/resetPwd',
        name: 'resetPwd',
        component: resetPwd
    },

    {
        path: '/login',
        name: 'login',
        component: login
    },
    {
        path: '/',
        redirect:'/login'
    },

    {
        path: '/allSettings',
        name: 'allSettings',
        component: allSettings
    },
    {
        path: '/approvalApply',
        name: 'approvalApply',
        component: approvalApply
    },

    {
        path: '/selectEnterRecord',
        name: 'selectEnterRecord',
        component: selectEnterRecord
    },


];
//  创建一个路由器，History()模式
const router = createRouter({
    history: createWebHashHistory(),
    routes:routes
});

// 添加路由守卫
router.beforeEach((to, from, next) => {
  const isLoginPage = to.name === 'login'
  const isAuthenticated = store.state.isAuthenticated

  if (!isLoginPage && !isAuthenticated) {
    next({ name: 'login' })
  } else if (isLoginPage && isAuthenticated) {
    next({ name: 'index' })
  } else {
    next()
  }
})

export default router
