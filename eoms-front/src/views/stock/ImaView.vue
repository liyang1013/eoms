<template>
  <div>
    <el-form :inline="true" :model="ima" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="ima.centre" key="ima"></selectedCentre>
      </el-form-item>
      <el-form-item label="料件:">
        <selectedIma v-model="ima.ima" :centre="ima.centre" ></selectedIma>
      </el-form-item>
    </el-form>
    <el-alert title="料件基本资料: aimi100; " type="success" :closable="false">
    </el-alert>
    <el-table :data="imaList" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="ima01" label="料件编码" width="160">
      </el-table-column>
      <el-table-column prop="ima02" label="品名" width="160">
      </el-table-column>
      <el-table-column prop="ima021" label="规格" width="240">
      </el-table-column>
      <el-table-column prop="imz02" label="料件大类" width="120">
      </el-table-column>
      <el-table-column prop="ima08" label="料件类型" width="120">
        <template slot-scope="scope">
          {{ scope.row.ima08 | formatImaType }}
        </template>
      </el-table-column>
      <el-table-column prop="ima1010" label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.ima1010 | formatSignoff }}
        </template>
      </el-table-column>

      <el-table-column prop="ima25" label="单位" width="160">
        <template slot-scope="scope">
          <selected-gfe v-model="scope.row.ima25" :centre="scope.row.centre" :key="scope.row.ima01" @change="alterGfe($event,scope.row)"></selected-gfe>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="90">
        <template>
          <el-button type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="ima.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="ima.sizes"
                   :current-page.sync="ima.currentPage" :page-size="ima.size">
    </el-pagination>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre';
import selectedIma from "@/components/selected/selected-ima";
import selectedGfe from "@/components/selected/selected-gfe";

export default {
  name: 'ima',
  data() {
    return {
      ima: {
        ima: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      imaList: [],
      table_loading: false,
    }
  },
  components: {
    selectedCentre,
    selectedIma,
    selectedGfe
  },
  methods: {
    search(val = 1) {
      this.ima.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/ima/searchImaListPageHelper', this.ima).then(res => {
        this.imaList = res.data.result
        this.ima.total = res.data.total;
      }).finally(() => this.table_loading = false);

    },
    alterGfe(gfe01,row){
      this.$http.post('/api/ima/alterGfe',{ima01: row.ima01,ima25:gfe01,centre: row.centre})
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.ima.size = val;
      this.search(1);
    }
  }
}
</script>

<style lang="scss" scoped>
</style>