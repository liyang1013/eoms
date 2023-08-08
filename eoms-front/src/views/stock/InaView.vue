<template>
  <div>
    <el-form :inline="true" :model="ina" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="ina.centre" key="ina"></selectedCentre>
      </el-form-item>
      <el-form-item label="杂项单号:">
        <el-input v-model="ina.code" placeholder="单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-table :data="inaList" border style="width: 100%" max-height="500px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column  label="单据类型" width="200">
        <template slot-scope="scope">
          {{scope.row.ina00 | formatInaType}}
        </template>
      </el-table-column>
      <el-table-column prop="ina01" label="单号" width="120"></el-table-column>
      <el-table-column prop="ina03" label="录入日期" width="120"></el-table-column>
      <el-table-column label="部门" width="180">
        <template slot-scope="scope">
          <selectedGem v-model="scope.row.ina04" :centre="scope.row.centre" :key="scope.row.ina01" @change="alterGem($event,scope.row)"></selectedGem>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="scope">
          {{scope.row.inaconf | formatStatus}}
        </template>
      </el-table-column>
      <el-table-column label="项目" width="160">
        <template slot-scope="scope">
          <selectedPja v-model="scope.row.ina06" :centre="scope.row.centre"  :key="scope.row.ina01" @change="alterPja($event,scope.row)"></selectedPja>
        </template>
      </el-table-column>
      <el-table-column label="过账" width="120">
        <template slot-scope="scope">
          {{scope.row.inapost | formatPost}}
        </template>
      </el-table-column>
      <el-table-column prop="ina02" label="过账时间" width="120"></el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchInbList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="ina.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="ina.sizes"
                   :current-page.sync="ina.currentPage" :page-size="ina.size">
    </el-pagination>

    <!-- 明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1200px">
      <el-table :data="inbList" border max-height="350px">
        <el-table-column prop="inb03" label="项次" width="50"></el-table-column>
        <el-table-column prop="inb04" label="料件编码" width="150"></el-table-column>
        <el-table-column prop="ima02" label="料件名称" width="160"></el-table-column>
        <el-table-column prop="ima021" label="料件规格" width="150"></el-table-column>
        <el-table-column prop="inb05" label="仓库" width="90"></el-table-column>
        <el-table-column prop="imd02" label="仓库名称" width="120"></el-table-column>
        <el-table-column prop="inb16" label="数量" width="90"></el-table-column>
        <el-table-column prop="inb09" label="异动数量" width="90"></el-table-column>
        <el-table-column prop="inb08" label="单位" width="90"></el-table-column>
        <el-table-column label="WBS编码 " width="160">
          <template slot-scope="scope">
            <selectedPjb v-model="scope.row.inb42" :centre="scope.row.centre" :pjb01="scope.row.inb41" :key="scope.row.inb01 + scope.row.inb03" @change="alterPjb($event,scope.row)"></selectedPjb>
          </template>
        </el-table-column>
        <el-table-column label="理由码" width="160">
          <template slot-scope="scope">
            <selectedAzf v-model="scope.row.inb15"  :centre="scope.row.centre"  azf02="2" azf09="4" :key="scope.row.inb01 + scope.row.inb03" @change="alterAzf($event,scope.row)"></selectedAzf>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre';
import selectedGem from "@/components/selected/selected-gem";
import selectedAzf from "@/components/selected/selected-azf";
import selectedPja from "@/components/selected/selected-pja";
import selectedPjb from "@/components/selected/selected-pjb";

export default {
  name: 'ina',
  data() {
    return {
      ina: {
        code: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      inaList: [],
      inbList: [],
      table_loading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre,
    selectedGem,
    selectedAzf,
    selectedPja,
    selectedPjb
  },
  methods: {
    search(val) {
      this.ina.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/ina/searchInaListPageHelper', this.ina).then(res => {
        this.inaList = res.data.result
        this.ina.total = res.data.total;
      }).finally(() => this.table_loading = false);

    },
    searchInbList(row) {
      this.dialogTableVisible = true;
      this.$http.post('/api/ina/searchInbList', {code: row.ina01, centre: row.centre}).then(res => {
        this.inbList = res.data.result;
      })
    },
    alterGem(gem01,row){
      this.$http.post('/api/ina/alterGem',{ina04: gem01,ina01: row.ina01,centre: row.centre})
    },
    alterAzf(azf01,row){
      this.$http.post('/api/ina/alterAzf',{inb15: azf01,inb01: row.inb01,inb03: row.inb03,centre: row.centre})
    },
    alterPja(pja01,row){
      this.$http.post('/api/ina/alterPja',{ina06: pja01,ina01: row.ina01,centre: row.centre})
    },
    alterPjb(pjb01,row){
      this.$http.post('/api/ina/alterPjb',{inb42: pjb01,inb01: row.inb01,inb03: row.inb03,centre: row.centre})
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.ina.size = val;
      this.search(1);
    }
  }
}
</script>

<style lang="scss" scoped>

</style>