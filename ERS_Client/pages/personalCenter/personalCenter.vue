<template>
    <view class="template-about tn-safe-area-inset-bottom">
        <!-- 顶部自定义导航 -->
        <!-- <tn-nav-bar fixed alpha customBack>
          <view slot="back" class='tn-custom-nav-bar__back'
            @click="goBack">
            <text class='icon tn-icon-left'></text>
            <text class='icon tn-icon-home-capsule-fill'></text>
          </view>
        </tn-nav-bar> -->

        <view class="top-backgroup" style="background-color: #005bac">
<!--            <image src='https://resource.tuniaokj.com/images/my/my-bg2.png' mode='widthFix' class='backgroud-image'></image>-->
        </view>

        <view class="about__wrap">
            <view class="tn-flex tn-flex-row-between tn-flex-col-center tn-margin-bottom">
                <view class="justify-content-item">
                    <view class="tn-flex tn-flex-col-center tn-flex-row-left">
                        <!-- 图标logo -->
                        <view class="logo-pic tn-shadow" >
                            <view class="logo-image">
                                <view class="tn-shadow-blur" style="background-image:url('https://resource.tuniaokj.com/images/blogger/avatar_2.jpeg');width: 120rpx;height: 120rpx;background-size: cover;">
                                </view>
                            </view>
                        </view>
                        <view class="tn-padding-right tn-color-white">
                            <view class="tn-padding-right tn-padding-left-sm tn-text-xxl">
                                {{form.name}}
                            </view>
                            <view class="tn-padding-right tn-padding-top-xs tn-padding-left-sm tn-text-ellipsis">{{ form.id }}</view>
                        </view>
                    </view>
                </view>
            </view>

            <!-- 方式7 start-->
            <view class="tn-flex tn-padding-top-xl" style="margin-top: -20rpx;">
                <view class="tn-flex-1 my-shadow tn-margin-right-sm tn-bg-white" style="padding: 30rpx 0;">
                    <view class="tn-flex tn-flex-direction-column tn-flex-row-center tn-flex-col-center">
                        <view class="icon7__item--icon tn-flex tn-flex-row-center tn-flex-col-center">
                            <text class="tn-icon-success-square" style="color: #005bac;border-radius: 50rpx;font-size: 75rpx" ></text>
                        </view>
                        <view class="tn-text-center">
                            <view class="tn-text-ellipsis " style="color: #005bac;">我的申请</view>
                            <view class="tn-text-ellipsis " style="color: #005bac;margin-top: 20rpx;margin-bottom: 20rpx">{{form.date}}</view>
                            <tn-tag size="lg" shape="radius" :class="statusClass" fontColor="tn-color-white" :backgroundColor="statusClass">{{ statusText }}</tn-tag>
                        </view>
                    </view>
                </view>
            </view>
            <!-- 方式7 end-->

            <!-- 更多信息-->
            <view class="about-shadow tn-margin-top-xl tn-padding-top-sm tn-padding-bottom-sm">
                <tn-list-cell :hover="true" :unlined="true" :radius="true" :fontSize="30" @click="goToMyApply">
                    <view class="tn-flex tn-flex-col-center">
                        <view class="icon1__item--icon tn-flex tn-flex-row-center tn-flex-col-center  tn-color-white" style="background-color: #005bac">
                            <view class="tn-icon-logo-tuniao"></view>
                        </view>
                        <view class="tn-margin-left-sm tn-flex-1">我的申请</view>
                        <view class="tn-margin-left-sm tn-color-cyan tn-icon-link"></view>
                    </view>
                </tn-list-cell>

                <tn-list-cell :hover="true" :unlined="true" :radius="true" :fontSize="30" @click.native="goToNoshowRecords">
                    <view class="tn-flex tn-flex-col-center">
                        <view class="icon1__item--icon tn-flex tn-flex-row-center tn-flex-col-center  tn-color-white" style="background-color: #005bac">
                            <view class="tn-icon-search-list-fill"></view>
                        </view>
                        <view class="tn-margin-left-sm tn-flex-1">爽约记录</view>
                    </view>
                </tn-list-cell>
            </view>

            <!-- 更多信息-->
            <view class="about-shadow tn-margin-top-xl tn-padding-top-sm tn-padding-bottom-sm">
                <tn-list-cell :hover="true" :unlined="true" :radius="true" :fontSize="30" @click="goToUpdateUser">
                    <view class="tn-flex tn-flex-col-center">
                        <view class="icon1__item--icon tn-flex tn-flex-row-center tn-flex-col-center  tn-color-white" style="background-color: #005bac">
                            <view class="tn-icon-wechat-fill"></view>
                        </view>
                        <view class="tn-margin-left-sm tn-flex-1">账号信息</view>
                        <view class="tn-margin-left-sm tn-color-cyan tn-icon-link"></view>
                    </view>
                </tn-list-cell>

                <tn-list-cell :hover="true" :unlined="true" :radius="true" :fontSize="30">
                    <view class="tn-flex tn-flex-col-center">
                        <view class="icon1__item--icon tn-flex tn-flex-row-center tn-flex-col-center  tn-color-white" style="background-color: #005bac">
                            <view class="tn-icon-message-fill"></view>
                        </view>
                        <view class="tn-margin-left-sm tn-flex-1">问题反馈</view>
                        <view class="tn-margin-left-sm tn-color-blue tn-icon-copy-fill"></view>
                    </view>
                </tn-list-cell>

                <tn-list-cell :hover="true" :unlined="true" :radius="true" :fontSize="30">
                    <view class="tn-flex tn-flex-col-center">
                        <view class="icon1__item--icon tn-flex tn-flex-row-center tn-flex-col-center  tn-color-white" style="background-color: #005bac">
                            <view class="tn-icon-phone-fill"></view>
                        </view>
                        <view class="tn-margin-left-sm tn-flex-1">绑定手机号</view>
                        <view class="tn-margin-left-sm tn-color-orangered tn-text-sm tn-padding-left-xs tn-padding-right-xs tn-bg-orange--disabled tn-round">{{form.phone}}</view>
                    </view>
                </tn-list-cell>
            </view>
        </view>

        <!-- 底部tabbar start-->
        <view class="tabbar footerfixed">
            <view class="action" @click="goToIndex">
                <view class="bar-icon">
                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/home_tn.png'></image>
                </view>
                <view class="tn-color-gray" >签到</view>
            </view>
            <view class="action" @click="goToMyApplyPages">
                <view class="bar-icon">
                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/information_tn.png'></image>
                </view>
                <view class="tn-color-gray">申请登记</view>
            </view>
            <view class="action" @click="goToMyApply">
                <view class="bar-icon">
                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/case_tn.png'></image>
                </view>
                <view class="tn-color-gray">我的申请</view>
            </view>
            <view class="action">
                <view class="bar-icon">
                    <text class="tn-icon-my-simple-fill"  style="padding: 10rpx;color: #005bac"></text>
                </view>
                <view class="tn-color-black">个人中心</view>
            </view>
        </view>
        <view class="tn-padding-xl"></view>

        <!-- 回到首页悬浮按钮-->
        <nav-index-button></nav-index-button>
    </view>
