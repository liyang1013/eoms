<!-- 通用中心下拉框组件 -->
<template>
  <el-select  filterable remote :remote-method="remoteMethod"  v-model="tempval" @change="$emit('change', $event)" placeholder="选择中心" clearable >
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
  methods: {
    remoteMethod(str){
      this.$http.get('/api/azp/searchAzpList?azp01='+str).then(res => this.centres = res.data.result)
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
    if(this.centre){
      this.$http.get('/api/azp/searchAzpByKey?azp01='+this.centre).then(res => this.centres.push(res.data.result))
    }
  }
}
</script>