<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" ></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="searchVo.zx" placeholder="员工姓名\编码" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="用户基本资料: p_zx; 用户权限设置作业: p_zxw; 权限档案: p_zw; 权限作业档案: p_zy" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="zx01" label="工号">
      </el-table-column>
      <el-table-column prop="zx02" label="姓名">
      </el-table-column>
      <el-table-column prop="gem02" label="部门">
      </el-table-column>
      <el-table-column prop="zx04" label="权限编号">
      </el-table-column>
      <el-table-column prop="zw02" label="权限">
      </el-table-column>
      <el-table-column prop="zxy03" label="中心权限">
      </el-table-column>
      <el-table-column prop="zx08" label="最近中心">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre';

export default {
  name: "zx",
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        zx: null,
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
    selectedCentre
  },
  methods: {
    search(val) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/zx/searchListPageHelper', this.searchVo).then(res => {
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