</template>

<script>
import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
import NavIndexButton from '@/libs/components/nav-index-button.vue'
import {selectLatestApprovedApply} from "@/common/request/api";
export default {
    name: 'about-demo-2',
    mixins: [template_page_mixin],
    components: { NavIndexButton },
    data(){
        return {
            form: {
                id: "",
                name: "",
                date: "",
                status: 0,
                phone: ""
            },
            latestApply: null
        }
    },
    computed: {
        statusText() {
            switch(this.form.status) {
                case 0: return '待审核';
                case 1: return '已通过';
                case 2: return '未通过';
                default: return '未知状态';
            }
        },
        statusClass() {
            switch(this.form.status) {
                case 0: return 'tn-bg-orangeyellow';
                case 1: return 'tn-bg-green';
                case 2: return 'tn-bg-red';
                default: return 'tn-bg-gray';
            }
        }
    },
    created() {
        this.loadUserInfo();
        this.fetchLatestApprovedApply();
    },
    methods: {
        goToIndex(){
            uni.navigateTo({
                url: '/pages/index/index'
            })
        },
        goToMyApplyPages(){
            uni.navigateTo({
                url: '/pages/applyPages/applyPages'
            })
        },
        goToMyApply(){
            uni.navigateTo({
                url: '/pages/myApply/myApply'
            })
        },
        goToNoshowRecords() {
            uni.navigateTo({
                url: '/pages/noshowRecords/noshowRecords'
            });
        },
        goToUpdateUser(){
            uni.navigateTo({
                url: '/pages/updateUser/updateUser'
            });
        },

        loadUserInfo() {
            const userInfo = this.$store.state.vuex_user;
            if (userInfo) {
                this.form.name = userInfo.uname || '';
                this.form.id = userInfo.uidnumber || '';
                this.form.phone = userInfo.uphone || '';
            }
        },

        async fetchLatestApprovedApply() {
            try {
                const userInfo = this.$store.state.vuex_user;
                if (!userInfo || !userInfo.uname) return;

                const res = await selectLatestApprovedApply(userInfo.uname);
                if (res.code === 200 && res.data) {
                    this.latestApply = res.data;
                    this.form.date = this.formatDateTime(res.data.aentertime);
                    this.form.status = res.data.astatus;
                }
            } catch (error) {
                console.error('获取最新申请失败:', error);
            }
        },

        formatDateTime(dateTimeStr) {
            if (!dateTimeStr) return '';
            const date = new Date(dateTimeStr);
            return `${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/static/css/templatePage/custom_nav_bar.scss';
.template-about{
}
/* 顶部背景图 start */
.top-backgroup {
    height: 450rpx;
    z-index: -1;

    .backgroud-image {
        width: 100%;
        height: 450rpx;
    }
}
/* 顶部背景图 end */

/* 用户头像 start */
.logo-image{
    width: 120rpx;
    height: 120rpx;
    position: relative;
    background-color: #FFFFFF;
}
.logo-pic{
    background-size: cover;
    background-repeat:no-repeat;
    background-position:top;
    border-radius: 50%;
    overflow: hidden;
}

/* 页面 start*/
.about-shadow{
    border-radius: 15rpx;
    box-shadow: 0rpx 0rpx 50rpx 0rpx rgba(0, 0, 0, 0.07);
}

.about {
    &__wrap {
        position: relative;
        z-index: 1;
        margin: 20rpx 30rpx;
        margin-top: -230rpx;
    }
}
/* 页面 end*/

.my-shadow{
    box-shadow: 0rpx 0rpx 80rpx 0rpx rgba(0, 0, 0, 0.07);
    border-radius: 20rpx;
}

/* 图标容器7 start */
.icon7 {
    &__item {
        width: 30%;
        background-color: #FFFFFF;
        border-radius: 10rpx;
        padding: 10rpx;
        margin: 20rpx 10rpx;
        transform: scale(1);
        transition: transform 0.3s linear;
        transform-origin: center center;

        &--icon {
            width: 100rpx;
            height: 100rpx;
            font-size: 60rpx;
            border-radius: 0;
            margin-bottom: 18rpx;
            position: relative;
            z-index: 1;
        }
    }
}

/* 图标容器1 start */
.icon1 {
    &__item {
        background-color: #FFFFFF;
        border-radius: 10rpx;
        padding: 30rpx;
        margin: 20rpx 10rpx;
        transform: scale(1);
        transition: transform 0.3s linear;
        transform-origin: center center;

        &--icon {
            width: 40rpx;
            height: 40rpx;
            font-size: 28rpx;
            border-radius: 50%;
            position: relative;
            z-index: 1;

            &::after {
                content: " ";
                position: absolute;
                z-index: -1;
                width: 100%;
                height: 100%;
                left: 0;
                bottom: 0;
                border-radius: inherit;
                opacity: 1;
                transform: scale(1, 1);
                background-size: 100% 100%;
                background-image: url(https://resource.tuniaokj.com/images/cool_bg_image/icon_bg.png);
            }
        }
    }
}
/* 图标容器1 end */

/* 底部tabbar start*/
.footerfixed{
    position: fixed;
    width: 100%;
    bottom: 0;
    z-index: 999;
    background-color: #FFFFFF;
    box-shadow: 0rpx 0rpx 30rpx 0rpx rgba(0, 0, 0, 0.07);
}

.tabbar {
    display: flex;
    align-items: center;
    min-height: 110rpx;
    justify-content: space-between;
    padding: 0;
    height: calc(110rpx + env(safe-area-inset-bottom) / 2);
    padding-bottom: calc(env(safe-area-inset-bottom) / 2);
}

.tabbar .action {
    font-size: 22rpx;
    position: relative;
    flex: 1;
    text-align: center;
    padding: 0;
    display: block;
    height: auto;
    line-height: 1;
    margin: 0;
    overflow: initial;
}

.tabbar .action .bar-icon {
    width: 100rpx;
    position: relative;
    display: block;
    height: auto;
    margin: 0 auto 10rpx;
    text-align: center;
    font-size: 42rpx;
}

.tabbar .action .bar-icon image {
    width: 50rpx;
    height: 50rpx;
    display: inline-block;
}
</style>
