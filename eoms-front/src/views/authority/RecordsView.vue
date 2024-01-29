<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="success" round icon="el-icon-download">
          <el-link :underline="false" style="color: white" href="/static/历年职位权限导入模板.xlsx">模板</el-link>
        </el-button>
        <el-button type="danger" icon="el-icon-delete" round @click="deleteRecords" :loading="deleteLoading">删除</el-button>
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
            :on-change="handleOnChange"
            accept=".xlsx,.xls"
            action="/api/authorityReview/importRecords"
            multiple>
          <el-button type="success" round icon="el-icon-upload2" :loading="uploadLoading">导入</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <el-alert title="历年职位权限记录" type="success" :closable="false">
    </el-alert>
    <el-table @selection-change="handleSelectionChange" :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="year" label="年度" width="80"></el-table-column>
      <el-table-column prop="positionName" label="职位" width="120"></el-table-column>
      <el-table-column prop="permissionCode" label="作业代码" width="90"></el-table-column>
      <el-table-column prop="permissionName" label="作业名称" width="120"></el-table-column>
      <el-table-column prop="isAmount" label="金额" width="90"></el-table-column>
      <el-table-column prop="isCreate" label="新增" width="90"></el-table-column>
      <el-table-column prop="isDelete" label="删除" width="90"></el-table-column>
      <el-table-column prop="isUpdate" label="修改" width="90"></el-table-column>
      <el-table-column prop="isRead" label="查询" width="90"></el-table-column>
      <el-table-column prop="isConfirm" label="审核" width="90"></el-table-column>
      <el-table-column prop="isUnConfirm" label="撤销审核" width="90"></el-table-column>
      <el-table-column prop="isVoid" label="作废" width="90"></el-table-column>
      <el-table-column prop="isUnVoid" label="撤销作废" width="90"></el-table-column>
      <el-table-column prop="isPost" label="过账" width="90"></el-table-column>
      <el-table-column prop="isUnPost" label="撤销过账" width="90"></el-table-column>
      <el-table-column prop="isPrint" label="打印" width="90"></el-table-column>
      <el-table-column prop="isExport" label="数据导出" width="90"></el-table-column>
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
      tableLoading: false,
      uploadLoading: false,
      deleteLoading: false
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
        this.deleteLoading = true;
        this.$http.post('/api/authorityReview/deleteRecords', this.selectedList).then(res => {
          this.search(1);
        }).finally(() => this.deleteLoading = false);
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
    handleOnChange(file, fileList){
      this.uploadLoading= true
      if(file.percentage === 100){
        this.uploadLoading= false
      }
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
