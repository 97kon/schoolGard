import { post, get } from './index.js'

// 用户登录
export const login = (username, password) => {
  return post('/api/Client/login', {
    username,
    password
  })
}

// 用户注册
export const register = (userData) => {
  return post('/api/client/register', userData)
}

// 提交申请记录
export const submitApply = (applyData) => {
  return post('/api/client/insertrecord', applyData)
}

// 获取禁止时间段
export const getDisabledTimes = () => {
  return get('/api/manage/getDisabledTimes')
}

// 根据姓名查询申请记录
export const getApplyRecordsByName = (aname) => {
  console.log('请求参数:', aname);
  const url = `/api/client/selectallByaname?aname=${encodeURIComponent(aname)}`;
  console.log('请求URL:', url);
  return get(url);
}

// 查询用户最近一条已通过的申请记录
export const selectLatestApprovedApply = (aname) => {
  return get(`/api/client/selectapplyrecordByanameandastatus?aname=${encodeURIComponent(aname)}&astatus=1`);
}

// 更新申请状态
export const updateApplyStatus = (anumbering, astatus) => {
  return get(`/api/client/updateastatusByanumberingandastatus?anumbering=${encodeURIComponent(anumbering)}&astatus=${encodeURIComponent(astatus)}`);
}

// 取消申请
export const cancelApply = (anumbering) => {
  return get(`/api/client/updateastatusByanumbering?anumbering=${encodeURIComponent(anumbering)}`);
}
// 获取验证码
export const getVerCode = (uphone) => {
  return post(`/api/client/user/getveritycode?uphone=${encodeURIComponent(uphone)}`)
}

// 手机验证码登录
export const loginBySms = (uphone,code) => {
  return post(`/api/client/user/clientloginbyuphone?uphone=${encodeURIComponent(uphone)}&code=${encodeURIComponent(code)}`)
}
// 修改用户信息
export const updateUserClient = (userData) => {
  return post('/api/manage/user/update',userData)
}

// 其他接口可以在这里继续添加
export default {
  login,
  submitApply,
  getDisabledTimes,
  getApplyRecordsByName,
  cancelApply,
  getVerCode,
  loginBySms,
  updateUserClient
}
