<template>
  <div class="background">
      <el-form :model="zx"  label-width="80px" class="login-box" label-position="left">
        <h3 class="login-title">欢迎登录</h3>
        <el-form-item label="用户名:">
          <el-input type="text" placeholder="请输入用户名" v-model="zx.zx01"/>
        </el-form-item>
        <el-form-item label="密码:">
          <el-input type="password" placeholder="请输入密码" v-model="zx.zx10"/>
        </el-form-item>
        <el-form-item>
          <el-col :span="9">
            <el-button type="primary" @click="login">登录</el-button>
          </el-col>
        </el-form-item>
      </el-form>
  </div>
</template>
<script>

import store from "@/store";
export default {
  name: 'login',
  data() {
    return {
      zx: {
        zx01: 'JX2302304',
        zx10: 'JX2302304'
      }
    }
  },
  methods: {
    login() {
      this.$http.post('/api/zx/login', this.zx).then(res => {

        if(res.data.status === 200){
          localStorage.setItem('token', res.data.result.token);
          localStorage.setItem('username', res.data.result.zx01);
          this.$router.push('/home');
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.background {
  height: 100%;
  width: 100%;
  background: url(../image/background.jpg) no-repeat;
  overflow: hidden;
  .login-box {
    border: 1px solid #DCDFE6;
    width: 420px;
    margin: 200px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
    background-color: rgba(255, 255, 255, 0.5);
    .login-title {
      text-align: center;
      margin: 0 auto 40px auto;
      color: #303133;
    }
  }
}
</style>