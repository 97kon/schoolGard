const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8093,
    proxy: {
      '/api': {
        target: 'http://localhost:8090', // 后端地址
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
