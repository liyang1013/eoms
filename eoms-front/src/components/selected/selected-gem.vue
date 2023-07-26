<!-- 通用部门下拉框组件 -->
<template>
  <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择部门" clearable filterable remote :remote-method="remoteMethod">
    <el-option v-for="item in gem" :key="item.gem01" :label="item.gem02" :value="item.gem01">
      <span style="float: left">{{ item.gem02 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.gem01 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedGem',
  data() {
    return {
      tempval: this.gem01,
      gem: []
    }
  },
  props: {
    gem01: {
      type: String
    },
    centre: {
      type: String,
      default: 'WCTZ'
    },
  },
  model: {
    prop: 'gem01',
    event: 'change'
  },
  methods: {
    remoteMethod(str){
      if(str){
        this.$http.post('/api/gem/searchGemList',{centre: this.centre,code_1: str,code_2: 'Y'}).then(res => this.gem = res.data.result)
      }
    }
  },
  beforeMount() {
    if(this.gem01){
      this.$http.post('/api/gem/searchGemByKey',{centre: this.centre,code_1: this.gem01,code_2: 'Y'}).then(res => this.gem.push(res.data.result))
    }
  }
}
</script>