import axios from 'axios'
import { Message } from 'element-ui'
import { BASE_PATH } from 'openai/dist/base'

const http = axios.create({
  timeout: 1000 * 60,
  responseType: 'json',
})

/**
 * 请求发送前可以带上token信息，以后加用户权限可以用
 */
http.interceptors.request.use(
  (request) => {
    request.headers.Token = 'token'
    return request
  },
)

/**
 * 拦截全局返回数据，对错误数据预处理
 */
http.interceptors.response.use(
  response => {
    if (response.data.status === 200 && response.data.message != '成功') {
      Message.info(response.data.message);
    }
    else if (response.data.status === 500) {
      Message.warning(response.data.message);
    }
    return Promise.resolve(response)
  },
  error => {
    Message.error(error);
    return Promise.reject(error)
  }
)

export default http
