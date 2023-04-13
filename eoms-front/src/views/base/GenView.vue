<template>
  <div>
    <el-form :inline="true" :model="gen" class="demo-form-inline" size="mini">
      <el-form-item label="中心:">
        <selectedCentre v-model="gen.centre" ></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="gen.code_1" placeholder="员工姓名\编码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="genList" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="gen07" label="中心">
      </el-table-column>
      <el-table-column prop="gen03" label="部门">
      </el-table-column>
      <el-table-column prop="gen01" label="工号">
      </el-table-column>
      <el-table-column prop="gen02" label="姓名">
      </el-table-column>
      <el-table-column prop="gen06" label="邮箱">
      </el-table-column>
      <el-table-column prop="gen05" label="电话号码">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="gen.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="gen.sizes"
                   :current-page.sync="gen.currentPage" :page-size="gen.size">
    </el-pagination>
  </div>
</template>

<script>

import {mapState} from 'vuex';
import selectedCentre from '@/components/selected-centre.vue';

export default ({
  name: 'contact',
  data() {
    return {
      gen: {
        centre: 'WCTZ',
        code_1: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      genList: [],
      table_loading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val) {
      this.gen.currentPage = val;
      this.table_loading = true
      this.$http.post('/api/gen/searchGenList', this.gen).then(res => {
        this.genList = res.data.result
        this.gen.total = res.data.total
      }).finally(() => this.table_loading = false);
    },
    reset() {
      this.gen = {
        centre: 'WCTZ',
        code_1: null
      }
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.gen.size = val;
      this.search(1);
    }
  },
})
</script>

<style scoped></style>