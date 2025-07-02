<template>
<!--    这个link是谷歌的标签库，用CDN的方式引入来使用的-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!--   这里采用了elementplus的Container布局容器，将页面分为header、aside、main三部分，分别是顶部导航栏el-header，左边侧边手风琴式的菜单栏el-aside，和中间的展示栏el-main -->
    <div class="common-layout" style="height: 100vh; border: 1px #304156 solid">
        <el-container style="height: 100%">
<!--            下面的el-header就是顶栏容器，里面放了logo、顶部导航、用户下拉框，用户头像-->
            <el-header style="background: linear-gradient( to right,#304156 200px, white 200px)" v-if="$route.name !== 'login'">
<!--                我将el-header通过layout布局分成了20份，其中nav_box顶部导航栏占了14分，用户头像加用户下拉框占了6分-->
                <el-row :gutter="20">
<!--                    下面的logo块级元素就是我顶部的MIS的logo放置的地方，设置了一个点击就恢复首页的方法goHome但还未实现-->
                    <div class="logo" style="width: 190px;height: 60px" @click="goHome">
                        <img src="@/assets/img/logo.png" class="logo" alt="管理系统">
                        <h1 style="color: white">ERS</h1>
                    </div>
<!--                    <el-col :span="3" style="border: 1px solid black;">-->
<!--                        <img src="../assets/logo.png" class="logo" alt="管理系统">-->
<!--                    </el-col>-->
<!--                    下面占14份区域就是顶部导航栏放置的区域-->
                    <el-col :span="14" class="col1" >
                        <div class="nav_box">
                            <div class="nav_1"  style="color: #1296DB"><p>华北科技学院--入校登记系统v1.0.0</p></div>
<!--                            <div class="nav_2"><p style="color: white">系统概述</p></div>-->
<!--                            <div class="nav_3"><p style="color: white">系统功能</p></div>-->
<!--                            <div class="nav_4" ><p style="color: white">个人申请</p></div>-->
                        </div>
                    </el-col>
<!--                    下面占6份区域的就是用户下拉栏和用户头像的区域-->
                    <el-col :span="6">
                        <div class="admin">
<!--                            <p>欢迎您，670</p>-->
                            <el-dropdown style="margin-right: 20px">
                                <span class="el-dropdown-link">
                                  欢迎您，{{ userInfo.username || '管理员' }}
                                  <el-icon class="el-icon--right">
                                    <arrow-down />
                                  </el-icon>
                                </span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                                        <el-dropdown-item>Action 2</el-dropdown-item>
                                        <el-dropdown-item>Action 3</el-dropdown-item>
                                        <el-dropdown-item disabled>Action 4</el-dropdown-item>
                                        <el-dropdown-item divided>Action 5</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                            <div class="admin_img">
                                <img src="@/assets/img/670.jpg"  alt="" style="width: 50px;border-radius: 50%; padding-top: 5px;">
                            </div>
                        </div>
                    </el-col>
                </el-row>
            </el-header>
<!--            下面的就是el-aside侧栏容器，里面就是实现的侧边手风琴式的菜单，使用了element plus的menu组件实现-->
            <el-container>
                <el-aside width="200px" style="height: 100%;background-color: #304156" >
                    <el-menu ref="menu"
                            :default-active="$route.path"
                             :active="activeIndex"
                             class="el-menu-vertical-demo"
                             active-text-color=" #1296DB"
                             background-color="#304156"
                             text-color="#fff"
                             @open="handleOpen"
                             @close="handleClose"
                             @select="handleMenuClick"
                             accordion>
<!--                        这个el-sub-menu里面的index属性就是用来实现SPA单页面应用的，比如当点击1-1，在el-main里面就显示1-1对应的vue组件-->

                        <el-sub-menu index="5">
                            <template #title>
                                <el-icon><UserFilled /></el-icon>
                                <span>账号管理</span>
                            </template>
                            <el-menu-item index="5-1" style="background-color: #202F3F">账号列表</el-menu-item>
                            <el-menu-item index="5-2" style="background-color: #202F3F">添加管理员账号</el-menu-item>
                            <el-menu-item index="5-3" style="background-color: #202F3F">修改管理员密码</el-menu-item>
                        </el-sub-menu>
                        <el-sub-menu index="8">
                            <template #title>
                                <el-icon><Histogram /></el-icon>
                                <span>查询与统计</span>
                            </template>
                            <el-menu-item index="8-1" style="background-color: #202F3F">查询入校记录</el-menu-item>
<!--                            <el-menu-item index="8-2" style="background-color: #202F3F">查询爽约记录</el-menu-item>-->
<!--                            <el-menu-item index="8-3" style="background-color: #202F3F">黑名单</el-menu-item>-->
                        </el-sub-menu>
                        <el-sub-menu index="10">
                            <template #title>
                                <el-icon><Tools /></el-icon>
                                <span>设置</span>
                            </template>
                            <el-menu-item index="10-1" style="background-color: #202F3F">所有设置</el-menu-item>
                        </el-sub-menu>
                        <el-sub-menu index="7">
                            <template #title>
                                <el-icon><Checked /></el-icon>
                                <span>审核申请</span>
                            </template>
                            <el-menu-item index="7-1" style="background-color: #202F3F">审批入校登记</el-menu-item>
                        </el-sub-menu>
                    </el-menu>
                </el-aside>
