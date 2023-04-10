<template>
  <div>
    <el-form :inline="true" :model="operation" class="demo-form-inline" size="mini">
      <el-form-item label="编码:">
        <el-input v-model="operation.code" placeholder="作业编码"></el-input>
      </el-form-item>
      <el-form-item label="名称:">
        <el-input v-model="operation.name" placeholder="作业名称"></el-input>
      </el-form-item>
      <el-form-item label="详情:">
        <el-input v-model="operation.tips" placeholder="简介"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="operations" border style="width: 100%" max-height="500px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="code" label="作业编码">
      </el-table-column>
      <el-table-column prop="name" label="作业名称">
      </el-table-column>
      <el-table-column prop="tips" label="详情">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click.native.prevent="deleteRow(scope.$index, operations)" type="text">移除</el-button>
          <el-button type="text">新增</el-button>
          <el-button type="text">保存</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "operation",
  data() {
    return {
      operation: {
        code: null,
        name: null,
        tips: null
      },
      operations: [],
      table_loading: false
    }
  },
  methods: {
    search() {
      this.table_loading = true
      this.$http.post('/api/operation/searchOperation', this.operation).then(res => {
        this.operations = res.data.result
      }).finally(() => this.table_loading = false);
    },
    reset() {
      this.operation = {
        code: null,
        name: null,
        tips: null
      }
    },
    deleteRow(index, rows) {
      rows.splice(index, 1);
    }
  }
}
</script>

<style scoped>

</style>