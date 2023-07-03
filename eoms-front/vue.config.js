const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false, //忽略lint检测
  //请求代理
  devServer: {
    open: true, 
    host: "localhost",
    port: "80",
    https: false,
    hot: "only", 
    proxy: {
      '/api': {
        target: 'http://localhost:18080',
        // target: 'http://172.17.200.100:18080',
        changeOrigin: true,
        pathRewrite: {
          '/api': ''
        }
      },
    }
  }
});
