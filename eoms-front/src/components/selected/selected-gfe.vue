<!-- 通用项目大类下拉框组件 -->
<template>
  <el-select filterable remote :remote-method="remoteMethod" v-model="tempval" @change="$emit('change', $event)" placeholder="选择单位" clearable >
    <el-option v-for="item in gfe" :key="item.gfe01" :label="item.gfe01" :value="item.gfe01">
      <span style="float: left">{{ item.gfe01 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.gfe02 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedGfe',
  data() {
    return {
      tempval: this.gfe01,
      gfe: []
    }
  },
  props: {
    gfe01: {
      type: String
    },
    centre: {
      type: String,
      default: 'WCTZ'
    }
  },
  model: {
    prop: 'gfe01',
    event: 'change'
  },
  methods: {
    remoteMethod(str){
      if(str){
        this.$http.post('/api/gfe/searchGfeList',{centre: this.centre,code_1: str,code_2: 'Y'}).then(res => this.gfe = res.data.result)
      }
    }
  },
  created() {
    if(this.gfe01){
      this.$http.post('/api/gfe/searchGfeByKey',{centre: this.centre,code_1: this.gfe01,code_2: 'Y'}).then(res => this.gfe.push(res.data.result))
    }
  }
}
</script>