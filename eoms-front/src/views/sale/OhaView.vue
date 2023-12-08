<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="rvu"></selectedCentre>
      </el-form-item>
      <el-form-item label="销退单号:">
        <el-input v-model="searchVo.code" placeholder="销退单号" clearable></el-input>
      </el-form-item>
      <el-form-item label="多角贸易号:">
        <el-input v-model="searchVo.multiTradeCode" placeholder="多角贸易号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="销退单维护: axmt700;" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="oha01" label="销退单号" width="160">
      </el-table-column>
      <el-table-column prop="oha02" label="销退日期" width="120">
      </el-table-column>
      <el-table-column  label="销售类型" width="120">
        <template slot-scope="scope">
          {{scope.row.oha08 | formatSaleType }}
        </template>
      </el-table-column>
      <el-table-column prop="occ02" label="客户" width="160">
      </el-table-column>
      <el-table-column prop="gem02" label="部门" width="120">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="120">
      </el-table-column>
      <el-table-column prop="oha99" label="多交贸易序列号" width="160">
      </el-table-column>
      <el-table-column  label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.ohaconf | formatVerifyStatus }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchOgbList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>

    <!-- 明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1200px">

      <el-form label-position="left"  :model="documents.master">
        <el-row >
          <el-col :span="7" >
            <el-form-item label="销退单号:">
              <span style="float: left;">{{ documents.master.oha01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="销售类型:">
              {{ documents.master.oha08 | formatSaleType }}
            </el-form-item>
            <el-form-item label="销退日期:">
              <el-date-picker type="date" placeholder="选择日期" v-model="documents.master.oha02" value-format="yyyy-MM-dd" style="width: 140px;" @change="alterOgaDate(documents.master)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="客户:">
              <span style="float: left;">{{ documents.master.oha03 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.occ02 }}</span>
            </el-form-item>
            <el-form-item label="部门:">
              <span style="float: left;">{{ documents.master.oha15 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px"> {{ documents.master.gem02 }}</span>
            </el-form-item>
            <el-form-item label="人员:">
              <span style="float: left;">{{ documents.master.oha14 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="审核状态:">
              {{ documents.master.ohaconf | formatVerifyStatus }}
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.oha55 | formatStatus }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="300px">
        <el-table-column prop="ohb03" label="项次" width="60"></el-table-column>
        <el-table-column prop="ohb04" label="料件编号" width="120"></el-table-column>
        <el-table-column prop="ohb06" label="料件名称" width="160"></el-table-column>
        <el-table-column prop="ima021" label="规格" width="160"></el-table-column>
        <el-table-column prop="imd02" label="仓库" width="120"></el-table-column>
        <el-table-column prop="ohb12" label="数量" width="120"></el-table-column>
        <el-table-column prop="ohb05" label="单位" width="90"></el-table-column>
        <el-table-column prop="ohb13" label="未税单价" width="120"></el-table-column>
        <el-table-column prop="ohb14t" label="含税金额" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';
import {formatStatus} from "@/filters/filters";

export default {
  name: 'oha',
  data() {
    return {
      searchVo: {
        code: null,
        multiTradeCode: null,
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
      this.$http.post('/api/oha/searchOhaListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchOgbList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/oha/searchOhbList', {code: row.oha01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
    },
    alterOgaDate(master){
      this.$http.post('/api/oha/alterOhaDate', master)
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
    padding: 20px!important;
  }
}
</style>