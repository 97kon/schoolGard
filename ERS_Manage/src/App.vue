<template>
<!--  <el-container>-->
<!--    <el-header >-->
<!--      <div class="header-content">-->
<!--        <div class="logo">入校登记系统</div>-->
<!--        <div class="user-info">-->
<!--          <el-dropdown>-->
<!--            <span class="el-dropdown-link">-->
<!--              欢迎您，{{ userInfo.username || '管理员' }}-->
<!--              <el-icon class="el-icon&#45;&#45;right">-->
<!--                <arrow-down />-->
<!--              </el-icon>-->
<!--            </span>-->
<!--            <template #dropdown>-->
<!--              <el-dropdown-menu>-->
<!--                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>-->
<!--              </el-dropdown-menu>-->
<!--            </template>-->
<!--          </el-dropdown>-->
<!--        </div>-->
<!--      </div>-->
<!--    </el-header>-->
<!--    <el-main>-->
<!--      <router-view></router-view>-->
<!--    </el-main>-->
<!--  </el-container>-->
    <router-view></router-view>
</template>

<script>
// import { ArrowDown } from '@element-plus/icons-vue'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {

  },
  computed: {
    ...mapState({
      userInfo: state => state.user || { username: '管理员' }
    })
  },
  methods: {
    logout() {
      this.$store.dispatch('logout')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
/*.el-header {*/
/*  background-color: #409EFF;*/
/*  color: white;*/
/*  line-height: 60px;*/
/*}*/
/*.header-content {*/
/*  display: flex;*/
/*  justify-content: space-between;*/
/*  align-items: center;*/
/*  height: 100%;*/
/*}*/
/*.logo {*/
/*  font-size: 20px;*/
/*  font-weight: bold;*/
/*}*/
/*.el-dropdown-link {*/
/*  color: white;*/
/*  cursor: pointer;*/
/*  display: flex;*/
/*  align-items: center;*/
/*}*/
</style>
