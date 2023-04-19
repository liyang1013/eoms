<template>
  <div>
    <selected-centre v-model="centre" style="margin-bottom: 10px"></selected-centre>
    <el-alert type="success" class="tip_alert" :closable="false">
     <el-card style="background-color: rgba(255,255,255,0);float: left;border:none" shadow="never">
       <baseUpload url="/api/ima/alterStockParameter" title="料件库存参数信息批量修改" :centre="centre"></baseUpload>
     </el-card>
    </el-alert>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import baseUpload from "@/components/base-upload";
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

<style scoped>

</style>