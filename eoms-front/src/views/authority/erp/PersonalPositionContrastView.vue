<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="success" round icon="el-icon-download" @click="download" :loading="downloadLoading">
          员工权限对比记录
        </el-button>
        <el-button type="primary" @click="search()" icon="el-icon-search" round :loading="tableLoading">查询</el-button>
      </el-form-item>
      <el-form-item label="对比年度:">
        <el-date-picker v-model="searchVo.year" type="year" value-format="yyyy" placeholder="选择年" style="width: 120px"></el-date-picker>
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="searchVo.itype" placeholder="请选择" style="width: 120px">
          <el-option label="权限类别" :value="1"></el-option>
          <el-option label="程序编号" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="员工:">
        <el-input clearable v-model="searchVo.zx" placeholder="工号/姓名" style="width: 160px"></el-input>
      </el-form-item>
    </el-form>

    <el-table :data="documentList" border style="width: 100%" max-height="500px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" :cell-class-name="delLine">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="code" label="工号"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="zx04" :label="searchVo.itype === 1 ? '当前职位代码' : '当前作业代码'"></el-table-column>
      <el-table-column prop="zw02" :label="searchVo.itype === 1 ? '当前职位' : '当前作业'"></el-table-column>
      <el-table-column prop="permissionCode" :label="searchVo.itype === 1 ? '历史职位代码' : '历史作业代码'"></el-table-column>
      <el-table-column prop="permissionName" :label="searchVo.itype === 1 ? '历史职位' : '历史作业'"></el-table-column>
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
  name: "PersonalPositionContrast",
  data() {
    return {
      searchVo: {
        year: new Date().getFullYear().toString(),
        zx: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0,
        itype: 1
      },
      downloadLoading: false,
      documentList: [],
      tableLoading: false,
    }
  },
  methods: {
    search(val = 1) {
      if (!this.searchVo.year) {
        this.$message({type: 'info', message: '对比年度不能为空'})
        return
      }
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/authorityReview/searchPersonalPositionContrastRecordsListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total
      }).finally(() => this.tableLoading = false);
    },
    download() {
      if (!this.searchVo.year) {
        this.$message.warning('目标年份不能为空');
        return
      }
      this.downloadLoading = true;
      this.$http.get('/api/authorityReview/PersonalPositionContrastRecords2Excel?year=' + this.searchVo.year + '&itype=' + this.searchVo.itype, {
        responseType: 'blob',
        timeout: 1200000
      }).then(res => {

        let blob = new Blob([res.data], {type: "application/vnd.ms-excel",});
        let fileName = this.searchVo.year + "年员工"+ (this.searchVo.itype === 1 ? "权限组" : "作业") +"对比记录"+new Date()+".xlsx";
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(blob, fileName);
        } else {
          let objectUrl = (window.URL || window.webkitURL).createObjectURL(
              blob
          );
          let downFile = document.createElement("a");
          downFile.style.display = "none";
          downFile.href = objectUrl;
          downFile.download = fileName;
          document.body.appendChild(downFile);
          downFile.click();
          document.body.removeChild(downFile);
          window.URL.revokeObjectURL(objectUrl);
        }
      }).finally(() => this.downloadLoading = false)
    },
    delLine({row, column, rowIndex, columnIndex}) {
      if (row.zx04 !== row.permissionCode) return "update_line";
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
<style lang="scss">
.update_line {
  background: #ffff00 !important
}
</style>

