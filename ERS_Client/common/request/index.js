import request from './request.js'

// GET请求
export const get = (url, params = {}, options = {}) => {
  return request({
    url,
    method: 'GET',
    data: params,
    ...options
  })
}

// POST请求
export const post = (url, data = {}, options = {}) => {
  return request({
    url,
    method: 'POST',
    data,
    ...options
  })
}

// PUT请求
export const put = (url, data = {}, options = {}) => {
  return request({
    url,
    method: 'PUT',
    data,
    ...options
  })
}

// DELETE请求
export const del = (url, data = {}, options = {}) => {
  return request({
    url,
    method: 'DELETE',
    data,
    ...options
  })
}

export default {
  get,
  post,
  put,
  delete: del
}
