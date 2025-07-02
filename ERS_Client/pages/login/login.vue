<template>
    <view class="template-login">
        <view class="login">
            <view class="login__bg login__bg--top">
                <image class="rocket" src="/static/img/logo.png" mode="widthFix"></image>
            </view>

            <view class="login__wrapper">
                <view class="login__mode tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-center">
                    <view class="login__mode__item tn-flex-1" :class="[{'login__mode__item--active': currentModeIndex === 0}]" @tap.stop="modeSwitch(0)">
                        登录
                    </view>
                    <view class="login__mode__item tn-flex-1" :class="[{'login__mode__item--active': currentModeIndex === 1}]" @tap.stop="modeSwitch(1)">
                        注册
                    </view>
                    <view class="login__mode__slider" style="background-color: #005bac" :style="[modeSliderStyle]"></view>
                </view>

                <view class="login__info tn-flex tn-flex-direction-column tn-flex-col-center tn-flex-row-center">
                    <!-- 登录 -->
                    <block v-if="currentModeIndex === 0">
<!--                        <view class="login__info__item__button" hover-class="tn-hover" :hover-stay-time="150" @click="useSmsLogin = !useSmsLogin">-->
<!--                            {{ useSmsLogin ? '账号密码登录' : '手机验证码登录' }}-->
<!--                        </view>-->

                        <block v-if="!useSmsLogin">
                            <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                                <view class="login__info__item__input__left-icon">
                                    <view class="tn-icon-phone"></view>
                                </view>
                                <view class="login__info__item__input__content">
                                    <input v-model="loginForm.username" maxlength="20" placeholder-class="input-placeholder" placeholder="请输入登录手机号码" />
                                </view>
                            </view>

                            <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                                <view class="login__info__item__input__left-icon">
                                    <view class="tn-icon-lock"></view>
                                </view>
                                <view class="login__info__item__input__content">
                                    <input v-model="loginForm.password" :password="!showPassword" placeholder-class="input-placeholder" placeholder="请输入登录密码" />
                                </view>
                                <view class="login__info__item__input__right-icon" @click="showPassword = !showPassword">
                                    <view :class="[showPassword ? 'tn-icon-eye' : 'tn-icon-eye-hide']"></view>
                                </view>
                            </view>
                        </block>

                        <block v-else>
                            <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                                <view class="login__info__item__input__left-icon">
                                    <view class="tn-icon-phone"></view>
                                </view>
                                <view class="login__info__item__input__content">
                                    <input v-model="loginForm.phone" maxlength="11" placeholder-class="input-placeholder" placeholder="请输入手机号码" />
                                </view>
                            </view>

                            <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                                <view class="login__info__item__input__left-icon">
                                    <view class="tn-icon-verification"></view>
                                </view>
                                <view class="login__info__item__input__content login__info__item__input__content--verify-code">
                                    <input v-model="loginForm.code" maxlength="6" placeholder-class="input-placeholder" placeholder="请输入验证码" />
                                </view>
                                <view class="login__info__item__input__right-verify-code" @click="getVerifyCode">
                                    <view class="tn-verification-code" style="color: gray">{{ countdown > 0 ? `${countdown}s后重试` : '获取验证码' }}</view>
                                </view>
                            </view>
                        </block>
