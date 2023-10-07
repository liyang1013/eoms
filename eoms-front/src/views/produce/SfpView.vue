<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="sfp"></selectedCentre>
      </el-form-item>
      <el-form-item label="发料单号:">
        <el-input v-model="searchVo.code" placeholder="收货单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="工单发料: asfi510; 工单成套发料: asfi511; 工单超领发料: asfi512; 工单欠料补料: asfi513; 工单退料: asfi520; 工单成套退料: asfi526; 工单超领退料: asfi527; 工单一般退料: asfi528;" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="sfp01" label="发料单号" width="160">
      </el-table-column>
      <el-table-column prop="sfp06" label="发料类型" width="120">
        <template slot-scope="scope">
          {{ scope.row.sfp06 | formatSfp06 }}
        </template>
      </el-table-column>
      <el-table-column prop="gem02" label="部门" width="160">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="90">
      </el-table-column>
      <el-table-column prop="sfp02" label="录入日期" width="120">
      </el-table-column>
      <el-table-column prop="sfp03" label="扣账日期" width="120">
      </el-table-column>
      <el-table-column prop="sfpconf" label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.sfpconf | formatStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="sfp15" label="签核状况" width="90">
        <template slot-scope="scope">
          {{ scope.row.sfp15 | formatSignoff }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchRvbList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>

    <!-- 收货单明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1200px">

      <el-form label-position="left"  :model="documents.master">
        <el-row >
          <el-col :span="7" >
            <el-form-item label="发料单号:">
              <span style="float: left;">{{ documents.master.sfp01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="录入日期:">
              {{ documents.master.sfp02}}
            </el-form-item>
            <el-form-item label="扣账日期:">
              {{ documents.master.sfp03 }}
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="发料类型:">
              {{ documents.master.sfp06 | formatSfp06 }}
            </el-form-item>
            <el-form-item label="部门:">
              <span style="float: left;">{{ documents.master.sfp07 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gem02 }}</span>
            </el-form-item>
            <el-form-item label="申请人:">
              <span style="float: left;">{{ documents.master.sfp16 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="审核状态:">
              <selectedConf v-model="documents.master.sfpconf" :key="documents.master.sfp01" @change="alterSfpConf"></selectedConf>
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.sfp15 | formatSignoff }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="300px">
        <el-table-column prop="sfs02" label="项次" width="60"></el-table-column>
        <el-table-column prop="sfs03" label="工单号" width="120"></el-table-column>
        <el-table-column prop="sfs04" label="发料料号" width="120"></el-table-column>
        <el-table-column prop="ima02" label="品名" width="160"></el-table-column>
        <el-table-column prop="sfs06" label="单位" width="120"></el-table-column>
        <el-table-column prop="sfs07" label="仓库" width="120"></el-table-column>
        <el-table-column prop="imd02" label="仓库名称" width="120"></el-table-column>
        <el-table-column prop="sfs05" label="发料量" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';
import {formatSfp06} from "@/filters/filters";

export default {
  name: 'sfp',
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
      this.$http.post('/api/sfp/searchSfpListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchRvbList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/sfp/searchSfseList', {code: row.sfp01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
    },
    alterSfpConf(val) {
      this.$http.post('/api/sfp/alterSfpConf', {
        sfp01: this.documents.master.sfp01,
        centre: this.documents.master.centre,
        sfpconf: val
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
    padding: 20px 20px 10px 20px!important;
  }
}
</style>