<template>
  <div>
    <selected-centre v-model="centre" style="margin-bottom: 10px"></selected-centre>
    <el-alert type="success" class="tip_alert" :closable="false">
     <el-card class="upload_card"  shadow="never">
       <baseUpload url="/api/ima/alterStockParameter" title="料件库存参数信息批量修改" :centre="centre" href="/static/料件库存信息导入模板.xlsx"></baseUpload>
     </el-card>
      <el-card class="upload_card" shadow="never">
        <baseUpload url="/api/ima/imaPackage" title="产品包装批量维护" :centre="centre" href="/static/产品包装导入模板.xlsx"></baseUpload>
      </el-card>
      <el-card class="upload_card" shadow="never">
        <baseUpload url="/api/ima/imaClass" title="产品分类编码批量写入" :centre="centre" href="/static/产品分类编码批量写入模板.xlsx"></baseUpload>
      </el-card>
      <el-card class="upload_card" shadow="never">
        <baseUpload url="/api/sfb/alterCostCenter" title="工单成本中心批量维护" :centre="centre" href="/static/工单成本中心导入模板.xlsx"></baseUpload>
      </el-card>
      <el-card class="upload_card" shadow="never">
        <baseUpload url="/api/ima/alterImaPurchaser" title="物料采购员批量维护" :centre="centre" href="/static/物料采购员导入模板.xlsx"></baseUpload>
      </el-card>
      <el-card class="upload_card" shadow="never">
        <baseUpload url="/api/faj/alterDepartment" title="固定资产部门批量维护" :centre="centre" href="/static/固定资产部门导入模板.xlsx"></baseUpload>
      </el-card>
    </el-alert>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import baseUpload from "@/components/other/base-upload.vue";
export default {
  name: "import",
  data() {
    return {
      centre: 'WCTZ'
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
  components: {
    selectedCentre,
    baseUpload
  }
}
</script>

<style scoped lang="scss">
.upload_card{
  background-color: rgba(255,255,255,0);
  float: left;
  border:none;
}
</style>