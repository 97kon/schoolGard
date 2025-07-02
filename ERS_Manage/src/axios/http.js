import axios from 'axios'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8090', // 后端API地址
  timeout: 10000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前可以做一些处理
    // 例如：如果需要token可以在这里统一设置
    // config.headers['Authorization'] = 'Bearer ' + getToken()
    return config
  },
  error => {
    // 对请求错误做些什么
    console.log(error) 
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    // 对响应数据做点什么
    const res = response.data
    // 这里可以根据后端返回的状态码进行统一处理
    if (res.code !== 200) {
      // 例如：提示错误信息
      console.error(res.message || 'Error')
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    // 对响应错误做点什么
    console.log('err' + error)
    return Promise.reject(error)
  }
)

export default service
