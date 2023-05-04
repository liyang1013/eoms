<template>
  <div>
    <el-form :inline="true" :model="stock" class="demo-form-inline" size="mini">
      <el-form-item label="货主:">
        <selectedCentre v-model="stock.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="料件:" >
        <selectedIma v-model="stock.code_1" :centre="stock.centre"></selectedIma>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="stockList" border style="width: 100%" max-height="500px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="customerid" label="货主" width="120">
      </el-table-column>
      <el-table-column prop="locationid" label="库位" width="120">
      </el-table-column>
      <el-table-column prop="sku" label="料件编码" width="160">
      </el-table-column>
      <el-table-column prop="skudescr1" label="料件名称" width="220">
      </el-table-column>
      <el-table-column prop="skudescr2" label="规格" width="160">
      </el-table-column>
      <el-table-column prop="lotnum" label="批次" width="120">
      </el-table-column>
      <el-table-column prop="qty" label="数量" width="120">
      </el-table-column>
      <el-table-column prop="grossweight" label="毛重" width="120">
      </el-table-column>
      <el-table-column prop="traceid" label="跟踪号" width="120">
      </el-table-column>
      <el-table-column prop="muid" label="muid" width="120">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="stock.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="stock.sizes"
                   :current-page.sync="stock.currentPage" :page-size="stock.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedIma from "@/components/selected/selected-ima.vue";

export default {
  name: "invTransfer",
  data() {
    return {
      stock: {
        centre: null,
        code_1: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      stockList: [],
      table_loading: false
    }
  },
  methods: {
    search(val){
      this.stock.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/invLotLocId/invLotLocIdListPageHelper', this.stock).then(res => {
        this.stockList = res.data.result
        this.stock.total = res.data.total;
      }).finally(() => this.table_loading = false);
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.stock.size = val;
      this.search(1);
    }
  },
  components: {
    selectedCentre,
    selectedIma
  },
}
</script>

<style scoped lang="scss">

</style>