<!--                这个el-main里面通过el-aside里面的侧边菜单控制显示对应的内容，通过单机的选项对应的index来控制，里面显示的内容就是<component>里面的内容，也就是currentComponent这个变量所指向的vue组件，通过js里面的handleMenuClick()方法实现-->
                <el-main style="height: 100%;background-color: #EDEFF1" ><component :is="showEcharts ? 'echartsOne' : currentComponent"></component></el-main>
            </el-container>
        </el-container>
    </div>



</template>

<script>
import { mapState } from 'vuex'
import {ElMain} from "element-plus";
import {ElIcon} from "element-plus";
import {ElSubMenu} from "element-plus";
import echartsOne from "@/components/echartsOne.vue";
import {
    ArrowDown,
    Avatar, Checked,
    GoodsFilled,
    Histogram,
    HomeFilled,
    House,
    List, Setting,
    Shop, Tools,
    UserFilled
} from "@element-plus/icons-vue";
import accountList from "@/components/accountList.vue";
import addAccount from "@/components/addAccount.vue";
import resetPwd from "@/components/resetPwd.vue";
// 导入ERS组件
import allSettings from "@/ERScomponents/allSettings.vue";
import approvalApply from "@/ERScomponents/approvalApply.vue";
import selectEnterRecord from "@/ERScomponents/selectEnterRecord.vue";
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "index",
    data(){
        return{
            currentComponent:'echartsOne',
            showEcharts:false,
            activeIndex:'1',
        }
    },
    components: {
        Checked,
        Tools,
        Setting,
        UserFilled,
        Histogram,
        Avatar,
        Shop,
        GoodsFilled,
        HomeFilled,
        List,
        House,
        ArrowDown,
        // eslint-disable-next-line vue/no-unused-components
        ElMain, ElIcon, ElSubMenu,echartsOne,
        accountList,addAccount,resetPwd,
        allSettings,approvalApply,selectEnterRecord,
    },
    methods: {
        logout() {
            this.$store.dispatch('logout')
            this.$router.push('/login')
        },
        // 这个handleMenuClick方法就是实现SPA的逻辑方法，通过点击侧边菜单栏对应的选项获取到index传入到这个方法，然后选择结构就会给对应的currentComponent赋对应的vue组件的路径名
        handleMenuClick(index) {
            switch (index) {
                case '1-1':
                    this.currentComponent = 'echartsOne'
                    break
                case '2-1':
                    this.currentComponent='ordersManage'
                    break
                case '3-1':
                    this.currentComponent='goodsList'
                    break
                case '3-2':
                    this.currentComponent='goodsAdd'
                    break
                case '3-3':
                    this.currentComponent='goodsClassify'
                    break
                case '4-1':
                    this.currentComponent='storeMange'
                    break
                case '5-1':
                    this.currentComponent='accountList'
                    break
                case '5-2':
                    this.currentComponent='addAccount'
                    break
                case '5-3':
                    this.currentComponent='resetPwd'
                    break
                case '6-1':
                    this.currentComponent='goodsStatistics'
                    break
                case '6-2':
                    this.currentComponent='ordersStatistics'
                    break
                case '7-1':
                    this.currentComponent='approvalApply'
                    break
                case '8-1':
                    this.currentComponent='selectEnterRecord'
                    break
                case '8-2':
                    this.currentComponent='selectNoShowRecord'
                    break
                case '8-3':
                    this.currentComponent='blackList'
                    break
                case '9-1':
                    this.currentComponent='setUserInfo'
                    break
                case '10-1':
                    this.currentComponent='allSettings'
                    break
            }
        },
        // goHome(){
        //     // this.$router.push('/echartsOne')
        //     this.showEcharts=true;
        //     this.activeIndex='1';
        // }
    },
    computed: {
        ...mapState({
            userInfo: state => state.user || { username: '管理员' }
        })
    },
}
</script>

<style scoped>
.el-row{
    height: 60px;
}
.logo{
    /*width: 45px;*/
    height: 45px;
    /*margin: auto;*/
    /*margin-left: 30%;*/
    display: flex;
    justify-content: center;
    align-items: center;
}
.nav_box{
    /*background-color: gray;*/
    display: flex;
    flex-direction: row;
    /*border: 1px black solid;*/
    width: 400px;
    text-align: center;
    height: 58px;
}
.nav_box > div{
    flex: 1;
}
.nav_box > div:hover{
    background-color: #434A50;
}
.col1{
    color: #F6F8FA;
}
.el-dropdown-link {
    color: #304156;
    /*text-align: center;*/
    display: flex;
    flex-direction: row;
}
/*.el-dropdown-link > i{*/
/*    flex: 1;*/
/*}*/
/*.el-dropdown-link > p{*/
/*    flex: 1;*/
/*    margin-top: 5px;*/
/*}*/
.el-menu-vertical-demo {
    width: 100%;
    border-radius: 0;
}
.admin{
    display: flex;
    flex-direction: row;
    /*text-align: left;*/
    justify-content: right;

}
.admin_img{
    display: flex;
    justify-content: center;
    align-items: center;
}
.example-showcase{
    cursor: pointer;
    color: var(--el-color-primary);
    /*color: black;*/
    display: flex;
    align-items: center;
}
.el-dropdown-link {
    cursor: pointer;
    /*color: var(--el-color-primary);*/
    color: #1296DB;
    /*color: black;*/
    display: flex;
    align-items: center;
}
</style>
