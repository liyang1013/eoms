<template>
  <div>
    <el-form ref="form" :model="searchVo" :inline="true" class="demo-form-inline">
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="sfb"></selectedCentre>
      </el-form-item>
      <el-form-item label="工单号:">
        <el-input v-model="searchVo.code" style="width: 160px;" clearable placeholder="工单号"></el-input>
      </el-form-item>
      <el-form-item label="单据日期:">
        <el-date-picker v-model="searchVo.startdate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择开始日期" style="width: 160px"></el-date-picker>
        至
        <el-date-picker v-model="searchVo.enddate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择结束日期" style="width: 160px">
        </el-date-picker>
      </el-form-item>
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search()" round>查询</el-button>
        <el-button type="danger" @click="closeOut" round style="margin-left: 10px;">结案</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="工单维护: asfi301; 工单状况: asfq301; 工单逐张结案: asfp400; 工单批结案: asfp401;" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" stripe border max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="sfb01" label="工单号" width="160">
      </el-table-column>
      <el-table-column label="工单类型" width="120">
        <template slot-scope="scope">
          {{scope.row.sfb02 | formatSfb02 }}
        </template>
      </el-table-column>
      <el-table-column prop="sfb81" label="开单日期" width="120">
      </el-table-column>
      <el-table-column prop="gem02" label="成本中心" width="120">
      </el-table-column>
      <el-table-column prop="sfb102" label="产线" width="120">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="120">
      </el-table-column>
      <el-table-column prop="ima02" label="物料" width="160">
      </el-table-column>
      <el-table-column prop="sfb08" label="生产数量" width="120">
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="scope">
          {{scope.row.sfb04 | formatSfb04 }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchSfaList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   @current-change="handleCurrentChange" style=" margin-top: 10px;"
                   @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre'
import {formatSfb02} from "@/filters/filters";

export default {
  name: 'sfb',
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        code: null,
        startdate: null,
        enddate: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      documentList: [],
      selectedDocumentList: [],
      documents: {
        master: {},
        slave: []
      },
      tableLoading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val = 1) {
      this.tableLoading = true;
      this.searchVo.currentPage = val;
      this.$http.post('/api/sfb/searchSfbListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result;
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false)
    },
    searchSfaList() {

    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.searchVo.size = val;
      this.search(1);
    },
    handleSelectionChange(rows) {
      this.selectedDocumentList = rows;
    },
    // modifyDate() {
    //   if (this.temp.sfbArr.length === 0) {
    //     this.$message.warning("请勾选要修改的工单");
    //     return
    //   } else if (this.temp.ddate === null) {
    //     this.$message.warning("请选择要修改的日期");
    //     return
    //   }
    //   this.temp.centre = this.searchVo.centre
    //   this.modifyLoad = true;
    //   this.$http.post('/api/sfb/modifySfbDate', this.temp).finally(() => this.modifyLoad = false)
    // },
    closeOut() {
      // if (this.temp.sfbArr.length === 0) {
      //   this.$message.warning("请勾选要结案的工单");
      //   return;
      // }
      // this.temp.centre = this.searchVo.centre
      // this.$http.post('/api/sfb/closeOut', this.temp)
    }
  }
}
</script>
<style lang="sass" scoped>

</style>