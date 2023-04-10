<template>
  <div>
    <el-form ref="form" :model="sfb" size="mini" :inline="true" class="demo-form-inline">
      <el-form-item label="中心：">
        <selectedCentre v-model="sfb.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="工单号：">
        <el-input v-model="sfb.code_1" style="width: 160px;"></el-input>
      </el-form-item>
      <el-form-item label="开单日期:">
        <el-date-picker type="date" placeholder="选择日期" v-model="sfb.startdate" value-format="yyyy-MM-dd"
                        style="width: 140px;"></el-date-picker>
        <span> - </span>
        <el-date-picker type="date" placeholder="选择日期" v-model="sfb.enddate" value-format="yyyy-MM-dd"
                        style="width: 140px;"></el-date-picker>
      </el-form-item>
      <el-button size="mini" type="primary" @click="searchSfb()" round>查询</el-button>
      <br>
      <el-form-item label="修改工单日期：">
        <el-date-picker type="date" placeholder="选择日期" v-model="temp.ddate" value-format="yyyy-MM-dd"
                        style="width: 140px;"></el-date-picker>
        &nbsp&nbsp

        <el-switch v-model="temp.flag" active-text="改大" inactive-text="改小"></el-switch>

        <el-button size="mini" type="danger" @click="modifyDate()" round style="margin-left: 10px;"
                   :loading="modifyLoad">修改
        </el-button>
      </el-form-item>
    </el-form>

    <el-table :data="sfbArr" stripe border max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading"
              style="width: 1200px;" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="sfb01" label="工单号">
      </el-table-column>
      <el-table-column prop="sfb81" label="开单日期">
      </el-table-column>
      <el-table-column prop="ima02" label="物料">
      </el-table-column>
      <el-table-column prop="sfb04" label="状态">
      </el-table-column>
      <el-table-column prop="gem02" label="部门">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="sfb.total"
                   @current-change="handleCurrentChange" style=" margin-top: 10px;"
                   @size-change="handleSizeChange" :page-sizes="sfb.sizes"
                   :current-page.sync="sfb.currentPage" :page-size="sfb.size">
    </el-pagination>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected-centre.vue'

export default {
  name: 'workOrderEdit',
  data() {
    return {
      sfb: {
        centre: 'WCTZ',
        code_1: '',
        startdate: null,
        enddate: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      sfbArr: [],
      temp: {
        sfbArr: [],
        ddate: null,
        centre: '',
        flag: false
      },
      modifyLoad: false,
      table_loading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    searchSfb(val = 1) {
      this.table_loading = true;
      this.sfb.currentPage = val;
      this.$http.post('/api/sfb/searchSfbList', this.sfb).then(res => {
        this.sfbArr = res.data.result;
        this.sfb.total = res.data.total;
      }).finally(() => this.table_loading = false)
    },
    handleCurrentChange(val) {
      this.searchSfb(val);
    },
    handleSizeChange(val) {
      this.sfb.size = val;
      this.searchSfb(1);
    },
    handleSelectionChange(rows) {
      this.temp.sfbArr = rows;
    },
    modifyDate() {
      if (this.temp.sfbArr.length === 0) {
        this.$message.warning("请勾选要修改的工单");
        return
      } else if (this.temp.ddate === null) {
        this.$message.warning("请选择要修改的日期");
        return
      }
      this.temp.centre = this.sfb.centre
      this.modifyLoad = true;
      this.$http.post('/api/sfb/modifyDate', this.temp).then().finally(() => this.modifyLoad = false)

    }
  }

}
</script>
<style lang="sass" scoped>

</style>