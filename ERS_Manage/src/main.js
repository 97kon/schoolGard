import { createApp } from 'vue'
// import Vue from 'vue'
import App from './App.vue'
import installElementPlus from './plugins/element'
import router from './router/index.js'
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css"
import {ElIcon} from "element-plus";
import service from "./axios/http.js";
import store from '@/store'
import ECharts from "vue-echarts";
import "echarts"

// const app = createApp(App)
const app = createApp(App).use(store).use(router)
installElementPlus(app)
app.mount('#app')
app.use(ElementPlus)
app.use(router)
app.component(ElIcon.name, ElIcon)
app.config.globalProperties.$axios = service
app.component('ECharts',ECharts)
// app.use(store)

// 全局配置
// app.config.globalProperties.$store=store;
// app.config.globalProperties.$axios=axios;
// axios.defaults.baseURL = '/api'
