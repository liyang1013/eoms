<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="success" round icon="el-icon-download" @click="currentPermissionExcel" :loading="downloadLoading">
          现行权限档案
        </el-button>
        <el-button type="primary" @click="search()" icon="el-icon-search" round :loading="tableLoading">查询</el-button>
      </el-form-item>
      <el-form-item label="对比年度:">
        <el-date-picker v-model="searchVo.year" type="year" value-format="yyyy" placeholder="选择年"></el-date-picker>
      </el-form-item>
      <el-form-item label="职位:">
        <el-select style="width: 400px" v-model="searchVo.codes" filterable remote reserve-keyword multiple collapse-tags
                   placeholder="请选择职位" clearable :remote-method="searchZwList">
          <el-option
              :disabled="item.zw01 === 'CLASS-A'"
              v-for="item in zwList"
              :key="item.zw01"
              :label="item.zw02"
              :value="item.zw01">
            <span style="float: left">{{ item.zw01 }}</span>
            <span style="float: right; color: #5d87cb; font-size: 13px">{{ item.zw02 }}</span>
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-alert :title="title" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading" :cell-class-name="delLine">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="positionname" label="职位"></el-table-column>
      <el-table-column prop="permissioncode" label="作业代码"></el-table-column>
      <el-table-column prop="permissionname" label="作业名称"></el-table-column>
      <el-table-column prop="containAmount" label="是否有金额"></el-table-column>
    </el-table>
    <el-pagination background layout="total, prev, pager, next" :total="titleMap.length"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" page-size="1"
                   :current-page.sync="index" >
    </el-pagination>
  </div>
</template>
<script>
export default {
  name: "contrast",
  data() {
    return {
      searchVo: {
        year: new Date().getFullYear().toString(),
        codes: []
      },
      downloadLoading: false,
      documentList: [],
      documentMap: {},
      selectedList: [],
      zwList: [],
      tableLoading: false,
      titleMap: [],
      title: '',
      index: 1
    }
  },
  methods: {
    search() {
      if (!(this.searchVo.year && this.searchVo.codes.length)) {
        this.$message({type: 'info', message: '对比年年度和职位不能为空'})
        return
      }
      this.tableLoading = true
      this.$http.post('/api/authorityReview/contrastRecords', this.searchVo).then(res => {
        this.index = 1;
        this.documentMap = res.data.result
        this.titleMap = Object.keys(this.documentMap);
        this.title = Object.keys(this.documentMap)[this.index - 1]
        this.documentList = this.documentMap[this.title];

      }).finally(() => this.tableLoading = false);
    },
    currentPermissionExcel() {
      this.downloadLoading = true;
      this.$http.get('/api/authorityReview/currentPermissionExcel', {responseType: 'blob'}).then(res => {

        let blob = new Blob([res.data], {type: "application/vnd.ms-excel",});
        let fileName = "现行权限档案.xlsx";
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          // IE
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
      }).finally(() => this.downloadLoading = false )
    },
    searchZwList(str) {
      if (!str) return;
      this.$http.get('/api/zw/searchZwList?query=' + str).then(res => {
        this.zwList = res.data.result;
      });
    },
    delLine({row, column, rowIndex, columnIndex}) {
      if (row.color === 'red') return "del_line";
      if (row.color === 'green') return "add_line";
    },
    handleCurrentChange(val) {
      this.title = Object.keys(this.documentMap)[this.index - 1]
      this.documentList = this.documentMap[this.title];
    },
  }
}
</script>

<style lang="scss">
.del_line {
  background: #faafaf !important;

  ::after {
    content: no-open-quote;
    position: absolute;
    top: 51%;
    left: 0;
    width: 100%;
    border-bottom: 1px solid #bd2424;
  }
}

.add_line {
  background: #bdf4a2 !important
}
</style>
