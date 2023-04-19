<template>
  <div>
    <el-form :inline="true" :model="rva" class="demo-form-inline" size="mini">
      <el-form-item label="中心:">
        <selectedCentre v-model="rva.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="收货单号:">
        <el-input v-model="rva.code_1" placeholder="收货单号" clearable></el-input>
      </el-form-item>
      <el-form-item label="采购单号:">
        <el-input v-model="rva.code_2" placeholder="采购单号" clearable></el-input>
      </el-form-item>
      <el-form-item label="供应商:">
        <el-input v-model="rva.pmc" placeholder="供应商编码/简称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="采购收货: apmt110; JIT收货: apmt111; 委外收货: apmt200; 多角贸易采购收货: apmt300" type="success" :closable="false">
    </el-alert>
    <el-table :data="rvaList" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="rva01" label="收货单号" width="160">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="90">
      </el-table-column>
      <el-table-column prop="rva06" label="预计收货日期" width="120">
      </el-table-column>
      <el-table-column prop="rvaud02" label="到货确认码" width="90">
        <template slot-scope="scope">
          {{ scope.row.rvaud02 | formatConfirmationStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="rvaud13" label="到货日期" width="120">
      </el-table-column>
      <el-table-column prop="rvaconf" label="审核状态" width="90">
        <template slot-scope="scope">
          {{ scope.row.rvaconf | formatStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="rva32" label="签核状况" width="90">
        <template slot-scope="scope">
          {{ scope.row.rva32 | formatSignoff }}
        </template>
      </el-table-column>
      <el-table-column prop="rva02" label="采购单号" width="160">
      </el-table-column>
      <el-table-column prop="pmc03" label="供应商" width="160">
      </el-table-column>
      <el-table-column prop="rva10" label="采购性质" width="140">
        <template slot-scope="scope">
          {{ scope.row.rva10 | formatNature }}
        </template>
      </el-table-column>
      <el-table-column prop="rvaud03" label="采购确认码" width="90">
        <template slot-scope="scope">
          {{ scope.row.rvaud03 | formatConfirmationStatus }}
        </template>
      </el-table-column>
      <el-table-column prop="rvaud14" label="采购确认日期" width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchRvbList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="rva.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="rva.sizes"
                   :current-page.sync="rva.currentPage" :page-size="rva.size">
    </el-pagination>

    <!-- 收货单明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1000px">

      <el-form :inline="true" :model="rvab.rva" class="demo-form-inline" size="mini">
        <el-form-item label="收货单号:">
          <el-input v-model="rvab.rva.rva01" placeholder="收货单号" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="审核状态:">
          <selectedConf v-model="rvab.rva.rvaconf" :key="rvab.rva.rva01" @change="alterRva"></selectedConf>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-refresh-right" round @click="resetrva">刷新</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="rvab.rvbList" border max-height="300px">
        <el-table-column prop="rvb02" label="项次" width="60"></el-table-column>
        <el-table-column prop="rvb04" label="采购订单号" width="120"></el-table-column>
        <el-table-column prop="rvb05" label="料件编号" width="120"></el-table-column>
        <el-table-column prop="rvb051" label="料件名称" width="200"></el-table-column>
        <el-table-column prop="ima021" label="规格" width="240"></el-table-column>
        <el-table-column prop="rvb07" label="数量" width="160">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.rvb07" controls-position="right"
                             @change="scope.row.rvb88t = Math.round(scope.row.rvb07 * scope.row.rvb10t * 100)/100"
                             :min="0"
                             :precision="2" :step="1" size="mini"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="rvb30" label="入库数量" width="90"></el-table-column>
        <el-table-column prop="rvb90" label="单位" width="90"></el-table-column>
        <el-table-column prop="rvb10t" label="含税单价" width="120"></el-table-column>
        <el-table-column prop="rvb88t" label="含税金额" width="120"></el-table-column>
      </el-table>
      <el-divider></el-divider>
      <div style="text-align: right;">
        <el-button type="primary" round @click="alterRvb">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';

export default {
  name: 'rva',
  data() {
    return {
      rva: {
        code_1: null,
        code_2: null,
        pmc: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      rvaList: [],
      rvab: {
        rva: {},
        rvbList: []
      },
      table_loading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre,
    selectedConf
  },
  methods: {
    search(val) {
      this.rva.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/rva/searchRvaList', this.rva).then(res => {
        this.rvaList = res.data.result
        this.rva.total = res.data.total;
      }).finally(() => this.table_loading = false);

    },
    searchRvbList(row) {
      this.dialogTableVisible = true;
      this.rvab.rva = {...row};
      this.$http.post('/api/rva/searchRvbList', {code_1: row.rva01, centre: row.rvaplant}).then(res => {
        this.rvab.rvbList = res.data.result;
      })
    },
    reset() {
      this.rva.code_1 = null;
      this.rva.code_2 = null;
      this.rva.pmc = null;
      this.rva.centre = 'WCTZ';
    },
    resetrva() {
      this.rvaList.find(e => {
        if (e.rva01 === this.rvab.rva.rva01) {
          this.rvab.rva = {...e}
          return
        }
      })
      this.$http.post('/api/rva/searchRvbList', {
        code_1: this.rvab.rva.rva01,
        centre: this.rvab.rva.rvaplant
      }).then(res => {
        this.rvab.rvbList = res.data.result
      })
    },
    alterRva(val) {
      this.$http.post('/api/rva/alterRva', {rva01: this.rvab.rva.rva01, rvaplant: this.rvab.rva.rvaplant, rvaconf: val})
    },
    alterRvb() {
      this.$http.post('/api/rva/alterRvb', this.rvab.rvbList)
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.rva.size = val;
      this.search(1);
    }
  }
}
</script>

<style lang="scss" scoped>

::v-deep .el-dialog {
  .el-dialog__header {
    padding: 5px !important;
  }

  .el-dialog__body {
    padding: 15px 20px !important;
  }

  .el-divider {
    margin: 10px 0 !important;
  }
}</style>