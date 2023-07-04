<!-- 通用审核码下拉框组件 -->
<template>
  <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择生效状态" :disabled="disabled">
    <el-option v-for="item in confs" :key="item.value" :label="item.name" :value="item.value">
      <span style="float: left">{{ item.name }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
    </el-option>
  </el-select>
</template>

<script>

export default {
  name: 'selectedActi',
  data() {
    return {
      tempval: this.acti,
      confs: [
        {name: '生效', value: 'Y'},
        {name: '未生效', value: 'N'},
        {name: '全部', value: null},
      ]
    }
  },
  props: {
    acti: {
      type: String
    },
    disabled:{
      type: Boolean,
      default: false
    }
  },
  model: {
    prop: 'acti',
    event: 'change'
  }
}
</script>