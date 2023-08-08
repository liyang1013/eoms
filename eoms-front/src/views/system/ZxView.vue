<template>
  <div>
    <el-form :inline="true" :model="zx" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="zx.centre" ></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="zx.zx" placeholder="员工姓名\编码" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="用户基本资料: p_zx" type="success" :closable="false">
    </el-alert>
    <el-table :data="zxList" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="zx08" label="最近中心">
      </el-table-column>
      <el-table-column prop="zx04" label="权限">
      </el-table-column>
      <el-table-column prop="zx01" label="工号">
      </el-table-column>
      <el-table-column prop="zx02" label="姓名">
      </el-table-column>
      <el-table-column prop="zx09" label="邮箱">
      </el-table-column>
      <el-table-column prop="zx18" label="可用日期">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="zx.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="zx.sizes"
                   :current-page.sync="zx.currentPage" :page-size="zx.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue';

export default {
  name: "zx",
  data() {
    return {
      zx: {
        centre: null,
        zx: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      zxList: [],
      table_loading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val) {
      this.zx.currentPage = val;
      this.table_loading = true
      this.$http.post('/api/zx/searchZxListPageHelper', this.zx).then(res => {
        this.zxList = res.data.result
        this.zx.total = res.data.total
      }).finally(() => this.table_loading = false);
    },
    handleCurrentChange(val){
      this.search(val);
    },
    handleSizeChange(val){
      this.zx.size = val;
      this.search(1);
    }
  }
}
</script>

<style scoped>
</style>