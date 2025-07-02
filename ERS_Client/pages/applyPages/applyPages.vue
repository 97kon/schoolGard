<template>
    <view class="components-form">

        <!-- 顶部自定义导航 -->
        <tn-nav-bar fixed>申请登记</tn-nav-bar>

        <!-- 页面内容 -->
        <view  :style="{paddingTop: vuex_custom_bar_height + 'px'}" >

            <dynamic-demo-template ref="demoTemplate" :tips="tips" :sectionList="sectionList" :full="true" :fullWindowsScroll="true" @click="click" >
                <tn-form :model="model" ref="form" :errorType="errorType" :labelPosition="labelPosition" :labelWidth="labelWidth" :labelAlign="labelAlign">
<!--                    <tn-form-item label="姓名" prop="name" leftIcon="identity" :required="true" :labelPosition="labelPosition" :labelAlign="labelAlign">-->
<!--                        <tn-input v-model="model.name" type="text" placeholder="请输入姓名" :border="border"></tn-input>-->
<!--                    </tn-form-item>-->
<!--                    <tn-form-item label="性别" prop="sex" :labelPosition="labelPosition" :labelAlign="labelAlign">-->
<!--                        <tn-input v-model="model.sex" type="select" placeholder="请选择性别" :border="border" :selectOpen="actionSheetShow" @click="actionSheetShow = true"></tn-input>-->
<!--                    </tn-form-item>-->
<!--                    <tn-form-item label="身份证号" prop="IDNumber" :labelPosition="labelPosition" :labelAlign="labelAlign">-->
<!--                        <tn-input v-model="model.IDNumber" type="number" placeholder="请输入身份证号" ></tn-input>-->
<!--&lt;!&ndash;                        <tn-keyboard mode="card" v-model="model.IDNumber" v-if="showCardKeyboard" @close="showCardKeyboard = false"></tn-keyboard>&ndash;&gt;-->
<!--                    </tn-form-item>-->
                    <tn-form-item label="手机号码" prop="phone" rightIcon="phone" :labelPosition="labelPosition" :labelAlign="labelAlign">
                        <tn-input v-model="model.phone" type="number" placeholder="请输入手机号码" :border="border"></tn-input>
                    </tn-form-item>
                    <tn-form-item label="车牌号(开车须填)" prop="plateNumber" :labelPosition="labelPosition" :labelAlign="labelAlign">
                        <view class="plate-input" @click="showPlateKeyboardMethod()">
                            <view class="plate-input__container">
                                <view v-for="(char, index) in model.plateNumber" :key="index" class="plate-input__item" :class="{
              'plate-input__item--new-energy': index === 7,
              'plate-input__item--active': index === model.plateNumber.length
            }">
                                    {{ char || '' }}  <!-- 显示字符，如果为空则显示空字符串 -->
                                </view>

                                <!-- 删除按钮 -->
                                <view class="delete-btn" @click="deleteLastCharacter" v-if="model.plateNumber.length > 0">
                                    删除
                                </view>

                                <!-- 占位符 -->
                                <view class="plate-input__placeholder"  @click="showPlateKeyboard = true">
                                    点击输入车牌号
                                </view>

                                <tn-keyboard  mode="car" v-model="model.plateNumber" v-if="showPlateKeyboard" @confirm="confirmMethod()" @backspace="backspace()" @change="handlePlateChange" @close="showPlateKeyboard = false" />

                            </view>
                            <view class="plate-input__placeholder" @click="showPlateKeyboard = true"  v-if="!model.plateNumber" >

                            </view>
                        </view>

                    </tn-form-item>

                    <tn-form-item label="进校时间" prop="EnterDateTime"  :labelPosition="labelPosition" :labelAlign="labelAlign">
                        <tn-input v-model="model.EnterDateTime" type="text" placeholder="请选择进校时间" :border="border" @click="pickerShowEnter = true"></tn-input>
                    </tn-form-item>

                    <tn-form-item label="出校时间" prop="OutDateTime" :labelPosition="labelPosition" :labelAlign="labelAlign">
                        <tn-input v-model="model.OutDateTime" type="text" placeholder="请选择离校时间" :border="border" @click="pickerShowOut = true"></tn-input>
                    </tn-form-item>
                    <tn-form-item label="来访事由" prop="desc" :labelPosition="labelPosition" :labelAlign="labelAlign">
                        <tn-input v-model="model.desc" type="textarea" placeholder="请输入来访事由" :border="border" inputAlign="center"></tn-input>
                    </tn-form-item>

                </tn-form>
                <view class="agreement">
                    <tn-checkbox v-model="model.agreement" @change="agreementCheckChange"></tn-checkbox>
                    <view class="agreement-text">勾选同意当前协议</view>
                </view>
                <tn-button style="padding-bottom: 200rpx" backgroundColor="#01BEFF" fontColor="#FFFFFF" width="100%" @click="submit">提交</tn-button>
            </dynamic-demo-template>

            <!-- 性别选项 -->
            <tn-action-sheet
                v-model="actionSheetShow"
                :list="actionSheetList"
                @click="actionSheetClick"
            ></tn-action-sheet>


            <!-- 时间选择器 -->
            <tn-picker
                v-model="pickerShowTime"
                mode="time"
                @confirm="timePickerConfirm"
            ></tn-picker>

            <!-- 进校时间选择器 -->
            <tn-picker
                v-model="pickerShowEnter"
                mode="time"
                :params="params"
                :disabledDate="disabledDate"
                @confirm="confirmEnterTime"
            ></tn-picker>

            <!-- 离校时间选择器 -->
            <tn-picker
                v-model="pickerShowOut"
                mode="time"
                :params="params"
                :disabledDate="disabledDate"
                @confirm="confirmOutTime"
            ></tn-picker>

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
            <view class="action" >
                <view class="bar-icon">
                    <!-- <view class="tn-icon-discover">
                    </view> -->
                    <text class="tn-icon-write-fill"  style="padding: 10rpx;color: #005bac"></text>

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
import dynamicDemoTemplate from '@/libs/components/dynamic-demo-template.vue'
import { submitApply, getDisabledTimes } from '@/common/request/api'
import app from "@/App.vue";
export default {
    name: 'componentsForm',
    components: {dynamicDemoTemplate},
    data() {
        return {
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

            actionSheetList:[
                {
                    text: '男'
                },
                {
                    text: '女'
                },
                {
                    text: '保密'
                }
            ],
            params: {
                year: true,
                month: true,
                day: true,
                hour: true,
                minute: true,
                second: false
            },

            model: {
                name: '',
                sex: '',
                IDNumber: '',
                plateNumber: '',
                EnterDateTime: '',
                OutDateTime: '',
                phone: '',
                desc: '',
                password: '',
                rePassword: '',
                fruit: ['橘子'],
                payType: '微信',
                region: '',
                goodsType: '',
                code: '',
                remember: false,
                photo: [],
                agreement: false
            },
            rules: {
                name: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 3,
                        max: 5,
                        message: '姓名长度在3到5个字符',
                        trigger: ['change','blur'],
                    },
                    {
                        // 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
                        validator: (rule, value, callback) => {
                            return this.$tn.test.chinese(value);
                        },
                        message: '姓名必须为中文',
                        // 触发器可以同时用blur和change，二者之间用英文逗号隔开
                        trigger: ['change','blur'],
                    },
                    {
                        // 异步验证需要通过调用callback()，并且在里面抛出new Error()
                        // 抛出的内容为需要提示的信息，和其他方式的message属性的提示一样
                        asyncValidator: (rule, value, callback) => {
                            if (value === '图鸟') {
                                callback(new Error('姓名重复'));
                            } else {
                                // 没有错误，也要执行callback()回调
                                callback();
                            }
                        },
                        trigger: ['blur'],
                    }
                ],
                sex: [
                    {
                        required: true,
                        message: '请选择性别',
                        trigger: 'change'
                    }
                ],
                phone: [
                    {
                        required: true,
                        message: '请输入手机号码',
                        trigger: 'change'
                    }
                ],
                desc: [
                    {
                        min: 5,
                        message: '原由不能少于5个字',
                        trigger: 'change'
                    },
                    {
                        // 正则表达式验证演示
                        pattern: /^[\u4e00-\u9fa5]+$/gi,
                        message: '原由只能包含中文',
                        trigger: 'change'
                    }
                ],

                region: [
                    {
                        required: true,
                        message: '所在地区不能为空',
                        trigger: 'change'
                    }
                ],

            },

            tips: ['无需依赖额外的样式文件','使用tn-toast组件'],
            sectionList: [

                {
                    name: '参数切换',
                    section: [

                    ]
                }
            ]
        }
    },
    onReady() {
        this.$refs.form.setRules(this.rules)
    },
    async mounted() {
        console.log('挂载的时候输出'+this.model.plateNumber)
        // 获取禁止时间段
        try {
            const res = await getDisabledTimes()
            this.disabledTimes = res.data.map(item => ({
                start: new Date(item.ostartdatetime),
                end: new Date(item.oenddatetime),
                name: item.oname
            }))
        } catch (error) {
            console.error('获取禁止时间段失败:', error)
        }
    },
    methods: {
        goToIndex(){
            uni.navigateTo({
                url: '/pages/index/index'
            })
        },
        // goToMyApplyPages(){
        //     uni.navigateTo({
        //         url: '/pages/applyPages/applyPages'
        //     })
        // },
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
        confirmMethod(){
            console.log('当前输出'+this.model.plateNumber)
        },
        backspace() {
            // 删除value的最后一个字符
            if (this.model.plateNumber.length) this.model.plateNumber = this.model.plateNumber.substr(0, this.model.plateNumber.length - 1)
            console.log(this.model.plateNumber);
        },
        showPlateKeyboardMethod(){
            this.showPlateKeyboard = true;
            console.log("执行显示")
        },
        handlePlateChange(val) {
            console.log(val);  // 查看输入的值
            this.model.plateNumber += val;  // 将新输入的值追加到车牌号后面
            console.log(this.model.plateNumber)
              // 查看更新后的车牌号
            // this.$nextTick(() => {
            //     console.log('更新后车牌号:', this.model.plateNumber); // 确保车牌号显示在输入框中
            // });
        },
        deleteLastCharacter() {
            if (this.model.plateNumber.length > 0) {
                this.model.plateNumber = this.model.plateNumber.slice(0, -1); // 删除最后一个字符
            }
        },
        click(event) {
            this[event.methods] && this[event.methods](event)
        },
        // 切换label显示位置
        labelPositionChange(event) {
            switch (event.index) {
                case 0:
                    this.labelPosition = 'left'
                    break
                case 1:
                    this.labelPosition = 'top'
                    break
            }
        },
        // 切换label对其方式
        labelAlignChange(event) {
            switch (event.index) {
                case 0:
                    this.labelAlign = 'left'
                    break
                case 1:
                    this.labelAlign = 'right'
                    break
                case 2:
                    this.labelAlign = 'center'
                    break
            }
        },
        // 切换边框显示
        borderChange(event) {
            this.border = event.index === 0 ? true : false
        },
        // 切换可选项样式
        checkRadioStyleChange(event) {
            switch (event.index) {
                case 0:
                    this.checkboxWidth = 'auto'
                    this.checkboxWrap = false
                    this.radioWidth = 'auto'
                    this.radioWrap = false
                    break
                case 1:
                    this.checkboxWidth = '50%'
                    this.checkboxWrap = false
                    this.radioWidth = '50%'
                    this.radioWrap = false
                    break
                case 2:
                    this.checkboxWidth = 'auto'
                    this.checkboxWrap = true
                    this.radioWidth = 'auto'
                    this.radioWrap = true
                    break
            }
        },
        // 切换错误提示方式
        errorTypeChange(event) {
            switch (event.index) {
                case 0:
                    this.errorType = ['message']
                    break
                case 1:
                    this.errorType = ['toast']
                    break
                case 2:
                    this.errorType = ['border-bottom']
                    break
                case 3:
                    this.errorType = ['border']
                    break
            }
        },


        // 表单提交
        async submit() {
            // 检查用户是否在黑名单中
            const userInfo = this.$store.state.vuex_user || uni.getStorageSync('userInfo')
            if (userInfo && userInfo.blacklist === 1) {
                this.$tn.message.toast('您当前在黑名单中，无法提交申请')
                return
            }

            // 基本表单验证
            if (!this.model.phone) {
                this.$tn.message.toast('请输入手机号码')
                return
            }
            if (!this.model.EnterDateTime) {
                this.$tn.message.toast('请选择进校时间')
                return
            }
            if (!this.model.OutDateTime) {
                this.$tn.message.toast('请选择离校时间')
                return
            }
            if (!this.model.desc) {
                this.$tn.message.toast('请输入来访事由')
                return
            }
            if (!this.model.agreement) {
                this.$tn.message.toast('请勾选协议')
                return
            }

            try {
                // 获取用户token和基本信息
                const token = uni.getStorageSync('token')
                let userInfo = this.$store.state.vuex_user
                console.log('token为',token)
                console.log('userInfo为',userInfo)

                // 如果vuex中没有用户信息，尝试从localStorage加载
                if (!userInfo || !userInfo.uname) {
                    userInfo = uni.getStorageSync('userInfo')
                    if (userInfo) {
                        this.$store.commit('SET_USER_INFO', userInfo)
                    }
                }

                // if (!token || !userInfo) {
                //     this.$tn.message.toast('请先登录')
                //     return uni.navigateTo({
                //         url: '/pages/login/login'
                //     })
                // }

                // 生成当前时间并准备格式化
                const now = new Date()
                const year = now.getFullYear()
                const month = String(now.getMonth() + 1).padStart(2, '0')
                const day = String(now.getDate()).padStart(2, '0')
                const hours = String(now.getHours()).padStart(2, '0')
                const minutes = String(now.getMinutes()).padStart(2, '0')
                const seconds = String(now.getSeconds()).padStart(2, '0')
                
                // 格式化提交时间和生成申请编号
                const applisubtime = `${year}-${month}-${day} ${hours}:${minutes}`
                const anumbering = `${userInfo.uid}${year}${month}${day}${hours}${minutes}${seconds}`

                // 将时间字符串格式化为"yyyy-MM-dd HH:mm"格式
                const parseTime = (timeStr) => {
                    return timeStr // 直接返回已格式化的字符串
                }

                // 准备提交数据
                const applyData = {
                    aname: userInfo.uname, // 姓名
                    asex: userInfo.usex,   // 性别
                    aidnumber: userInfo.uidnumber, // 身份证号
                    aphone: this.model.phone, // 手机号
                    acar: this.model.plateNumber || '', // 车牌号(选填)
                    aentertime: this.model.EnterDateTime, // 进校时间(已格式化的字符串)
                    aouttime: this.model.OutDateTime, // 出校时间(已格式化的字符串)
                    adesc: this.model.desc, // 来访事由
                    applisubtime: applisubtime, // 提交时间
                    anumbering: anumbering, // 申请编号
                    astatus: 0 // 状态默认为0
                }
                console.log('applyData为',applyData)
                // 调用API提交申请
                const res = await submitApply(applyData)
                // console.log()

                if (res.code === 200) {
                    this.$tn.message.toast('提交成功')
                    // 清空表单
                    this.model.phone = ''
                    this.model.plateNumber = ''
                    this.model.EnterDateTime = ''
                    this.model.OutDateTime = ''
                    this.model.desc = ''
                    this.model.agreement = false
                } else {
                    this.$tn.message.toast(res.message || '提交失败')
                }
            } catch (error) {
                console.error('提交申请错误:', error)
                this.$tn.message.toast('网络错误，请重试')
            }
        },
        // 点击actionSheet选择性别
        actionSheetClick(index) {
            uni.hideKeyboard()
            this.model.sex = this.actionSheetList[index].text
        },
        // 点击地区选择器
        regionPickerConfirm(event) {
            this.model.region = event.province.label + '-' + event.city.label + '-' + event.area.label
        },
        // 点击商品类型列选择器
        goodsTypeSelectConfirm(event) {
            this.model.goodsType = `${event[0]['label']}`
        },
        // 多选项值改变事件
        checkboxGroupChange(event) {
            this.model.fruit = event
        },
        // 单选项值改变事件
        radioGroupChange(event) {
            this.model.payType = event
        },
        // 获取验证码
        getCode() {
            if (this.$refs.code.canGetCode) {
                this.$tn.message.loading('正在获取验证码')
                setTimeout(() => {
                    this.$tn.message.closeLoading()
                    this.$tn.message.toast('验证码已经发送')
                    // 通知组件开始计时
                    this.$refs.code.start()
                }, 2000)
            } else {
                this.$tn.message.toast(this.$refs.code.secNum + '秒后再重试')
            }
        },
        // 验证码倒计时时间改变
        codeChange(text) {
            this.codeTips = text
        },
        // 图片有修改
        imageUploadChange(lists) {
            this.model.photo = lists
        },
        // 同意协议状态修改
        agreementCheckChange(event) {
            this.model.agreement = event.value
        },
        timePickerConfirm(event) {
            this.model.entryTime = `${event.hour}:${event.minute}`
        },
        // 检查时间是否在禁止时间段内
        isTimeDisabled(time) {
            const selectedTime = new Date(time.year, time.month - 1, time.day, time.hour, time.minute)
            return this.disabledTimes.some(disabled => {
                return selectedTime >= disabled.start && selectedTime <= disabled.end
            })
        },

        // 禁用日期函数
        disabledDate(time) {
            return this.isTimeDisabled(time)
        },

        // 进校时间选择确认
        confirmEnterTime(event) {
            if (this.isTimeDisabled(event)) {
                this.$tn.message.toast('该时间段禁止入校，请重新选择')
                return
            }
            this.model.EnterDateTime = `${event.year}-${event.month}-${event.day} ${event.hour}:${event.minute}`
        },
        // 离校时间选择确认
        confirmOutTime(event) {
            if (this.isTimeDisabled(event)) {
                this.$tn.message.toast('该时间段禁止入校，请重新选择')
                return
            }
            this.model.OutDateTime = `${event.year}-${event.month}-${event.day} ${event.hour}:${event.minute}`
        },


    }
}
</script>

<style lang="scss" scoped>

.agreement {
    display: flex;
    align-items: center;
    margin: 40rpx 0;

    &-text {
        padding-left: 8rpx;
        color: $tn-font-sub-color;
    }
}

</style>

<style lang="scss" scoped>
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
