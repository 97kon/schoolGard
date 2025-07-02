<template>
    <div class="box">
        <div class="text">
            <p style="font-size: 17px;font-weight: bold;margin-left: 15px;padding-top: 10px">修改密码</p>
        </div>
        <div class="area">
            <el-form :model="form">
                <el-form-item label="账号">
                    <el-input v-model="form.adusername"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="form.newPassword" type="password"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码">
                    <el-input v-model="form.confirmPassword" type="password"></el-input>
                </el-form-item>
            </el-form>
        </div>
        <div class="submit">
            <el-button type="primary" @click="submitForm">确定</el-button>
            <el-button @click="resetForm">重置</el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: "resetPwd",
    data() {
        return {
            form: {
                adusername: '',
                newPassword: '',
                confirmPassword: ''
            }
        }
    },
    methods: {
        submitForm() {
            if (!this.form.adusername || !this.form.newPassword) {
                this.$message.error('账号和新密码不能为空');
                return;
            }

            if (this.form.newPassword !== this.form.confirmPassword) {
                this.$message.error('两次输入的密码不一致');
                return;
            }

            this.$axios.post('/api/manage/admin/resetPwd',
            //     {
            //     adminId: this.form.adusername,
            //     newPassword: this.form.newPassword
            // }
                null,
                {
                    params:{
                        adminId: this.form.adusername,
                        newPassword: this.form.newPassword
                    }
                }
            )
            .then(res => {
                if (res.code === 200) {
                    this.$message.success('密码修改成功');
                    this.resetForm();
                } else {
                    this.$message.error(res.message);
                }
            })
            .catch(err => {
                console.error(err);
                this.$message.error('密码修改失败');
            });
        },
        resetForm() {
            this.form = {
                adusername: '',
                newPassword: '',
                confirmPassword: ''
            };
        }
    }
}
</script>

<style scoped>
.box{
    margin: 0;
    padding: 0;
    width: 100%;
    background-color: white;
}
.text {
    border-bottom: 2px solid lightgray;
}
.area{
    width: 500px;
    margin: 30px;
}
.submit{
    margin-left: 70px;
    padding-bottom: 30px;
}
</style>
