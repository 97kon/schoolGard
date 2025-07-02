<template>
    <view class="template-about tn-safe-area-inset-bottom">
        <view class="main">
            <view class="sort-buttons tn-flex tn-flex-row-between tn-margin-bottom">
                <button @click="sortByPriority" class="sort-btn">默认排序</button>
                <button @click="sortByTime" class="sort-btn">时间排序</button>
            </view>

            <view class="tn-padding" v-for="(item, index) in sortedModel" :key="index">
                <view class="card" @click="toggleExpand(index)">
                    <view class="card-header tn-flex tn-flex-row-between">
<!--                        <view>{{expandedIndex.index}} </view>-->
                        <view class="tn-text-bold">{{item.entertime}} - {{item.outtime}}</view>
                        <view class="status-tag" :style="{backgroundColor: getStatusColor(item.status)}">
                            {{getStatusText(item.status)}}
                        </view>
                    </view>

                    <view v-if="expandedIndex === index" class="card-content">
                        <view class="tn-margin-top-sm">
                            <view>姓名：{{item.name}}</view>
                            <view>性别：{{item.sex}}</view>
                            <view>身份证：{{item.idnumber}}</view>
                            <view>电话：{{item.phone}}</view>
                            <view v-if="item.car">车牌号：{{item.car}}</view>
                            <view>事由：{{item.desc}}</view>
                            <view>编号：{{item.anumbering}}</view>
                        </view>

                        <view class="tn-margin-top-sm">
                            <view v-if="item.status === '0' || item.status === '1'" class="time-remaining">
                                距签到还有: {{getTimeRemaining(item.entertime)}}
                            </view>
                            <button v-if="item.status === '0'" class="cancel-btn" @click.stop="cancelApply(index)">
                                取消申请
                            </button>
                            <button v-if="item.status === '1'" class="sign-btn" @click.stop="goToSignIn">
                                签到
                            </button>
                        </view>
                    </view>
                </view>
            </view>
        </view>




        <!-- 底部tabbar start-->
        <view class="tabbar footerfixed">
            <view class="action" @click="goToIndex">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-home-smile">
                    </view> -->
                    <text class="tn-icon-location-fill"  style="padding: 10rpx;color: gray"></text>

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
            <view class="action">
                <view class="bar-icon">
                    <!-- <view class="tn-icon-image-text">
                    </view> -->
                    <text class="tn-icon-inventory-fill"  style="padding: 10rpx;color: #005bac"></text>

<!--                    <image class="" src='https://resource.tuniaokj.com/images/tabbar/case_tn.png'></image>-->
                </view>
                <view class="tn-color-gray">我的申请</view>
            </view>
            <view class="action" @click="goToPersonCenter">
                <view class="bar-icon" >
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
import {cancelApply, getApplyRecordsByName} from "@/common/request/api";

