// 基础请求配置
const BASE_URL = 'http://localhost:8090'

// 请求拦截器
const requestInterceptor = (config) => {
  // 添加token等公共请求头
  config.header = config.header || {}
  const token = uni.getStorageSync('token')
  if (token) {
    config.header['Authorization'] = `Bearer ${token}`
  }
  return config
}

// 响应拦截器
const responseInterceptor = (response) => {
  const { statusCode, data } = response
  if (statusCode === 200) {
    return data
  } else {
    return Promise.reject(response)
  }
}

// 错误处理
const errorHandler = (error) => {
  console.error('请求错误:', error)
  uni.showToast({
    title: '网络请求失败',
    icon: 'none'
  })
  return Promise.reject(error)
}

// 请求方法封装
const request = (options) => {
  options = requestInterceptor(options) || options
  options.url = BASE_URL + options.url
  
  return new Promise((resolve, reject) => {
    uni.request({
      ...options,
      success: (res) => {
        const processed = responseInterceptor(res)
        resolve(processed)
      },
      fail: (err) => {
        const processed = errorHandler(err)
        reject(processed)
      }
    })
  })
}

export default request
