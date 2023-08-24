<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="pmk"></selectedCentre>
      </el-form-item>
      <el-form-item label="请购单号:">
        <el-input v-model="searchVo.code" placeholder="请购单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="请购: apmt420; MRO请购: cpmt420;请购变更: apmt900; 请购批处理: apmp200; 请购结案: apmp451; 请购结案还原: apmp452;请购转采购: apmp500" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="pmk01" label="请购单号" >
      </el-table-column>
      <el-table-column prop="pmk02" label="性质">
        <template slot-scope="scope">
          {{scope.row.pmk02 | formatNature}}
        </template>
      </el-table-column>
      <el-table-column prop="pmk04" label="请购日期">
      </el-table-column>
      <el-table-column prop="gem02" label="部门" >
      </el-table-column>
      <el-table-column prop="gen02" label="申请人">
      </el-table-column>
      <el-table-column prop="pmk18" label="审核状态">
        <template slot-scope="scope">
          {{scope.row.pmk18 | formatStatus}}
        </template>
      </el-table-column>
      <el-table-column label="状况" >
        <template slot-scope="scope">
          {{ scope.row.pmk25 | formatSignoff }}
        </template>
      </el-table-column>

      <el-table-column label="操作" >
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
            <el-form-item label="请购单号:">
              <span style="float: left;">{{ documents.master.pmk01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="请购性质:">
              {{documents.master.pmk02 | formatNature}}
            </el-form-item>
            <el-form-item label="请购日期:">
              {{ documents.master.pmk04 }}
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="部门:">
              <span style="float: left;">{{ documents.master.pmk13 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gem02 }}</span>
            </el-form-item>
            <el-form-item label="申请人:">
              <span style="float: left;">{{ documents.master.pmk12 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="审核状态:">
              {{ documents.master.pmk18 | formatStatus }}
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.pmk25 | formatSignoff }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="300px">
        <el-table-column prop="pml02" label="项次" width="60"></el-table-column>
        <el-table-column prop="pml04" label="料件编号" width="120"></el-table-column>
        <el-table-column prop="pml041" label="料件名称" width="160"></el-table-column>
        <el-table-column prop="ima021" label="规格" width="160"></el-table-column>
        <el-table-column prop="pml07" label="单位" width="120"></el-table-column>
        <el-table-column prop="pml20" label="请购量" width="120"></el-table-column>
        <el-table-column prop="pml21" label="转采购量" width="120"></el-table-column>
        <el-table-column prop="pml16" label="状况" width="120">
          <template slot-scope="scope">
            {{ scope.row.pml16 | formatSignoff }}
          </template>
        </el-table-column>
        <el-table-column prop="pml57" label="仓库" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';

export default {
  name: 'pmk',
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
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/pmk/searchPmkListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchSlaveList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/pmk/searchPmlList', {code: row.pmk01, centre: row.centre}).then(res => {
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