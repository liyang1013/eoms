<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pma"></selectedCentre>
      </el-form-item>
      <el-form-item label="生效状态:">
        <selected-acti v-model="searchVo.acti" key="pma"></selected-acti>
      </el-form-item>
      <el-form-item label="付款方式:">
        <el-input v-model="searchVo.code" placeholder="付款方式" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="付款方式: apmi100; 多账期: apmi101" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pma01" label="付款条件">
      </el-table-column>
      <el-table-column prop="pma11" label="类型">
        <template slot-scope="scope">
          {{scope.row.pma03 | formatPma11}}
        </template>
      </el-table-column>
      <el-table-column prop="pma02" label="说明">
      </el-table-column>
      <el-table-column prop="pma03" label="付款日起算基准">
        <template slot-scope="scope">
          {{scope.row.pma03 | formatPma}}
        </template>
      </el-table-column>
      <el-table-column prop="pma09" label="加月数">
      </el-table-column>
      <el-table-column prop="pma08" label="加天数">
      </el-table-column>
      <el-table-column prop="pma12" label="票据日起算基准">
        <template slot-scope="scope">
          {{scope.row.pma12 | formatPma}}
        </template>
      </el-table-column>
      <el-table-column prop="pma13" label="加月数">
      </el-table-column>
      <el-table-column prop="pma10" label="加天数">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedActi from "@/components/selected/selected-acti.vue";

export default {
  name: "pma",
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        code: null,
        acti: 'Y',
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
    selectedActi
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/pma/searchPmaListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total
      }).finally(() => this.tableLoading = false);
    },
    handleCurrentChange(val){
      this.search(val);
    },
    handleSizeChange(val){
      this.searchVo.size = val;
      this.search(1);
    }
  }
}
</script>
