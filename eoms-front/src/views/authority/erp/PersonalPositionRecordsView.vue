<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="success" round icon="el-icon-download">
          <el-link :underline="false" style="color: white" href="/static/员工职位导入模板.xlsx">模板</el-link>
        </el-button>
        <el-button type="danger" icon="el-icon-delete" round @click="deleteRecords" :loading="deleteLoading">删除</el-button>
        <el-button type="primary" @click="search()" icon="el-icon-search" round :loading="tableLoading">查询</el-button>
      </el-form-item>
      <el-form-item label="年度:">
        <el-date-picker v-model="searchVo.year" type="year" value-format="yyyy" placeholder="选择年" style="width: 120px"></el-date-picker>
      </el-form-item>
      <el-form-item label="类型:">
        <el-select v-model="searchVo.itype" placeholder="请选择" style="width: 160px">
          <el-option label="权限类别" :value="1"></el-option>
          <el-option label="程序编号" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="员工:">
        <el-input clearable v-model="searchVo.zx"  placeholder="工号/姓名" style="width: 160px"></el-input>
      </el-form-item>
      <el-form-item :label="searchVo.itype === 1 ? '职位:':'作业:'">
        <el-input clearable v-model="searchVo.code"  :placeholder="searchVo.itype === 1 ? '职位代码/名称' : '作业代码/名称'" style="width: 160px"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-upload
            class="upload-demo"
            :show-file-list="false"
            :data="{'year': searchVo.year,'itype': searchVo.itype}"
            :on-success="upload_success"
            :on-error="upload_err"
            :on-change="handleOnChange"
            accept=".xlsx,.xls"
            action="/api/authorityReview/importPersonalPositionRecords"
            multiple>
          <el-button type="success" round icon="el-icon-upload2" :loading="uploadLoading">导入</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <el-alert title="历年员工权限记录" type="success" :closable="false">
    </el-alert>
    <el-table @selection-change="handleSelectionChange" :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="year" label="年度"></el-table-column>
      <el-table-column prop="code" label="工号" ></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="itype" label="类型">
        <template slot-scope="scope">
          {{scope.row.itype === 1 ? '权限类别' : '程序编号'}}
        </template>
      </el-table-column>
      <el-table-column prop="permissionCode" :label="searchVo.itype === 1 ? '职位代码' : '作业代码'" ></el-table-column>
      <el-table-column prop="permissionName"  :label="searchVo.itype === 1 ? '职位' : '作业'"></el-table-column>
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
  name: "PersonalPositionRecords",
  data() {
    return {
      searchVo: {
        year: new Date().getFullYear().toString(),
        code: null,
        zx: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0,
        itype: 1
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
      this.$http.post('/api/authorityReview/searchPersonalPositionRecordsListPageHelper', this.searchVo).then(res => {
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
        this.$http.post('/api/authorityReview/deletePersonalPositionRecords', this.selectedList).then(res => {
          this.search(1);
        }).finally(() => this.deleteLoading = false);
      }).catch(action => {
        this.$message({type: 'info', message: '取消'})
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