<!--                        <view class="" hover-class="tn-hover" :hover-stay-time="150" @click="useSmsLogin = !useSmsLogin">-->
<!--                            {{ useSmsLogin ? '账号密码登录' : '手机验证码登录' }}-->
<!--                        </view>-->
                    </block>

                    <!-- 注册 -->
                    <block v-if="currentModeIndex === 1">
                        <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                            <view class="login__info__item__input__left-icon">
                                <view class="tn-icon-my-lack"></view>
                            </view>
                            <view class="login__info__item__input__content">
                                <input v-model="registerForm.uname" maxlength="20" placeholder-class="input-placeholder" placeholder="请输入账号名" />
                            </view>
                        </view>
                        <!--                        密码-->
                        <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                            <view class="login__info__item__input__left-icon">
                                <view class="tn-icon-lock"></view>
                            </view>
                            <view class="login__info__item__input__content">
                                <input v-model="registerForm.upwd" :password="!showPassword" placeholder-class="input-placeholder" placeholder="请输入登录密码" />
                            </view>
                            <view class="login__info__item__input__right-icon" @click="showPassword = !showPassword">
                                <view :class="[showPassword ? 'tn-icon-eye' : 'tn-icon-eye-hide']"></view>
                            </view>
                        </view>
                        <!--                        手机-->
                        <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                            <view class="login__info__item__input__left-icon">
                                <view class="tn-icon-phone"></view>
                            </view>
                            <view class="login__info__item__input__content">
                                <input v-model="registerForm.uphone" maxlength="20" placeholder-class="input-placeholder" placeholder="请输入注册手机号码" />
                            </view>
                        </view>
                        <!--                        身份证-->
                        <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                            <view class="login__info__item__input__left-icon">
                                <view class="tn-icon-bankcard"></view>
                            </view>
                            <view class="login__info__item__input__content">
                                <input v-model="registerForm.uidnumber" maxlength="20" placeholder-class="input-placeholder" placeholder="请输入身份证号" />
                            </view>
                        </view>
                        <!--                        性别-->
                        <view class="login__info__item__input tn-flex tn-flex-direction-row tn-flex-nowrap tn-flex-col-center tn-flex-row-left">
                            <view class="login__info__item__input__left-icon">
                                <view class="tn-icon-sex"></view>
                            </view>
                            <view class="login__info__item__input__content">
                                <tn-input v-model="registerForm.usex" type="select" placeholder="请选择性别"
                                    :selectOpen="actionSheetShow"
                                    @click="actionSheetShow = true">
                                </tn-input>
                            </view>
                        </view>
                        <!-- 其他注册字段... -->
                    </block>

                    <view class="login__info__item__button" hover-class="tn-hover" :hover-stay-time="150" @click="handleSubmit">
                        {{ currentModeIndex === 0 ? '登录' : '注册'}}
                    </view>
                    <view class="" style="margin-bottom: 20rpx;color: gray" hover-class="tn-hover" :hover-stay-time="150" @click="useSmsLogin = !useSmsLogin">
                        {{ useSmsLogin ? '账号密码登录' : '手机验证码登录' }}
                    </view>
                </view>
            </view>
        </view>
        <!-- 性别选项 -->
        <tn-action-sheet
            v-model="actionSheetShow"
            :list="actionSheetList"
            @click="actionSheetClick"
        ></tn-action-sheet>
    </view>
</template>

