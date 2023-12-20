<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="success" round icon="el-icon-download">
          <el-link :underline="false" style="color: white" href="/static/历年职位权限导入模板.xlsx">模板</el-link>
        </el-button>
        <el-button type="danger" icon="el-icon-delete" round @click="deleteRecords">删除</el-button>
        <el-button type="primary" @click="search()" icon="el-icon-search" round :loading="tableLoading">查询</el-button>
      </el-form-item>

      <el-form-item label="年度:">
        <el-date-picker v-model="searchVo.year" type="year" value-format="yyyy" placeholder="选择年"></el-date-picker>
      </el-form-item>
      <el-form-item label="职位:">
        <el-autocomplete style="width: 400px" clearable v-model="searchVo.code" :fetch-suggestions="searchZwList" placeholder="职位名称/代码" @select="handleSelect">
          <template slot-scope="{ item }">
            <span style="float: left">{{ item.zw01 }}</span>
            <span style="float: right; color: #5d87cb; font-size: 13px">{{ item.zw02 }}</span>
          </template>
        </el-autocomplete>
      </el-form-item>
      <el-form-item style="float: right">
        <el-upload
            class="upload-demo"
            :show-file-list="false"
            :data="{'year': searchVo.year}"
            :on-success="upload_success"
            :on-error="upload_err"
            accept=".xlsx,.xls"
            action="/api/authorityReview/importRecords"
            multiple>
          <el-button type="success" round icon="el-icon-upload2">上传</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <el-alert title="历年职位权限记录" type="success" :closable="false">
    </el-alert>
    <el-table @selection-change="handleSelectionChange" :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" >
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="year" label="年度"></el-table-column>
      <el-table-column prop="positionname" label="职位"></el-table-column>
      <el-table-column prop="permissioncode" label="作业代码"></el-table-column>
      <el-table-column prop="permissionname" label="作业名称"></el-table-column>
      <el-table-column prop="containAmount" label="是否有金额"></el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>
<script>
export default {
  name: "records",
  data() {
    return {
      searchVo: {
        year: new Date().getFullYear().toString(),
        code: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      documentList: [],
      selectedList: [],
      tableLoading: false
    }
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/authorityReview/searchRecordsListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total
      }).finally(() => this.tableLoading = false);
    },
    deleteRecords() {
      if(! this.selectedList.length) { this.$message({type: 'info', message: '先选删除记录'});return}
      this.$confirm('是否删除选中记录？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(() => {
        this.$http.post('/api/authorityReview/deleteRecords', this.selectedList).then(res => {
          this.search(1);
        });
      }).catch(action => {
        this.$message({type: 'info', message: '取消'})
      });
    },
    searchZwList(str,cb){
      if(!str) return;
      this.$http.get('/api/zw/searchZwList?query=' + str).then(res => {
        cb( res.data.result);
      });
    },
    upload_success(data) {
      this.$message.success(data.message)
    },
    upload_err(data) {
      this.$message.warning(data.message)
    },
    handleSelect(val) {
      this.searchVo.code = val.zw02;
    },
    handleSelectionChange(selected){
      this.selectedList = selected;
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.searchVo.size = val;
      this.search(1);
    }
  }
}
</script>
