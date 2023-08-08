<template>
  <div>
    <el-form :inline="true" :model="wdi" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="wdi.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="出库单号:">
        <el-input v-model="wdi.code" placeholder="出库单号" clearable></el-input>
      </el-form-item>
    </el-form>

    <el-table :data="wdiList" border style="width: 100%" max-height="500px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column prop="cdanhao" label="出库单号" width="160"></el-table-column>
      <el-table-column prop="tcWdi20" label="制单日期" width="120"></el-table-column>
      <el-table-column prop="tcWdi21" label="制单人" width="120"></el-table-column>
      <el-table-column prop="tcWdi53" label="拣货方式" width="120">
        <template slot-scope="scope">
          {{scope.row.tcWdi53 | pickType}}
        </template>
      </el-table-column>
      <el-table-column prop="tcWdi54" label="拣货状态" width="120">
        <template slot-scope="scope">
          {{scope.row.tcWdi54 | formatWdi54}}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchWdjList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="wdi.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="wdi.sizes"
                   :current-page.sync="wdi.currentPage" :page-size="wdi.size">
    </el-pagination>

    <!-- 收货单明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1000px">
      <el-table :data="wdjList" border max-height="300px">
        <el-table-column prop="cbarcodeno" label="栈板码" width="120"></el-table-column>
        <el-table-column prop="cproduceno" label="品号" width="120"></el-table-column>
        <el-table-column prop="cwhcode" label="仓库" width="120"></el-table-column>
        <el-table-column prop="chuojiano" label="库位" width="120"></el-table-column>
        <el-table-column prop="iqty" label="数量" width="160"></el-table-column>
        <el-table-column prop="ickqty" label="出库数量" width="160"></el-table-column>
        <el-table-column prop="fickqty" label="flux出库数量" width="160"></el-table-column>
        <el-table-column prop="cdc" label="生产日期" width="120"></el-table-column>
        <el-table-column prop="tcWdj21" label="操作人" width="120"></el-table-column>
        <el-table-column prop="tcWdj20" label="操作时间" width="120"></el-table-column>
        <el-table-column fixed="right" label="操作" width="90">
          <template slot-scope="scope">
            <el-button @click="repair(scope.row)" type="text">修复</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue';
import selectedConf from '@/components/selected/selected-conf.vue';
import {pickType} from "@/filters/filters";

export default {
  name: 'wdi',
  data() {
    return {
      wdi: {
        code: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      wdiList: [],
      wdjList: [],
      table_loading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val) {
      this.wdi.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/wdi/searchWdiListPageHelper', this.wdi).then(res => {
        this.wdiList = res.data.result
        this.wdi.total = res.data.total;
      }).finally(() => this.table_loading = false);

    },
    searchWdjList(row) {
      this.dialogTableVisible = true;
      this.$http.post('/api/wdi/searchWdjList', {code: row.cdanhao, centre: row.centre}).then(res => {
        this.wdjList = res.data.result;
      })
    },
    repair(row) {
      this.$http.post('/api/wdi/repairWdj', row)
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.wdi.size = val;
      this.search(1);
    }
  }
}
</script>

<style lang="scss" scoped>

</style>