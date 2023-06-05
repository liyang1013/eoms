<!-- 通用项目下拉框组件 -->
<template>
  <el-select filterable remote :remote-method="remoteMethod" v-model="tempval" @change="$emit('change', $event)" placeholder="选择项目" clearable >
    <el-option v-for="item in pjb" :key="item.pjb02" :label="item.pjb03" :value="item.pjb02">
      <span style="float: left">{{ item.pjb03 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.pjb02 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedPjb',
  data() {
    return {
      tempval: this.pjb02,
      pjb: []
    }
  },
  props: {
    pjb01: {
      type: String
    },
    pjb02: {
      type: String
    },
    centre: {
      type: String,
      default: 'WCTZ'
    }
  },
  model: {
    prop: 'pjb02',
    event: 'change'
  },
  methods: {
    remoteMethod(str){
      if(  this.pjb01 && str){
        this.$http.post('api/pjb/searchPjbList',{centre: this.centre,code_1: this.pjb01,code_2: str,code_3: 'Y'}).then(res => this.pjb = res.data.result)
      }
    }
  },
  created() {
    if( this.pjb01 && this.pjb02 ){
      this.$http.post('api/pjb/searchPjbByKey',{centre: this.centre,code_1: this.pjb01,code_2: this.pjb02,code_3: 'Y'}).then(res => this.pjb.push(res.data.result))
    }
  }
}
</script>