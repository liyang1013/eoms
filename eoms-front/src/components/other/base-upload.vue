<template>
  <div>
    <el-alert type="success" center :closable="false">
      <el-link type="primary" :underline="false" :href="href">{{ msg }}</el-link>
    </el-alert>
    <el-upload
        class="upload-demo"
        drag
        :action=url
        :data="{'centre':centre}"
        :on-success="upload_success"
        :on-error="upload_err"
        accept=".xlsx,.xls"
        multiple>
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip" style="text-align: center;color:#67C23A;font-size: 15px ">
        {{ title }}
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: "base-upload",
  props: {
    url: {
      type: String,
      default: null
    },
    title: {
      type: String,
      default: '默认模板'
    },
    centre: {
      type: String,
      default: 'WCTZ'
    },
    msg: {
      type: String,
      default: '导入模板下载'
    },
    href:{
      type: String,
      default: 'javascript:void(0)'
    }
  },
  methods: {
    upload_success(data) {
      this.$message.success(data.message)
    },
    upload_err(data) {
      this.$message.warning(data.message)
    }
  },
}
</script>

<style scoped>

</style>