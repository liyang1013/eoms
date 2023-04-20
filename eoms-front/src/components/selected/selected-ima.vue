<template>
  <el-autocomplete v-model="tempval" @change="changeIma" @select="selectIma" :fetch-suggestions="querySearchImaAsync" placeholder="输入料件编码/名称" clearable>
    <template slot-scope="{ item }">
      <p style="float: left; font-size: 10px">{{item.ima02}}</p>
      <p style="float: right; font-size: 10px;color: #8492a6; ">{{item.ima01}}</p>
    </template>
  </el-autocomplete>
</template>

<script>
export default {
  name: "selected-ima",
  data() {
    return {
      tempval: this.ima01
    }
  },
  props: {
    centre: {
      type: String,
      default: 'WCTZ'
    },
    ima01: {
      type: String
    }
  },
  model: {
    prop: 'ima01',
    event: 'change'
  },
  methods: {
    querySearchImaAsync(queryString, cb){
      this.$http.post('/api/ima/searchImaList', {
        centre: this.centre,
        code_1: queryString
      }).then(res => cb(res.data.result))
    },
    selectIma(obj){
      this.tempval = obj.ima01
      this.$emit('change',obj.ima01 )
    },
    changeIma(val){
      this.tempval = val
      this.$emit('change',val)
    }
  }
}
</script>

<style>

</style>