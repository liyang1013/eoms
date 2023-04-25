<template>
  <div class="bk">
    <el-card shadow="always" style="margin: 200px auto;">
      总是显示
    </el-card>
    <div class="login-box">

    </div>
  </div>

</template>
<script>
export default {
  name: 'login',
  data() {
    return {
      zx: {
        zx01: 'JX2302304',
        zx02: 'JX2302304'
      }
    }
  },
  methods: {
    login() {
      this.$http.post('/api/zx/login', this.zx).then(res => {
        localStorage.setItem('token', res.data.result.token);
        this.$store.commit('SET_USERNAME', res.data.result.zx01)
        this.$router.push('/');
      })
    }
  }
}
</script>

<style scoped lang="scss">

.login-box {
  border: 1px solid #DCDFE6;
  width: 420px;
  height: 250px;
  margin: 200px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
  background-color: rgba(255, 255, 255, 0.5);
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}

.bk {
  height: 100%;
  width: 100%;
  background: url(../image/background.jpg) no-repeat;
  overflow: hidden;
}

</style>