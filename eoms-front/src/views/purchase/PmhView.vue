<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pmh"></selectedCentre>
      </el-form-item>
      <el-form-item label="生效状态:">
        <selected-acti v-model="searchVo.acti"></selected-acti>
      </el-form-item>
      <el-form-item label="料件:">
        <el-input v-model="searchVo.ima" placeholder="料件编码/名称" clearable></el-input>
      </el-form-item>
      <el-form-item label="供应商:">
        <el-input v-model="searchVo.pmc" placeholder="供应商编码/名称" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="料件/供应商: apmi254; 供应商/料件: apmi258; 委外料件/供应商: apmi264; 供应商/委外料件: apmi268" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pmh01" label="料件编码" width="160">
      </el-table-column>
      <el-table-column prop="ima02" label="料件名称" width="160">
      </el-table-column>
      <el-table-column prop="ima021" label="料件规格" width="160">
      </el-table-column>
      <el-table-column prop="pmh02" label="供应商编码" width="120">
      </el-table-column>
      <el-table-column prop="pmc03" label="供应商" width="160">
      </el-table-column>
      <el-table-column prop="pmh03" label="主要供应商" width="90">
      </el-table-column>
      <el-table-column prop="pmh05" label="核准状态" width="90">
        <template slot-scope="scope">
          {{scope.row.pmh05 | formatApproval}}
        </template>
      </el-table-column>
      <el-table-column prop="pmh06" label="核准日期" width="120">
      </el-table-column>
      <el-table-column prop="pmh13" label="币种" width="90">
      </el-table-column>
      <el-table-column prop="pmh14" label="汇率" width="90">
      </el-table-column>
      <el-table-column prop="pmh18" label="税率" width="90">
      </el-table-column>
      <el-table-column prop="pmh12" label="未税单价" width="120">
      </el-table-column>
      <el-table-column prop="pmh19" label="含税单价" width="120">
      </el-table-column>
      <el-table-column prop="ima44" label="采购单位" width="90">
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
import {formatApproval} from "@/filters/filters";

export default {
  name: "pmh",
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        pmc: null,
        ima: null,
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
      this.$http.post('/api/pmh/searchPmhListPageHelper', this.searchVo).then(res => {
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