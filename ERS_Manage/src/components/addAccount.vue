<template>
    <div class="box">
        <div class="text">
            <p style="font-size: 17px;font-weight: bold;margin-left: 15px;padding-top: 10px">添加账号</p>
        </div>
        <div class="area">
            <el-form :model="form">
                <el-form-item label="账号">
                    <el-input v-model="form.adusername"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.adpwd" type="password"></el-input>
                </el-form-item>
<!--                <el-form-item label="用户组">-->
<!--                    <el-select v-model="form.group">-->
<!--                        <el-option-->
<!--                            v-for="item in groups"-->
<!--                            :key="item.value"-->
<!--                            :label="item.label"-->
<!--                            :value="item.value">-->
<!--                        </el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
            </el-form>
        </div>
        <div class="submit">
            <el-button type="primary" @click="submitForm">添加</el-button>
            <el-button @click="resetForm">重置</el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: "addAccount",
    data() {
        return {
            form: {
                adusername: '',
                adpwd: '',
                // group: '1'
            },
            // groups: [
            //     { value: '1', label: '普通管理员' },
            //     { value: '2', label: '超级管理员' }
            // ]
        }
    },
    methods: {
        submitForm() {
            if (!this.form.adusername || !this.form.adpwd) {
                this.$message.error('账号和密码不能为空');
                return;
            }

            this.$axios.post('/api/manage/admin/add', this.form)
                .then(res => {
                    if (res.code === 200) {
                        this.$message.success('添加管理员成功');
                        this.resetForm();
                    } else {
                        this.$message.error(res.message);
                    }
                })
                .catch(err => {
                    console.error(err);
                    this.$message.error('添加管理员失败');
                });
        },
        resetForm() {
            this.form = {
                adusername: '',
                adpwd: '',
                group: '1'
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
