<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="货主:">
        <selectedCentre v-model="searchVo.centre" key="invtransfer"></selectedCentre>
      </el-form-item>
      <el-form-item label="料件:" >
        <selectedIma v-model="searchVo.ima" :centre="searchVo.centre" key="invtransfer"></selectedIma>
      </el-form-item>
      <el-form-item label="栈板码:" >
        <el-input v-model="searchVo.pallet" clearable></el-input>
      </el-form-item>
      <el-form-item label="批次号:" >
        <el-input v-model="searchVo.batch" clearable></el-input>
      </el-form-item>
      <el-form-item label="状态:">
        <el-select v-model="searchVo.status"  placeholder="质量状态" clearable>
          <el-option label="正常" value="ZC"></el-option>
          <el-option label="锁定" value="SD"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-table :data="documentList" border stripe style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="customerid" label="货主" width="120">
      </el-table-column>
      <el-table-column prop="locationid" label="库位" width="120">
      </el-table-column>
      <el-table-column prop="traceid" label="栈板码" width="120">
      </el-table-column>
      <el-table-column prop="sku" label="产品代码" width="160">
      </el-table-column>
      <el-table-column prop="lotnum" label="批次号" width="120">
      </el-table-column>
      <el-table-column prop="skudescr1" label="产品描述(L)" width="220">
      </el-table-column>
      <el-table-column prop="skudescr2" label="产品描述(S)" width="240">
      </el-table-column>
      <el-table-column prop="freightclass" label="货物类型" width="90">
      </el-table-column>
      <el-table-column prop="qty" label="数量" width="120">
      </el-table-column>
      <el-table-column prop="asqtyavailed" label="可用数" width="120">
      </el-table-column>
      <el-table-column prop="grossweight" label="毛重" width="120">
      </el-table-column>
      <el-table-column prop="lotatt04" label="批号" width="120">
      </el-table-column>
      <el-table-column prop="lotatt05" label="ERP逻辑仓" width="90">
      </el-table-column>
      <el-table-column prop="lotatt06" label="码托高度" width="90">
      </el-table-column>
      <el-table-column prop="lotatt07" label="区域" width="90">
      </el-table-column>
      <el-table-column prop="lotatt08" label="质量状态" width="90">
      </el-table-column>
      <el-table-column  label="操作数量" fixed="right" width="160">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.iqty" controls-position="right"
                           :min="0" :max="scope.row.asqtyavailed"
                           :precision="3" :step="1"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column  label="操作" width="120" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" v-if="scope.row.lotatt08 === 'SD'" @click="lock(scope.row,'ZC')">解锁</el-button>
          <el-button type="text" v-if="scope.row.lotatt08 === 'ZC'" @click="lock(scope.row,'SD')">锁定</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedIma from "@/components/selected/selected-ima.vue";

export default {
  name: "invTransfer",
  data() {
    return {
      searchVo: {
        centre: null,
        ima: null,
        status: null,
        batch: null,
        pallet: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      documentList: [],
      tableLoading: false
    }
  },
  methods: {
    search(val = 1){
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/wms/invLotLocIdListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);
    },
    lock(row,status){
      this.$http.post('/api/wms/lock?status='+status,[row]).then(res => {
        if(res.data.status === 200) this.search(this.searchVo.currentPage);
      })
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.searchVo.size = val;
      this.search(1);
    },
  },
  components: {
    selectedCentre,
    selectedIma
  }
}
</script>