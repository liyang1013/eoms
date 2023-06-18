<template>
  <v-chart ref="myCharts" :option="option" autoresize watch-shallow ></v-chart>
</template>

<script>
export default {
  name: "NightingaleChart",
  props:{
    data1: {
      type: Array,
      default: []
    }
  },
  model: {
    prop: 'data1',
    event: 'change'
  },
  data() {
    return {
      wcsTaskTypeTime:[],
      option: {
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)',
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series:
          {
            name: 'WCS任务',
            type: 'pie',
            radius: [50, 250],
            center: ['50%', '50%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 5
            },
            data: this.data1
          }
      }
    }
  }
}
</script>
<style scoped>

</style>