<!-- 通用中心下拉框组件 -->
<template>
  <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择中心" clearable >
    <el-option v-for="item in centres" :key="item.azp01" :label="item.azp02" :value="item.azp01">
      <span style="float: left">{{ item.azp02 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.azp01 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedCentre',
  data() {
    return {
      tempval: this.centre,
      centres: []
    }
  },
  props: {
    centre: {
      type: String
    }
  },
  model: {
    prop: 'centre',
    event: 'change'
  },
  beforeMount() {
    this.$http.get('api/azp/getAllCentre').then(res => this.centres = res.data.result)
  }
}
</script>