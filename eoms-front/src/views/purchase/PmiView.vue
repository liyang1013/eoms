<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pmi"></selectedCentre>
      </el-form-item>
      <el-form-item label="核价单号:">
        <el-input v-model="searchVo.code" placeholder="核价单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="核价: apmi255; MRO核价: cpmi255; " type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pmi01" label="核价单号" width="160">
      </el-table-column>
      <el-table-column prop="pmi02" label="核价日期" width="120">
      </el-table-column>
      <el-table-column prop="pmc03" label="供应商" width="160">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="120">
      </el-table-column>
      <el-table-column prop="pmi081" label="税率" width="120">
        <template slot-scope="scope">
          {{ scope.row.pmi081 + '%' }}
        </template>
      </el-table-column>
      <el-table-column label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.pmiconf | formatStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="pmi04" label="备注" width="160">
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
            <el-form-item label="核价单号:">
              <span style="float: left;">{{ documents.master.pmi01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="核价时间:">
              {{ documents.master.pmi02 }}
            </el-form-item>
            <el-form-item label="备注:">
              {{documents.master.pmi04}}
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="申请人:">
              <span style="float: left;">{{ documents.master.pmi09 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
            <el-form-item label="供应商:">
              <span style="float: left;">{{ documents.master.pmi03 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.pmc03 }}</span>
            </el-form-item>
            <el-form-item label="税种:">
              <span style="float: left;">{{ documents.master.pmi08 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{documents.master.pmi081 + '%' }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="审核状态:">
              {{ documents.master.pmiconf | formatStatus }}
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.pmi06 | formatSignoff }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="300px">
        <el-table-column prop="pmj02" label="项次" width="60"></el-table-column>
        <el-table-column prop="pmj03" label="料件编号" width="120"></el-table-column>
        <el-table-column prop="pmj031" label="料件名称" width="160"></el-table-column>
        <el-table-column prop="pmj032" label="规格" width="160"></el-table-column>
        <el-table-column prop="ima44" label="单位" width="120"></el-table-column>
        <el-table-column prop="pmj07" label="未税单价" width="120"></el-table-column>
        <el-table-column prop="pmj07t" label="含税单价" width="120"></el-table-column>
        <el-table-column prop="pmj09" label="生效日期" width="120"></el-table-column>
        <el-table-column prop="pmj091" label="失效日期" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';

export default {
  name: 'pmi',
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
    search(val) {
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/pmi/searchPmiListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchSlaveList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/pmi/searchPmjList', {code: row.pmi01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
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