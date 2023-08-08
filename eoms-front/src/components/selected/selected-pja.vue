<!-- 通用项目大类下拉框组件 -->
<template>
  <el-select filterable remote :remote-method="remoteMethod" v-model="tempval" @change="$emit('change', $event)" placeholder="选择项目" clearable >
    <el-option v-for="item in pja" :key="item.pja01" :label="item.pja02" :value="item.pja01">
      <span style="float: left">{{ item.pja02 }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.pja01 }}</span>
    </el-option>
  </el-select>
</template>
<script>

export default {
  name: 'selectedPja',
  data() {
    return {
      tempval: this.pja01,
      pja: []
    }
  },
  props: {
    pja01: {
      type: String
    },
    centre: {
      type: String,
      default: 'WCTZ'
    }
  },
  model: {
    prop: 'pja01',
    event: 'change'
  },
  methods: {
    remoteMethod(str){
      if(str){
        this.$http.post('/api/pja/searchPjaList',{centre: this.centre,pja: str,acti: 'Y',conf: 'Y'}).then(res => this.pja = res.data.result)
      }
    }
  },
  created() {
    if(this.pja01){
      this.$http.post('/api/pja/searchPjaByKey',{centre: this.centre,pja: this.pja01,acti: 'Y',conf: 'Y'}).then(res => this.pja.push(res.data.result))
    }
  }
}
</script>