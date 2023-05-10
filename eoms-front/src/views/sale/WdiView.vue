<template>
  <div>
    <el-form :inline="true" :model="wdi" class="demo-form-inline">
      <el-form-item label="中心:">
        <selectedCentre v-model="wdi.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="出库单号:">
        <el-input v-model="wdi.code_1" placeholder="出库单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="wdiList" border style="width: 100%" max-height="500px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="cdanhao" label="出库单号" width="160"></el-table-column>
      <el-table-column prop="tcWdi20" label="制单日期" width="120"></el-table-column>
      <el-table-column prop="tcWdi21" label="制单人" width="120"></el-table-column>
      <el-table-column prop="tcWdi53" label="拣货方式" width="120"></el-table-column>
      <el-table-column prop="tcWdi200" label="状态" width="120"></el-table-column>
      <el-table-column prop="tcWdi54" label="拣货状态" width="120"></el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchWdjList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="wdi.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="wdi.sizes"
                   :current-page.sync="wdi.currentPage" :page-size="wdi.size">
    </el-pagination>

    <!-- 收货单明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1000px">
      <el-table :data="wdjList" border max-height="300px">
        <el-table-column prop="rvb02" label="项次" width="60"></el-table-column>
        <el-table-column prop="rvb04" label="出库单号" width="120"></el-table-column>
        <el-table-column prop="rvb05" label="制单日期" width="120"></el-table-column>
        <el-table-column prop="rvb051" label="制单人" width="200"></el-table-column>
        <el-table-column prop="ima021" label="客户编码" width="240"></el-table-column>
        <el-table-column prop="rvb07" label="客户简称" width="160"></el-table-column>
        <el-table-column prop="rvb30" label="仓库" width="90"></el-table-column>
        <el-table-column prop="rvb90" label="任务数" width="90"></el-table-column>
        <el-table-column prop="rvb10t" label="出库数" width="120"></el-table-column>
        <el-table-column prop="rvb88t" label="待出库数量" width="120"></el-table-column>
        <el-table-column prop="rvb10t" label="状态" width="120"></el-table-column>
        <el-table-column prop="rvb88t" label="拣货状态" width="120"></el-table-column>
      </el-table>
      <el-divider></el-divider>
      <div style="text-align: right;">
        <el-button type="primary" round @click="alterRvb">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';

export default {
  name: 'rva',
  data() {
    return {
      wdi: {
        code_1: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      wdiList: [],
      wdjList: [],
      table_loading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val) {
      this.wdi.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/wdi/searchWdiListPageHelper', this.wdi).then(res => {
        this.wdiList = res.data.result
        this.wdi.total = res.data.total;
      }).finally(() => this.table_loading = false);

    },
    searchWdjList(row) {
      this.dialogTableVisible = true;
      this.rvab.rva = {...row};
      this.$http.post('/api/wdi/searchWdjList', {code_1: row.rva01, centre: row.rvaplant}).then(res => {
        this.rvab.rvbList = res.data.result;
      })
    },
    resetrva() {
      this.$http.post('/api/rva/searchRvbList', {
        code_1: this.rvab.rva.rva01,
        centre: this.rvab.rva.rvaplant
      }).then(res => {
        this.rvab.rvbList = res.data.result
      })
    },
    alterRvb() {
      this.$http.post('/api/wdi/alterRvb', this.wdjList)
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.wdi.size = val;
      this.search(1);
    }
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