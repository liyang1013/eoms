<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pmc"></selectedCentre>
      </el-form-item>
      <el-form-item label="生效状态:">
        <selected-acti v-model="searchVo.acti" key="pmc"></selected-acti>
      </el-form-item>
      <el-form-item label="供应商:">
        <el-input v-model="searchVo.pmc" placeholder="供应商编码/名称" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="供应商分类档案: apmi106; 供应商档案: apmi600; 采购价格条件: apmi110" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pmc01" label="供应商编码">
      </el-table-column>
      <el-table-column prop="pmc03" label="供应商名称">
      </el-table-column>
      <el-table-column prop="pmy02" label="供应商分类">
      </el-table-column>
      <el-table-column prop="pmc22" label="币种">
      </el-table-column>
      <el-table-column prop="pma02" label="付款方式">
      </el-table-column>
      <el-table-column prop="pnz02" label="价格条件">
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
  name: "pmc",
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        pmc: null,
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
    search(val) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/pmc/searchPmcListPageHelper', this.searchVo).then(res => {
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