<template>
    <view class="template-about tn-safe-area-inset-bottom">
        <view class="tn-padding">
            <view class="tn-text-bold tn-text-xl tn-margin-bottom">我的爽约记录</view>
            
            <view v-if="records.length === 0" class="tn-text-center tn-margin-top-xl">
                <view class="tn-icon-empty tn-text-xxl tn-color-gray"></view>
                <view class="tn-margin-top-sm">暂无爽约记录</view>
            </view>

            <view v-else class="record-list">
                <view v-for="(item, index) in records" :key="index" class="record-item tn-margin-bottom-sm">
                    <view class="tn-flex tn-flex-row-between">
                        <view class="tn-text-bold">{{ formatDateTime(item.ndatetime) }}</view>
                        <view class="tn-tag tn-tag--danger">爽约</view>
                    </view>
                    <view class="tn-margin-top-xs tn-color-gray">{{ item.nreason || '无具体原因' }}</view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data() {
        return {
            records: []
        }
    },
    mounted() {
        this.fetchNoshowRecords();
    },
    methods: {
        async fetchNoshowRecords() {
            try {
                const userInfo = this.$store.state.vuex_user;
                if (!userInfo || !userInfo.uname) return;
                
                const res = await this.$api.getNoshowRecords(userInfo.uname);
                if (res.code === 200) {
                    this.records = res.data || [];
                }
            } catch (error) {
                console.error('获取爽约记录失败:', error);
                uni.showToast({
                    title: '获取爽约记录失败',
                    icon: 'none'
                });
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

<style scoped>
.record-list {
    background-color: #fff;
    border-radius: 10rpx;
    padding: 20rpx;
    box-shadow: 0 2rpx 10rpx rgba(0,0,0,0.1);
}

.record-item {
    padding: 20rpx;
    border-bottom: 1rpx solid #eee;
}

.record-item:last-child {
    border-bottom: none;
}
</style>
