<template>
  <el-tabs type="border-card">
    <el-tab-pane label="WCS任务类型分析">
      <el-select v-model="type1" @change="seriesData">
        <el-option value="taskNum" label="任务数量"></el-option>
        <el-option value="taskTime" label="总任务时间"></el-option>
        <el-option value="avgTime" label="平均任务时间"></el-option>
      </el-select>
      <ve-pie :data="seriesData"></ve-pie>

    </el-tab-pane>
    <el-tab-pane label="2">2</el-tab-pane>
    <el-tab-pane label="3">3</el-tab-pane>
    <el-tab-pane label="4">4</el-tab-pane>
  </el-tabs>

</template>

<script>
import NightingaleChart from "@/components/echarts/Nightingale.vue";
export default {
  name: "TaskAnalysis",
  components: {NightingaleChart},
  data() {
    return {
      type1: 'taskNum',
      data1: []
    }
  },
  methods: {
    seriesData(){
      let arr = [];
      this.$http.post('/api/echarts/wcsTaskTypeTime?type='+this.type1).then(res => {
        arr = res.data.result
      });
      return arr;
    }
  }
}
</script>
