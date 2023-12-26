<template>
  <div>
    <el-form :inline="true" :model="zx" class="demo-form-inline" >
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" ></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="searchVo.code" placeholder="员工姓名\编码" clearable></el-input>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="用户权限组设置: p_zxw; 权限组类型: p_zw; 权限组运行作业设置: p_zy" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="zxw01" label="用户编号">
      </el-table-column>
      <el-table-column prop="zx02" label="用户名">
      </el-table-column>
      <el-table-column prop="gem01" label="部门编号">
      </el-table-column>
      <el-table-column prop="gem02" label="部门">
      </el-table-column>
      <el-table-column prop="zxw04" label="权限编号">
      </el-table-column>
      <el-table-column prop="zw02" label="权限">
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
  name: "zxw",
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
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/zxw/searchZxwListPageHelper', this.searchVo).then(res => {
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
  },
  components:{
    selectedCentre
  }
}
</script>