export default {
    computed: {
        getStatusColor() {
            return (status) => {
                switch(status) {
                    case '0': return '#FFCC00'; // 待审核-黄色
                    case '1': return '#00CC00'; // 已通过-绿色
                    case '2': return '#FF0000'; // 未通过-红色
                    case '3': return '#999999'; // 已取消-灰色
                    case '4': return '#0066CC'; // 已爽约-蓝色
                    default: return '#999999';
                }
            };
        },
        getStatusText() {
            return (status) => {
                switch(status) {
                    case '0': return '待审核';
                    case '1': return '已通过';
                    case '2': return '未通过';
                    case '3': return '已取消';
                    case '4': return '已爽约';
                    default: return '未知状态';
                }
            };
        },
        sortedModel() {
            if (this.sortBy === 'priority') {
                return [...this.model].sort((a, b) => {
                    // 1 > 0 > 2 > 3 > 4
                    const priorityOrder = {'1': 0, '0': 1, '2': 2, '3': 3, '4': 4};
                    return priorityOrder[a.status] - priorityOrder[b.status];
                });
            } else {
                return [...this.model].sort((a, b) => {
                    return new Date(a.entertime) - new Date(b.entertime);
                });
            }
        }
    },
    data() {
        return {
            model: [],
            expandedIndex: -1,
            sortBy: 'priority' // 'priority' or 'time'
        };
    },
    mounted() {
        this.fetchApplyRecords();
    },
    methods: {
        async fetchApplyRecords() {
            try {

                let userInfo = this.$store.state.vuex_user

                console.log(userInfo.uname)
                // if (!userInfo || !userInfo.uname) {
                //     console.error('用户信息不存在');
                //     return;
                // }
                // let aname;
                // aname = userInfo.uname
                // console.log(aname)
                const res = await getApplyRecordsByName(userInfo.uname);
                console.log(res)

                if (res.code === 200) {
                    this.model = res.data.map(item => ({
                        name: item.aname || '',
                        sex: item.asex || '',
                        idnumber: item.aidnumber || '',
                        phone: item.aphone || '',
                        car: item.acar || '',
                        entertime: this.formatDateTime(item.aentertime),
                        outtime: this.formatDateTime(item.aouttime),
                        desc: item.adesc || '',
                        // status: item.astatus.toString() ,
                        // anumbering: item.anumbering.toString()
                        status: String(item.astatus ?? ''), // 将 undefined/null 转为空字符串
                        anumbering: String(item.anumbering ?? '')

                    }));
                } else {
                    uni.showToast({
                        title: '1获取申请记录失败',
                        icon: 'none'
                    });
                }
            } catch (error) {
                console.error('2获取申请记录出错:', error);
                uni.showToast({
                    title: '3获取申请记录出错',
                    icon: 'none'
                });
            }
        },
        formatDateTime(dateTimeStr) {
            if (!dateTimeStr) return '';
            const date = new Date(dateTimeStr);
            return `${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
        },
        toggleExpand(index) {
            this.expandedIndex = this.expandedIndex === index ? -1 : index;
            console.log(index)
        },
        sortByPriority() {
            this.sortBy = 'priority';
        },
        sortByTime() {
            this.sortBy = 'time';
        },
        getTimeRemaining(entertime) {
            const now = new Date();
            const target = new Date(entertime);
            const diff = target - now;

            if (diff <= 0) return '已到签到时间';

            const days = Math.floor(diff / (1000 * 60 * 60 * 24));
            const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

            return `${days}天${hours}小时${minutes}分钟`;
        },
        goToSignIn() {
            uni.navigateTo({
                url: '/pages/index/index'
            });
        },
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
        goToPersonCenter(){
            uni.navigateTo({
                url: '/pages/personalCenter/personalCenter'
            })
        },
        async cancelApply(index) {
            const that = this; // 保存组件实例引用
            console.log('点击的index:', index);
            console.log('当前sortedModel:', that.sortedModel[index]);
            console.log('原始model:', that.model);

            // 找到原始model中对应的记录
            const clickedItem = that.sortedModel[index];
            const originalIndex = that.model.findIndex(item =>
                item.anumbering === clickedItem.anumbering
            );

            if (originalIndex === -1) {
                console.error('找不到对应的原始记录');
                return;
            }

            uni.showModal({
                title: '提示',
                content: '确定要取消申请吗？',
                success: async (res) => {
                    if (res.confirm) {
                        try {
                            const anumbering = clickedItem.anumbering;
                            console.log('取消申请编号:', anumbering);
                            console.log('原始记录索引:', originalIndex);

                            const result = await cancelApply(anumbering);
                            console.log('取消申请结果:', result);

                            if (result.code === 200) {
                                // 更新sortedModel和原始model
                                that.sortedModel[index].status = '3';
                                that.model[originalIndex].status = '3';

                                uni.showToast({
                                    title: '已取消申请',
                                    icon: 'success'
                                });
                                // 刷新数据
                                await that.fetchApplyRecords();
                            } else {
                                uni.showToast({
                                    title: result.message || '取消申请失败',
                                    icon: 'none'
                                });
                            }
                        } catch (error) {
                            console.error('取消申请出错:', error);
                            uni.showToast({
                                title: '取消申请出错: ' + error.message,
                                icon: 'none'
                            });
                        }
                    }
                }
            });
        }
    }
}
</script>

<style lang="scss" scoped>
.template-about{
    .main {
        padding-bottom: 120rpx;
    }
    .card {
        background-color: #fff;
        border-radius: 10rpx;
        box-shadow: 0 2rpx 10rpx rgba(0,0,0,0.1);
        padding: 20rpx;
        margin-bottom: 20rpx;
    }
    .card-header {
        padding-bottom: 10rpx;
        border-bottom: 1rpx solid #eee;
    }
    .status-tag {
        color: #fff;
        font-size: 24rpx;
        padding: 4rpx 12rpx;
        border-radius: 20rpx;
    }
    .sort-buttons {
        padding: 20rpx;
    }
    .sort-btn {
        background-color: #0066CC;
        color: #fff;
        border: none;
        border-radius: 8rpx;
        padding: 10rpx 20rpx;
        font-size: 28rpx;
        margin: 0 10rpx;
    }
    .time-remaining {
        color: #666;
        font-size: 24rpx;
        margin-bottom: 10rpx;
    }
    .cancel-btn {
        background-color: #FF0000;
        color: #fff;
        border: none;
        border-radius: 8rpx;
        padding: 10rpx 20rpx;
        font-size: 28rpx;
    }
    .sign-btn {
        background-color: #00CC00;
        color: #fff;
        border: none;
        border-radius: 8rpx;
        padding: 10rpx 20rpx;
        font-size: 28rpx;
    }
}

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
