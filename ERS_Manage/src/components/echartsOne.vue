<template>
<!--    该vue组件是后台首页的页面-->
    <div class="box">
        <div class="carousel-container">
            <el-carousel :interval="4000" type="card" height="300px">
                <el-carousel-item v-for="item in carouselImages" :key="item">
                    <img :src="item" class="carousel-image" />
                </el-carousel-item>
            </el-carousel>
        </div>

        <div class="description-container">
            <h2>系统功能说明</h2>
            
            <h3>1. 数据可视化</h3>
            <p>• 展示系统关键数据的图表分析</p>
            <p>• 提供直观的数据趋势和统计信息</p>
            
            <h3>2. 账号管理</h3>
            <p>• 账号列表：查看和管理所有用户账号</p>
            <p>• 添加账号：创建新的系统用户账号</p>
            <p>• 重置密码：为忘记密码的用户重置登录凭证</p>
            
            <h3>3. 审批申请</h3>
            <p>• 提交各类审批申请</p>
            <p>• 查看申请处理状态</p>
            
            <h3>4. 出入记录</h3>
            <p>• 查询人员出入记录</p>
            <p>• 支持按时间、人员等条件筛选</p>
            
            <h3>5. 系统设置</h3>
            <p>• 配置系统基础参数</p>
            <p>• 管理权限和角色设置</p>
            
            <h2>使用指南</h2>
            <p>1. 首次使用请通过登录页面进入系统</p>
            <p>2. 左侧菜单栏选择对应功能模块</p>
            <p>3. 账号管理功能需要管理员权限</p>
            <p>4. 数据可视化页面自动更新最新数据</p>
        </div>
    </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
    name: "echartsOne",
    setup() {
        const carouselImages = [
            require('@/assets/img/lbt1.jpg'),
            require('@/assets/img/lbt2.jpg'),
            require('@/assets/img/lbt3.jpg')
        ]

        return {
            carouselImages
        }
    }
})
</script>

<style scoped>
.box {
    padding: 0;
    height: 100%;
    width: 100%;
    background-color: #EDEFF1;
    padding: 20px;
    box-sizing: border-box;
}

.carousel-container {
    margin-bottom: 30px;
}

.carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.description-container {
    background: white;
    padding: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.description-container h2 {
    color: #409EFF;
    margin-bottom: 15px;
}

.description-container p {
    margin: 8px 0;
    color: #606266;
}
</style>
