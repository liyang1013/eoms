<template>
  <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择仓库" @visible-change="searchImdList" filterable remote :remote-method="remoteMethod" clearable>
    <el-option v-for="item in imdList" :key="item.imd01" :label="item.imd02" :value="item.imd01">
      <span style="float: left">{{ item.imd02 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.imd01 }}</span>
    </el-option>
  </el-select>
</template>

<script>
export default {
  name: "selected-imd",
  data() {
    return {
      tempval: this.imd01,
      imdList: []
    }
  },
  props: {
    centre: {
      type: String,
      default: 'WCTZ'
    },
    imd01: {
      type: String
    }
  },
  model: {
    prop: 'imd01',
    event: 'change'
  },
  methods:{
    searchImdList(val){
      if(val){
        this.$http.post('/api/imd/searchImdList',{centre: this.centre}).then(res => this.imdList = res.data.result)
      }
    },
    remoteMethod(query){
      this.$http.post('/api/imd/searchImdList',{centre: this.centre,code_1: query}).then(res => this.imdList = res.data.result)
    },
  }
}
</script>

<style scoped>

</style>