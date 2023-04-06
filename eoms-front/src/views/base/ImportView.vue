<template>
  <div>
    <selected-centre v-model="centre" style="margin-bottom: 10px"></selected-centre>
    <el-alert type="success" class="tip_alert" :closable="false">
      <el-upload
          class="upload-demo"
          drag
          action="/api/ima/alterStockParameter"
          :data="{'centre':centre}"
          multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip" style="text-align: center;color:#67C23A;font-size: 15px ">
          料件库存参数信息批量修改
        </div>
      </el-upload>
    </el-alert>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected-centre';

export default {
  name: "import",
  data() {
    return {
      centre: 'WCTZ'
    }
  },
  components: {
    selectedCentre
  }
}
</script>

<style scoped>

</style>