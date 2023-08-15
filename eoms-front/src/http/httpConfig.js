import axios from 'axios'
import {Message} from 'element-ui'

const http = axios.create({
    timeout: 1000 * 60,
    responseType: 'json',
})

/**
 * 请求发送前可以带上token信息
 */
http.interceptors.request.use(
    (request) => {
        request.headers.authorization = 'mrbase64 mrrest:YWRtaW4mYWRtaW4=' //flux rcs小车验证信息
        return request
    }
)

/**
 * 拦截全局返回数据，对错误数据预处理
 */
http.interceptors.response.use(
    response => {

        if (response.data.status && response.data.status === 200 ) {
            if( response.data.message !== '成功') Message.info(response.data.message);
        }

        if (response.data.status && response.data.status === 500) {
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