<script>
import template_page_mixin from '@/libs/mixin/template_page_mixin.js'
import {getVerCode, handleSubmit, login, loginBySms, register} from '@/common/request/api'
export default {
    name: 'login-demo-1',
    mixins: [template_page_mixin],
    data() {
        return {
            actionSheetShow: false,
            currentModeIndex: 0,
            modeSliderStyle: { left: 0 },
            showPassword: false,
            tips: '获取验证码',
            loginForm: {
                username: '',
                password: '',
                phone: '', // 手机号
                code: ''   // 验证码
            },
            useSmsLogin: false, // 是否使用短信验证码登录
            countdown: 0, // 验证码倒计时
            timer: null, // 定时器
            registerForm: {
                uname:'',
                usex: '',
                upwd: '',
                uidnumber: '',
                uphone: '',
            },
            actionSheetList:[
                {
                    text: '男',
                    value: '1'
                },
                {
                    text: '女',
                    value: '2'
                }
            ]
        }
    },
    watch: {
        currentModeIndex(value) {
            const sliderWidth = uni.upx2px(476 / 2)
            this.modeSliderStyle.left = `${sliderWidth * value}px`
        }
    },
    methods: {
        // 点击actionSheet选择性别
        actionSheetClick(index) {
            uni.hideKeyboard()
            this.registerForm.usex = this.actionSheetList[index].value
        },
        modeSwitch(index) {
            this.currentModeIndex = index
            this.showPassword = false
        },
        // 获取验证码
        getVerifyCode() {
            if (this.countdown > 0) return

            if (!this.loginForm.phone) {
                uni.$tn.message.toast('请输入手机号码')
                return
            }

            // 这里调用获取验证码的API
            uni.$tn.message.toast('验证码已发送')
            // 示例代码，实际需要替换为你的API调用
            this.countdown = 60
            this.timer = setInterval(() => {
                this.countdown--
                if (this.countdown <= 0) {
                    clearInterval(this.timer)
                }
            }, 1000)

            try {
                let uphone = this.loginForm.phone
                const res = getVerCode(uphone)
                if (res.code === 200){
                    console.log(res.code)
                }
                if (res.code === 400){
                    uni.$tn.message.toast('验证码发送失败400')
                }
                if (res.code === 500){
                    uni.$tn.message.toast('验证码发送失败500')

                }
            }catch(error){
                console.error('登录错误:', error)

            }



        },

        async handleSubmit() {
            if (this.currentModeIndex === 0) {
                if (this.useSmsLogin) {
                    // 手机验证码登录逻辑
                    if (!this.loginForm.phone || !this.loginForm.code) {
                        uni.$tn.message.toast('请输入手机号和验证码')
                        return
                    }
                    try {
                        // 这里调用手机验证码登录API
                        // 示例代码，实际需要替换为你的API调用
                        const res = await loginBySms(this.loginForm.phone, this.loginForm.code)
                        if (res.code === 200) {
                            uni.setStorageSync('token', res.data.id)
                            uni.setStorageSync('userInfo', res.data)
                            this.$store.commit('SET_USER_INFO', res.data)
                            uni.$tn.message.toast(res.message)
                            uni.redirectTo({
                                url: '/pages/applyPages/applyPages'
                            })
                        } else {
                            uni.$tn.message.toast(res.message || '登录失败')
                        }
                    } catch (error) {
                        console.error('登录错误:', error)
                        uni.$tn.message.toast('网络错误，请重试')
                    }
                } else {
                    // 账号密码登录逻辑
                    try {
                        const res = await login(this.loginForm.username, this.loginForm.password)
                        console.log(res)
                        if (res.code === 200) {
                            uni.setStorageSync('token', res.data.id)
                            uni.setStorageSync('userInfo', res.data)
                            this.$store.commit('SET_USER_INFO', res.data)
                            uni.$tn.message.toast(res.message)
                            uni.redirectTo({
                                url: '/pages/applyPages/applyPages'
                            })
                        } else {
                            uni.$tn.message.toast(res.message || '登录失败')
                        }
                    } catch (error) {
                        console.error('登录错误:', error)
                        uni.$tn.message.toast('网络错误，请重试')
                    }
                }
            } else {
                try {
                    const res = await register(this.registerForm)
                    if (res.code === 200) {
                        uni.$tn.message.toast('注册成功')
                        this.currentModeIndex = 0 // 切换到登录界面
                    } else {
                        uni.$tn.message.toast(res.message || '注册失败')
                    }
                } catch (error) {
                    console.error('注册错误:', error)
                    uni.$tn.message.toast('网络错误，请重试')
                }
            }
        }
    }
}
</script>

<style lang="scss" scoped>
/* 原有样式保持不变... */
</style>

<style lang="scss" scoped>
@import '@/static/css/templatePage/custom_nav_bar';
/* 悬浮 */
.rocket-sussuspension{
    animation: suspension 3s ease-in-out infinite;
}

@keyframes suspension {
    0%, 100% {
        transform: translate(0 , 0);
    }
    50% {
        transform: translate(-0.8rem , 1rem);
    }
}

