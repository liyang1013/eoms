<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" ></selectedCentre>
      </el-form-item>
      <el-form-item label="单别:">
        <el-input v-model="searchVo.code" placeholder="员工姓名\编码" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="单别维护作业: asmi300" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="smyslip" label="单别">
      </el-table-column>
      <el-table-column prop="smydesc" label="单据名称">
      </el-table-column>
      <el-table-column prop="smysys" label="系统别">
      </el-table-column>
      <el-table-column prop="smykind" label="单据性质">
      </el-table-column>
      <el-table-column prop="smydmy1" label="成本中心">
      </el-table-column>
      <el-table-column prop="taSmy211" label="先进先出">
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

export default {
  name: "smy",
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        code: null,
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
      this.$http.post('/api/smy/searchSmyListPageHelper', this.searchVo).then(res => {
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

<style scoped>
</style>