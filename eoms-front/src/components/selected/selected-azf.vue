<!-- 通用码别下拉框组件 -->
<template>
  <el-select filterable remote :remote-method="remoteMethod" v-model="tempval" @change="$emit('change', $event)" placeholder="选择码别" clearable>
    <el-option v-for="item in azf" :key="item.azf01" :label="item.azf03" :value="item.azf01">
      <span style="float: left">{{ item.azf03 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.azf01 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedAzf',
  data() {
    return {
      tempval: this.azf01,
      azf: []
    }
  },
  props: {
    azf01: {
      type: String
    },
    centre: {
      type: String,
      default: 'WCTZ'
    },
    azf02: {
      type: String
    },
    azf09: {
      type: String
    }
  },
  model: {
    prop: 'azf01',
    event: 'change'
  },
  methods: {
    remoteMethod(str){
      if(str){
        this.$http.post('api/azf/searchAzfList',{centre: this.centre,code_1: str,code_2: 'Y',code_3: this.azf02,code_4: this.azf09}).then(res => this.azf = res.data.result)
      }
    }
  },
  created() {
    if(this.azf01){
      this.$http.post('api/azf/searchAzfByKey',{centre: this.centre,code_1: this.azf01,code_2: 'Y',code_3: this.azf02,code_4: this.azf09}).then(res => this.azf.push(res.data.result))
    }
  }
}
</script>