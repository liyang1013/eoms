<template>
  <div>
    <el-form :inline="true" :model="gaz" class="demo-form-inline">
      <el-form-item label="作业:">
        <el-input v-model="gaz.gaz" placeholder="作业编号/名称" clearable ></el-input>
      </el-form-item>
      <el-form-item label="目的:">
        <el-input v-model="gaz.gaz04" placeholder="作业目的" clearable ></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="primary"  icon="el-icon-search" @click="search(1)" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="作业录入: p_zz; 自定义报表: p_query" type="success" :closable="false">
    </el-alert>
    <el-table :data="gazList" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="gaz01" label="作业编码" width="120"></el-table-column>
      <el-table-column prop="gaz03" label="作业" width="200"></el-table-column>
      <el-table-column prop="gaz04" label="目的"></el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="gaz.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="gaz.sizes"
                   :current-page.sync="gaz.currentPage" :page-size="gaz.size">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "gaz",
  data() {
    return {
      gaz:{
        gaz: null,
        gaz04: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      gazList: [],
      table_loading: false
    }
  },
  methods: {
    search(val) {
      this.gaz.currentPage = val;
      this.table_loading = true
      this.gaz.currentPage = val;
      this.$http.post('/api/gaz/searchGazListPageHelper', this.gaz).then(res => {
        this.gazList = res.data.result;
        this.gaz.total = res.data.total
      }).finally(() => this.table_loading = false)
    },

    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.gaz.size = val;
      this.search(1);
    },

  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-dialog {
  .el-dialog__header {
    padding: 5px !important;
  }

  .el-dialog__body {
    padding: 15px 20px !important;
  }

  .el-divider {
    margin: 10px 0 !important;
  }
}
</style>