<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
        <el-button  type="danger" @click="closeOut" round style="margin-left: 10px;">结案</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pmm"></selectedCentre>
      </el-form-item>
      <el-form-item label="采购单号:">
        <el-input v-model="searchVo.code" placeholder="采购单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="一般采购: apmt540; MRO采购: cpmt540; 委外采购: cpmt590; 采购变更: apmt910; MRO采购变更: cpmt910;采购结案: apmp551; 采购结案还原: apmp552;" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pmm01" label="采购单号" width="160">
      </el-table-column>
      <el-table-column prop="pmm02" label="采购性质"  width="160">
        <template slot-scope="scope">
          {{scope.row.pmm02 | formatNature}}
        </template>
      </el-table-column>
      <el-table-column prop="pmm04" label="采购日期" width="120">
      </el-table-column>
      <el-table-column prop="pmc03" label="供应商" width="160">
      </el-table-column>
      <el-table-column prop="gem02" label="部门" width="120">
      </el-table-column>
      <el-table-column prop="gen02" label="采购员" width="120">
      </el-table-column>
      <el-table-column label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.pmm18 | formatStatus }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchSlaveList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>

    <!-- 明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1200px" >
      <el-form label-position="left" :model="documents.master">
        <el-row >
          <el-col :span="7" >
            <el-form-item label="采购单号:">
              <span style="float: left;">{{ documents.master.pmm01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="采购性质:">
              {{ documents.master.pmm02 | formatNature}}
            </el-form-item>
            <el-form-item label="付款方式:">
              <span style="float: left;">{{ documents.master.pmm20 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{documents.master.pma02 }}</span>
            </el-form-item>
            <el-form-item label="价格条件:">
              <span style="float: left;">{{ documents.master.pmm41 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{documents.master.pnz02 }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="申请人:">
              <span style="float: left;">{{ documents.master.pmm12 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
            <el-form-item label="供应商:">
              <span style="float: left;">{{ documents.master.pmm09 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.pmc03 }}</span>
            </el-form-item>
            <el-form-item label="税种:">
              <span style="float: left;">{{ documents.master.pmm21 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{documents.master.pmm43 + '%' }}</span>
            </el-form-item>
            <el-form-item label="税种:">
              {{ documents.master.pmm22 }}
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="核价时间:">
              {{ documents.master.pmm04 }}
            </el-form-item>
            <el-form-item label="审核状态:">
              {{ documents.master.pmm18 | formatStatus }}
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.pmm25 | formatSignoff }}
            </el-form-item>
            <el-form-item label="备注:">
              {{documents.master.pmmud01}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="300px">
        <el-table-column prop="pmn02" label="项次" width="60"></el-table-column>
        <el-table-column label="状况码" width="120">
          <template slot-scope="scope">
            {{ scope.row.pmn16 | formatSignoff}}
          </template>
        </el-table-column>
        <el-table-column prop="pmn04" label="料件编号" width="120"></el-table-column>
        <el-table-column prop="pmn041" label="料件名称" width="160"></el-table-column>
        <el-table-column prop="ima021" label="规格" width="120"></el-table-column>
        <el-table-column prop="pmn07" label="单位" width="120"></el-table-column>
        <el-table-column prop="pmn20" label="采购量" width="120"></el-table-column>
        <el-table-column prop="pmn31" label="未税单价" width="120"></el-table-column>
        <el-table-column prop="pmn31t" label="含税单价" width="120"></el-table-column>
        <el-table-column prop="pmn88" label="未税金额" width="120"></el-table-column>
        <el-table-column prop="pmn88t" label="含税金额" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre';
import selectedConf from '@/components/selected/selected-conf';

export default {
  name: 'pmm',
  data() {
    return {
      searchVo: {
        code: null,
        centre: 'WCTZ',
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
      tableLoading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre,
    selectedConf
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/pmm/searchPmmListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchSlaveList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/pmm/searchPmnList', {code: row.pmm01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
    },
    closeOut(){
      if (this.selectedDocumentList.length === 0) {
        this.$message.warning("请勾选要结案的单据");
        return;
      }
      this.$http.post('/api/pmm/closeOut', this.selectedDocumentList)
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
  }
}
</script>

<style lang="scss" scoped>

::v-deep .el-dialog {
  .el-dialog__header {
    padding: 10px !important;
  }

  .el-dialog__body {
    padding: 20px !important;
  }
}
</style>