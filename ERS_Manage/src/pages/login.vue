<template>
    <div class="box">
<!--                <span id="logo"></span>-->
        <img id="logo_crms" src="@/assets/img/logo.png">
        <h2 style="color: #005bac">华北科技学院</h2>
        <h4 style="color: #005bac">入校登记系统--后台管理</h4>
<!--        使用了表单对登录录入的进行数据绑定-->
        <el-form :model="form" ref="form">
            <div class="box1">
<!--                下面这块是username用户名的部分，在表单item中还使用了rules进行数据校验-->
                <div class="UserName">
                    <p>Username or email address</p>
                </div>
                <el-form-item prop="username" :rules="[
                  {required: true, message:'账号不能为空', trigger: 'blur'}
              ]">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
<!--                以下是密码的部分-->
                <div class="pwd">
                    <p>Password</p>
                    <router-link to="/passwordreset"><a>Forgot password?</a></router-link>
                </div>
                <el-form-item prop="pwd" :rules="[
                  {required: true, message: '密码不能为空', trigger:'blur'}
              ]">
                    <el-input v-model="form.pwd" type="password" show-password></el-input>
                </el-form-item>
<!--                这是提交按钮，绑定了一个onsubmit方法-->
                <el-button  style="background-color: #005bac;color: white" @click="onsubmit">Sign in</el-button>
            </div>
        </el-form>
        <div class="box2">
            <p>New User?</p>
            <router-link to="/register"><a href="">Create an account</a></router-link>
        </div>
<!--        <div class="footer">-->
<!--            <a href="">Terms</a>-->
<!--            <a href="">Privacy</a>-->
<!--            <a href="">Security</a>-->
<!--            <p>Contact MIS</p>-->
<!--        </div>-->
    </div>
</template>

<script>
// import {store} from "@/store/store";

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "login",
    data(){
        return{
            typeThis:0,
            // store,
            /*下面是form表单，和上面表单输入框里面录入的数据通过v-model进行了双向数据绑定了的*/
            form:{
                username:'',
                pwd:'',
                // type:''
            }
        }
    },
    // 这一块是对提交按钮的方法实现，一般会写post接口将账号密码发送到后端的接口进行登录验证，来实现登录的功能
    methods:{
        async onsubmit(){
          try {
            // 先验证表单
            await this.$refs.form.validate()

            // 调用登录接口
            const res = await this.$axios.post('/api/manage/login', {
              username: this.form.username,
              password: this.form.pwd
            })

            // 登录成功处理
            if(res.code === 200) {
              // 存储token等操作
              // localStorage.setItem('token', res.data.token)
              this.$store.dispatch('login', {
                username: this.form.username
              })
              this.$message.success('登录成功')
              setTimeout(() => {
                this.$router.push('/index')
              }, 1000)
            } else {
              this.$message.error(res.message || '登录失败')
            }
          } catch (error) {
            // 错误处理
            if(error.response) {
              this.$message.error(error.response.data.message || '登录失败')
            } else {
              this.$message.error('请求失败: ' + error.message)
            }
          }
        }
    }
}
</script>

<style scoped>
html,body,#app{
    height: 100%;
    margin: 0px;
    padding: 0px;
}
.box{
    text-align: center;
    margin: auto;
    padding-top: 50px;
}
#logo_crms{
    padding-top: 30px;
    width: 100px;
    height: 100px;
    margin: auto;
}
.box1{
    width: 300px;
    margin: auto;
    border: #BCBCBC 1px solid;
    border-radius: 10px;
    padding-bottom: 20px;
    padding-left: 15px;
    padding-right: 15px;
    background-color: #F6F8FA;

}
.UserName{
    /*height: 50px;*/
    font-size: 13px;
    position: relative;
    /*border: #333333 1px solid;*/
}
.UserName > p{
    /*border: #333333 1px solid;*/
    margin-bottom: 10px;
    text-align: left;
}
.box2{
    width: 300px;
    border: #BCBCBC 1px solid;
    border-radius: 10px;
    padding-left: 15px;
    padding-right: 15px;
    background-color: #F6F8FA;
    margin: 20px auto auto;
    display: flex;
    flex-direction: row;
    font-size: 15px;
}
.box2 > a{
    flex: 1;
    line-height: 50px;
    padding-top: 5px;
    padding-bottom: 5px;
}
.box2 > p{
    flex: 1;
    padding-top: 5px;
    padding-bottom: 5px;
}
.footer{
    width: 400px;
    border-radius: 20px;
    padding-bottom: 20px;
    padding-left: 15px;
    padding-right: 15px;
    margin: 20px auto auto;
    display: flex;
    flex-direction: row;
    font-size: 5px;
}
.footer > p{
    padding-top: 12px;
    margin-right: 0px;
    flex: 2;
}
.footer > a{
    flex: 1;

    line-height: 50px;
}
.pwd{
    display: flex;
    flex-direction: row;
    /*border: #333333 1px solid;*/
    font-size: 13px;
    margin-top: 10px;
    position: relative;
}
.pwd > p{
    flex: 1;
    /*border: #333333 1px solid;*/
    text-align: left;
    margin-bottom: 10px;
    /*margin-top: 10px;*/
}
.pwd > a{
    flex: 1;
    /*border: #333333 1px solid;*/
    /*margin-bottom: 0px;*/
    position: absolute;
    bottom: 10px;
    right: 0;
}
.box1 > button{
    /*font-size: 50px;*/
    margin-top: 15px;
    width: 300px;
    /*height: 50px;*/

}
</style>
