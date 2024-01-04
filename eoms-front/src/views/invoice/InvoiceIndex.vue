<template>
  <el-container>
    <el-aside width="200px">
      <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          background-color="#EBF9E9"
          active-text-color="#67c23a"
          :router="true">
        <el-menu-item index="1" route="/invoice/ocr">
          <i class="el-icon-picture-outline"></i>
          <span slot="title">OCR识别</span>
        </el-menu-item>
        <el-menu-item index="2"  route="/invoice/records">
          <i class="el-icon-document"></i>
          <span slot="title">发票记录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <keep-alive :max="2">
      <router-view></router-view>
      </keep-alive>
    </el-main>
  </el-container>
</template>

<script>

import ocrView from "@/views/invoice/OcrView.vue";

export default {
  name: "invoice",
  computed: {
    ocrView() {
      return ocrView
    }
  },

}
</script>

<style lang="scss">
body {
  #app{
    width: 100%;
    height: 100%;
    .el-container {
      width: 100%;
      height: 100%;
      .el-aside {
        background-color: #EBF9E9;
        color: #000000;
      }
      .el-main {
        background-color: #F5FCF4;
        color: #000000;
      }
    }
  }

}

</style>