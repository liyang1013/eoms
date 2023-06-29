<template>
  <el-tabs type="border-card" v-model="activeName">
    <el-tab-pane label="中心" name="1">
      <el-col :span="24">
        <el-button round type="success" style="float: right">刷新</el-button>
      </el-col>
      <el-col :span="12">
        <el-table :data="chart1.chartData.rows" border stripe  max-height="450px"
                  element-loading-spinner="el-icon-loading">
          <el-table-column type="index" label="序号" width="60">
          </el-table-column>
          <el-table-column prop="customerid" label="货主" width="120">
          </el-table-column>
          <el-table-column prop="locationid" label="库位" width="120">
          </el-table-column>
          <el-table-column prop="traceid" label="栈板码" width="120">
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="12">
        <ve-histogram  :data="chart1.chartData" ref="chart1" :extend="chart1.extend"
                      :settings="chart1.chartSettings"></ve-histogram>
      </el-col>

    </el-tab-pane>
    <el-tab-pane label="配置管理" name="2">
      <ve-pie :data="chart2.chartData" ref="chart2"></ve-pie>
    </el-tab-pane>
    <el-tab-pane label="角色管理" name="3">
      <ve-line :data="chart3.chartData" ref="chart3"></ve-line>
    </el-tab-pane>
    <el-tab-pane label="定时任务" name="4">
      <ve-line :data="chart4.chartData" ref="chart4"></ve-line>
    </el-tab-pane>
  </el-tabs>
</template>

<script>

export default {
  name: "TaskAnalysis",
  data() {
    return {
      activeName: '1',
      chart1: {
        extend: {
          series: {
            label: {show: true, position: "top"}
          }
        },
        chartSettings: {
          axisSite: {right: ['平均耗时']},
          yAxisType: ['MB', 'MB'],
          yAxisName: ['次数', '时间']
        },
        chartData: {
          columns: ['中心', '次数', '总时间', '平均耗时'],
          rows: [
            {'中心': '机电', '次数': 47799, '总时间': 20303, '平均耗时': 1528},
            {'中心': '电子', '次数': 40968, '总时间': 14189, '平均耗时': 1246},
            {'中心': '科奥', '次数': 13374, '总时间': 5476, '平均耗时': 1474},
            {'中心': '温州', '次数': 6187, '总时间': 2050, '平均耗时': 1193},
            {'中心': '上海', '次数': 4668, '总时间': 1702, '平均耗时': 1313}
          ]
        }
      },
      chart2: {},
      chart3: {},
      chart4: {}
    }
  },
  watch: {
    activeName(v) {
      this.$nextTick(_ => {
        this.$refs[`chart${v}`].echarts.resize()
      })
    }
  }
}
</script>

