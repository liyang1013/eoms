<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search()" icon="el-icon-search" round :loading="tableLoading">查询</el-button>
      </el-form-item>
      <el-form-item label="发票代码:">
        <el-input v-model="searchVo.code" placeholder="发票代码" clearable></el-input>
      </el-form-item>
      <el-form-item label="发票号码:">
        <el-input v-model="searchVo.no" placeholder="发票号码" clearable></el-input>
      </el-form-item>
      <el-form-item label="开票日期:">
        <el-date-picker v-model="searchVo.startdate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择开始日期" style="width: 160px"></el-date-picker>
        至
        <el-date-picker v-model="searchVo.enddate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择结束日期" style="width: 160px">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号"></el-table-column>
      <el-table-column prop="invoiceType" label="发票类型"></el-table-column>
      <el-table-column prop="invoiceCode" label="发票代码"></el-table-column>
      <el-table-column prop="invoiceNo" label="发票号码"></el-table-column>
      <el-table-column prop="invoiceDate" label="开票日期"></el-table-column>
      <el-table-column prop="amount" label="金额"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="downloadFile(scope.row)">下载</el-button>
        </template>
      </el-table-column>
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
  name: "vat-invoice",
  data() {
    return {
      searchVo: {
        startdate: null,
        enddate: null,
        code: null,
        no: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      documentList: [],
      tableLoading: false,
    }
  },
  methods: {
    search(val = 1) {
      console.log(this.searchVo.invoiceDate)
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/invoice/searchVatInvoiceListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total
      }).finally(() => this.tableLoading = false);
    },
    downloadFile(row) {
      const fileName = row.fileName;
      this.$http.get('/api/invoice/downloadFile?fileName=' + fileName, {responseType: 'blob'}).then((res) => {
        console.log('文件下载成功');
        const downloadElement = document.createElement("a");
        const blob = new Blob([res.data]);
        const url = window.URL.createObjectURL(blob);
        downloadElement.href = url;
        downloadElement.download = fileName;
        document.body.appendChild(downloadElement);
        downloadElement.click();
        document.body.removeChild(downloadElement);
      }).catch((res) => {
        console.log('文件下载失败');
      });
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
