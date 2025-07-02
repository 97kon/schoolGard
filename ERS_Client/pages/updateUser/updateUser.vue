<template>
    <view class="components-form" >
        <tn-form title="编辑账号"  :model="editForm" ref="form" :errorType="errorType" :labelPosition="labelPosition" :labelWidth="labelWidth" :labelAlign="labelAlign">
            <tn-form-item label="ID编号" prop="uid">
                <tn-input v-model="editForm.uid" disabled></tn-input>
            </tn-form-item>
            <tn-form-item label="姓名">
                <tn-input v-model="editForm.uname" ></tn-input>
            </tn-form-item>
            <tn-form-item label="性别">
                <tn-input v-model="editForm.usex" ></tn-input>
            </tn-form-item>
            <tn-form-item label="身份证号">
                <tn-input v-model="editForm.uidnumber" ></tn-input>
            </tn-form-item>
            <tn-form-item label="手机号">
                <tn-input v-model="editForm.uphone" ></tn-input>
            </tn-form-item>
            <tn-form-item label="密码">
                <tn-input v-model="editForm.upwd" ></tn-input>
            </tn-form-item>
        </tn-form>
        <view class="btn-group">
            <tn-button class="btn-1" style="padding-bottom: 200rpx" backgroundColor="#01BEFF" fontColor="#FFFFFF" width="100%" @click="submitEdit">提交</tn-button>
            <tn-button class="btn-2" style="padding-bottom: 200rpx" backgroundColor="#01BEFF" fontColor="#FFFFFF" width="100%" @click="cancelEdit">取消</tn-button>
        </view>
    </view>
</template>

<script>
import {updateUserClient} from "@/common/request/api";

export default {
    name: "updateUser",
    data(){
        return{
            disabledTimes: [], // 禁止时间段
            showCardKeyboard: false,
            showPlateKeyboard: false,
            errorType: ['message','border-bottom','toast'],
            labelPosition: 'top',
            labelAlign: 'right',
            border: false,
            actionSheetShow: false,
            labelWidth: 140,
            checkboxWidth: 'auto',
            checkboxWrap: false,
            radioWidth: 'auto',
            radioWrap: false,
            pickerShow: false,
            selectShow: false,
            pickerShowTime: false,
            codeTips: '获取验证码',
            pickerShowEnter: false,
            pickerShowOut: false,
            editForm:{
                uid:'',
                uname:'',
                usex:'',
                uidnumber:'',
                uphone:'',
                upwd:'',
                blacklist:''
            }
        }
    },
    methods:{
        async submitEdit(){
            try{
                const res =await updateUserClient(this.editForm)
                if (res.code === 200){
                    uni.$tn.message.toast('修改成功',3000)
                    uni.navigateTo({
                        url:'/pages/login/login'
                    }) // 切换到登录界面
                }else{
                    uni.$tn.message.toast(res.message || '修改失败')
                }
            }catch (error){
                console.log('提交错误',error)
                uni.$tn.message.toast('提交错误，请重试')

            }
        },
        cancelEdit(){
            uni.navigateTo({
                url:'/pages/personalCenter/personalCenter'
            })
        },
        loadUserInfo() {
            const userInfo = this.$store.state.vuex_user;
            if (userInfo) {
                this.editForm.uname = userInfo.uname || '';
                this.editForm.uidnumber = userInfo.uidnumber || '';
                this.editForm.uphone = userInfo.uphone || '';
                this.editForm.uid = userInfo.uid || '';
                this.editForm.usex = userInfo.usex || '';
                this.editForm.upwd = userInfo.upwd || '';


            }
        },
    },
    mounted() {

    },
    created() {
        this.loadUserInfo()
    },
}
</script >

<style lang="scss" scoped>
.components-form{
    width: 90%;
    margin-left: 5%;
    //margin-bottom: 5%;
    margin-top: 5%;
    border: 1px solid darkgray;
    border-radius: 20px;
    padding: 10px;
}
.box{
    /*width: 9;*/
}
.btn-group{
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin-top: 5%;
}
.btn-1{
    /*flex: 1;*/
    width: 25%;
    margin-right: 10%;
}
.btn-2{
    /*flex: 1;*/
    width: 25%;
    margin-left: 10%;
}
.agreement {
    display: flex;
    align-items: center;
    margin: 40rpx 0;

    &-text {
        padding-left: 8rpx;
        color: $tn-font-sub-color;
    }
}
.plate-input {
    position: relative;
    height: 80rpx;
    padding: 10rpx 0;

    &__container {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    &__item {
        width: 70rpx;
        height: 80rpx;
        line-height: 80rpx;
        text-align: center;
        border: 2rpx solid #ddd;
        border-radius: 8rpx;
        font-size: 32rpx;
        color: #333;
        margin-right: 10rpx;

        &--active {
            border-color: #2979ff;
        }

        &--new-energy {
            width: 120rpx;
            font-size: 24rpx;
            color: #999;
        }
    }

    &__separator {
        margin: 0 20rpx;
        font-size: 36rpx;
        color: #333;
    }

    &__placeholder {
        position: absolute;
        left: 0;
        top: 10rpx;
        width: 100%;
        height: 80rpx;
        line-height: 80rpx;
        text-align: center;
        font-size: 28rpx;
        color: #999;
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
