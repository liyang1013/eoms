<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="success" icon="el-icon-printer" @click="toExcel" round>导出</el-button>
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="stockgap"></selectedCentre>
      </el-form-item>
      <el-form-item label="仓库:">
        <selectedImd v-model="searchVo.imd" :centre="searchVo.centre" key="stockgap"></selectedImd>
      </el-form-item>
      <el-form-item label="料件:">
        <selectedIma v-model="searchVo.ima" :centre="searchVo.centre" key="stockgap"></selectedIma>
      </el-form-item>
    </el-form>
    <el-table :data="documentList" border max-height="500px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" :row-style="tableRowClassName"
              show-summary :summary-method="getSummaries" ref="tableToExcel">
      <el-table-column prop="cdanhao" label="单号">
      </el-table-column>
      <el-table-column prop="cproduceno" label="品号">
      </el-table-column>
      <el-table-column prop="cproducename" label="品名">
      </el-table-column>
      <el-table-column prop="cwhcode" label="仓库编码">
      </el-table-column>
      <el-table-column prop="imd02" label="仓库">
      </el-table-column>
      <el-table-column prop="iqty" label="单据数量">
      </el-table-column>
      <el-table-column prop="qty" label="异动数量">
      </el-table-column>
      <el-table-column prop="gapqty" label="差异">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue'
import selectedImd from "@/components/selected/selected-imd.vue";
import selectedIma from "@/components/selected/selected-ima.vue";
import { useExcel } from "@/script/excel";

export default {
  name: 'EWStockGap',
  data() {
    return {
      searchVo: {
        code: null,
        ima: null,
        imd: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      documentList: [],
      tableLoading: false
    }
  },
  components: {
    selectedCentre,
    selectedImd,
    selectedIma
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/tlf/searchEWStockGapPageHelper', this.searchVo)
          .then(res => {this.documentList = res.data.result; this.searchVo.total = res.data.total;}).finally(() => this.tableLoading = false)
    },
    tableRowClassName({row, rowIndex}) {
      if(row.gapqty !== 0) return {"background": "#f56c6c82 !important"}
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        if(index === 5 || index === 6 || index === 7) {
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
          } else {
            sums[index] = 'N/A';
          }
        }
      });
      return sums;
    },
    toExcel(){
      const table = this.$refs["tableToExcel"].$el;
      useExcel(table,'EW库存差异');
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.searchVo.size = val;
      this.search(1);
    },
  }
}

</script>
<style scoped>
::v-deep .el-table tbody tr:hover>td {
  background-color: red !important;
  color: #FFFFFF;
}
</style>
