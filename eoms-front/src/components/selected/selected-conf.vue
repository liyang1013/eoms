<!-- 通用审核码下拉框组件 -->
<template>
  <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择审核状态" :disabled="disabled">
    <el-option v-for="item in confs" :key="item.value" :label="item.name" :value="item.value">
      <span style="float: left">{{ item.name }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
    </el-option>
  </el-select>
</template>

<script>

export default {
  name: 'selectedConf',
  data() {
    return {
      tempval: this.conf,
      confs: [
        {name: '未审核', value: 'N'},
        {name: '已审核', value: 'Y'},
        {name: '收/发料中', value: 'P'},
        {name: '收/发料完成', value: 'F'},
        {name: '作废', value: 'X'}
      ]
    }
  },
  props: {
    conf: {
      type: String
    },
    disabled:{
      type: Boolean,
      default: false
    }
  },
  model: {
    prop: 'conf',
    event: 'change'
  }
}
</script>

<style lang="scss" scoped></style>