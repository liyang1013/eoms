<template>
  <div>
    <el-form :inline="true" :model="operation" class="demo-form-inline">
      <el-form-item label="编码:">
        <el-input v-model="operation.code_1" placeholder="作业编码"></el-input>
      </el-form-item>
      <el-form-item label="名称:">
        <el-input v-model="operation.code_2" placeholder="作业名称"></el-input>
      </el-form-item>
      <el-form-item label="详情:">
        <el-input v-model="operation.code_3" placeholder="简介"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
        <el-button type="primary" icon="el-icon-plus" round @click="dialogTableVisible = true">新增</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="作业录入: p_zz; 自定义报表: p_query" type="success" :closable="false">
    </el-alert>
    <el-table :data="operations" border style="width: 100%" max-height="450px" v-loading="table_loading"
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
          <el-button @click.native.prevent="deleteOperation(scope.row)" type="text">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="operation.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="operation.sizes"
                   :current-page.sync="operation.currentPage" :page-size="operation.size">
    </el-pagination>

    <!-- 新增作业 -->
    <el-dialog :visible.sync="dialogTableVisible" width="600px">
      <el-table :data="tempOperation" border max-height="300px">
        <el-table-column prop="code" label="项次" width="160"></el-table-column>
        <el-table-column prop="name" label="采购订单号" width="200"></el-table-column>
        <el-table-column prop="tips" label="料件编号" width="200"></el-table-column>
      </el-table>
      <el-divider></el-divider>
      <div style="text-align: right;">
        <el-button type="primary" round >保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "operation",
  data() {
    return {
      operation: {
        code_1: null,
        code_2: null,
        code_3: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      operations: [],
      table_loading: false,
      dialogTableVisible: false,
      tempOperation: [
          {code: null, name: null, tips: null}
      ]
    }
  },
  methods: {
    search(val) {
      this.operation.currentPage = val;
      this.table_loading = true
      this.$http.post('/api/operation/searchOperationList', this.operation).then(res => {
        this.operations = res.data.result;
        this.operation.total = res.data.total
      }).finally(() => this.table_loading = false);
    },
    reset() {
      this.operation.code_1 = null;
      this.operation.code_2 = null;
      this.operation.code_3 = null
    },
    deleteOperation(row){
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/api/operation/deletOperation',row).then(() =>{this.$message({
          type: 'success',
          message: '删除成功!'
        });this.search(1);
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.operation.size = val;
      this.search(1);
    }
  }
}
</script>

<style scoped>
</style>