.login {
    position: relative;
    height: 100%;
    z-index: 1;

    /* 背景图片 start */
    &__bg {
        z-index: -1;
        position: fixed;

        &--top {
            top: 0;
            left: 0;
            right: 0;
            width: 100%;

            .bg {
                width: 750rpx;
                will-change: transform;
            }
            .rocket {
                //margin: 50rpx 28%;
                margin: 50rpx 30%;
                width: 300rpx;
                will-change: transform;
            }
        }

        &--bottom {
            bottom: -10rpx;
            left: 0;
            right: 0;
            width: 100%;
            // height: 144px;
            margin-bottom: env(safe-area-inset-bottom);

            image {
                width: 750rpx;
                will-change: transform;
            }
        }
    }
    /* 背景图片 end */

    /* 内容 start */
    &__wrapper {
        margin-top: 403rpx;
        width: 100%;
    }

    /* 切换 start */
    &__mode {
        position: relative;
        margin: 0 auto;
        width: 476rpx;
        height: 77rpx;
        background-color: #FFFFFF;
        box-shadow: 0rpx 10rpx 50rpx 0rpx rgba(0, 3, 72, 0.1);
        border-radius: 39rpx;

        &__item {
            height: 77rpx;
            width: 100%;
            line-height: 77rpx;
            text-align: center;
            font-size: 31rpx;
            color: #908f8f;
            letter-spacing: 1em;
            text-indent: 1em;
            z-index: 2;
            transition: all 0.4s;

            &--active {
                font-weight: bold;
                color: #FFFFFF;
            }
        }

        &__slider {
            position: absolute;
            height: inherit;
            width: calc(476rpx / 2);
            border-radius: inherit;
            box-shadow: 0rpx 18rpx 72rpx 18rpx rgba(0, 195, 255, 0.1);
            z-index: 1;
            transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
        }
    }
    /* 切换 end */

    /* 登录注册信息 start */
    &__info {
        margin: 0 30rpx;
        margin-top: 105rpx;
        padding: 30rpx 51rpx;
        padding-bottom: 0;
        border-radius: 20rpx;
        background-color: #ffff;
        box-shadow: 0rpx 10rpx 50rpx 0rpx rgba(0, 3, 72, 0.1);

        &__item {

            &__input {
                margin-top: 59rpx;
                width: 100%;
                height: 77rpx;
                border: 1rpx solid #E6E6E6;
                border-radius: 39rpx;

                &__left-icon {
                    width: 10%;
                    font-size: 44rpx;
                    margin-left: 20rpx;
                    color: #AAAAAA;
                }

                &__content {
                    width: 80%;
                    padding-left: 10rpx;

                    &--verify-code {
                        width: 56%;
                    }

                    input {
                        font-size: 24rpx;
                        // letter-spacing: 0.1em;
                    }
                }

                &__right-icon {
                    width: 10%;
                    font-size: 44rpx;
                    margin-right: 20rpx;
                    color: #AAAAAA;
                }

                &__right-verify-code {
                    width: 34%;
                    margin-right: 20rpx;
                }
            }

            &__button {
                margin-top: 75rpx;
                margin-bottom: 39rpx;
                width: 100%;
                height: 77rpx;
                text-align: center;
                font-size: 31rpx;
                font-weight: bold;
                line-height: 77rpx;
                letter-spacing: 1em;
                text-indent: 1em;
                border-radius: 39rpx;
                color: white;
                //box-shadow: 1rpx 10rpx 24rpx 0rpx rgba(60, 129, 254, 0.35);
                background-color: #005bac;

            }

            &__tips {
                margin: 30rpx 0;
                color: #AAAAAA;
            }
        }
    }
    /* 登录注册信息 end */

    /* 登录方式切换 start */
    &__way {
        margin: 0 auto;
        margin-top: 110rpx;

        &__item {
            &--icon {
                width: 77rpx;
                height: 77rpx;
                font-size: 50rpx;
                border-radius: 100rpx;
                margin-bottom: 18rpx;
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
                    background-image: url(https://resource.tuniaokj.com/images/cool_bg_image/icon_bg5.png);
                }
            }
        }
    }
    /* 登录方式切换 end */
    /* 内容 end */

}

/deep/.input-placeholder {
    font-size: 24rpx;
    color: #E6E6E6;
}

</style>
