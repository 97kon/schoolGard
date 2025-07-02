<template>
    <view class="template-about tn-safe-area-inset-bottom">
        <!-- 签到区域 start -->
        <view class="checkin-container">
            <!-- 圆形签到按钮 -->
            <view class="checkin-btn" :class="{'active': isCheckinTime}" @click="handleCheckin">
                <text>{{ isCheckinTime ? '签到' : '未到时间' }}</text>
            </view>

            <!-- 申请记录卡片 -->
            <view class="info-card">
                <view class="card-item">
                    <text>姓名：</text>
                    <text>{{ model.name }}</text>
                </view>
                <view class="card-item">
                    <text>身份证：</text>
                    <text>{{ model.idnumber }}</text>
                </view>
                <view class="card-item">
                    <text>手机号：</text>
                    <text>{{ model.phone }}</text>
                </view>
                <view class="card-item">
                    <text>进入时间：</text>
                    <text>{{ model.entertime }}</text>
                </view>
                <view class="card-item">
                    <text>离开时间：</text>
                    <text>{{ model.outtime }}</text>
                </view>
                <view class="card-item">
                    <text>事由：</text>
                    <text>{{ model.desc }}</text>
                </view>
                <view class="countdown" v-if="!isCheckinTime">
                    距离签到还有：{{ countdownText }}
                </view>
            </view>
        </view>
        <!-- 签到区域 end -->

        <!-- 底部tabbar start-->
        <view class="tabbar footerfixed">
            <view class="action">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-home-smile">
                    </view> -->
                    <text class="tn-icon-location-fill"  style="padding: 10rpx;color: #005bac"></text>

                    <!--                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/home_tn.png'></image>-->
                </view>
                <view class="tn-color-gray" >签到</view>
            </view>
            <view class="action" @click="goToMyApplyPages">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-discover">
                    </view> -->
                    <text class="tn-icon-write-fill"  style="padding: 10rpx;color: gray"></text>

                    <!--                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/information_tn.png'></image>-->
                </view>
                <view class="tn-color-gray">申请登记</view>
            </view>
            <view class="action" @click="goToMyApply">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-image-text">
                    </view> -->
                    <text class="tn-icon-inventory-fill"  style="padding: 10rpx;color: gray"></text>

                    <!--                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/case_tn.png'></image>-->
                </view>
                <view class="tn-color-gray">我的申请</view>
            </view>
            <view class="action" @click="goToPersonCenter">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-my">
                    </view> -->
                    <!--                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/my_tnnew.png'></image>-->
                    <text class="tn-icon-my-simple-fill"  style="padding: 10rpx;color: gray"></text>
                </view>
                <view class="tn-color-black">个人中心</view>
            </view>
        </view>
    </view>
</template>

<script>
import { selectLatestApprovedApply, updateApplyStatus } from "@/common/request/api";

export default {
    data() {
        return {
            model: {
                name: '',
                sex: '',
                idnumber: '',
                phone: '',
                car: '',
                entertime: '',
                outtime: '',
                desc: '',
                anumbering: '',
                status: ''
            },
            countdownText: '',
            timer: null,
            hasApprovedApply: false
        };
    },
    computed: {
        isCheckinTime() {
            const now = new Date();
            const enterTime = new Date(this.model.entertime);
            const outTime = new Date(this.model.outtime);
            return now >= enterTime && now <= outTime;
        }
    },
    async mounted() {
        await this.fetchLatestApprovedApply();
        this.updateCountdown();
        this.timer = setInterval(this.updateCountdown, 60000);
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },
    methods: {
        async fetchLatestApprovedApply() {
            try {
                const userInfo = this.$store.state.vuex_user;
                // if (!userInfo || !userInfo.uname) {
                //     console.error('用户信息不存在');
                //     return;
                // }

                const res = await selectLatestApprovedApply(userInfo.uname);
                console.log('返回的东西',res)
                if (res.code === 200 && res.data) {
                    this.model = {
                        name: res.data.aname,
                        sex: res.data.asex,
                        idnumber: res.data.aidnumber,
                        phone: res.data.aphone,
                        car: res.data.acar,
                        entertime: this.formatDateTime(res.data.aentertime),
                        outtime: this.formatDateTime(res.data.aouttime),
                        desc: res.data.adesc,
                        anumbering: res.data.anumbering,
                        status: res.data.astatus
                    };
                    this.hasApprovedApply = true;
                } else {
                    this.hasApprovedApply = false;
                    uni.showToast({
                        title: '没有已通过的申请',
                        icon: 'none'
                    });
                }
            } catch (error) {
                console.error('获取申请记录出错:', error);
                uni.showToast({
                    title: '获取申请记录出错',
                    icon: 'none'
                });
            }
        },

        async handleCheckin() {
            if (!this.isCheckinTime) {
                uni.showToast({
                    title: '未到签到时间',
                    icon: 'none'
                });
                return;
            }

            try {
                const res = await updateApplyStatus(this.model.anumbering, '5');
                if (res.code === 200) {
                    uni.showToast({
                        title: '签到成功',
                        icon: 'success'
                    });
                    this.model.status = '5';
                } else {
                    uni.showToast({
                        title: res.message || '签到失败',
                        icon: 'none'
                    });
                }
            } catch (error) {
                console.error('签到出错:', error);
                uni.showToast({
                    title: '签到出错',
                    icon: 'none'
                });
            }
        },

        updateCountdown() {
            if (!this.hasApprovedApply || this.isCheckinTime) return;

            const now = new Date();
            const enterTime = new Date(this.model.entertime);
            const diff = enterTime - now;

            if (diff <= 0) return;

            const days = Math.floor(diff / (1000 * 60 * 60 * 24));
            const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

            this.countdownText = `${days}天${hours}小时${minutes}分钟`;
        },

        formatDateTime(dateTimeStr) {
            if (!dateTimeStr) return '';
            const date = new Date(dateTimeStr);
            return `${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
        },
        // goToIndex(){
        //     uni.navigateTo({
        //         url: '/pages/index/index'
        //     })
        // },
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
        goToPersonCenter(){
            uni.navigateTo({
                url: '/pages/personalCenter/personalCenter'
            })
        },
    }
}
</script>

<style lang="scss" scoped>
/* 签到区域样式 start */
.checkin-container {
    padding: 40rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.checkin-btn {
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
    background-color: #f0f0f0;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 40rpx;
    font-size: 32rpx;
    color: #666;

    &.active {
        background-color: #005bac;
        color: white;
    }
}

.info-card {
    width: 100%;
    background-color: white;
    border-radius: 16rpx;
    padding: 30rpx;
    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
}

.card-item {
    display: flex;
    margin-bottom: 20rpx;
    font-size: 28rpx;

    text:first-child {
        color: #666;
        width: 150rpx;
    }
}

.countdown {
    margin-top: 30rpx;
    color: #ff5500;
    text-align: center;
    font-size: 28rpx;
}
/* 签到区域样式 end */

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
