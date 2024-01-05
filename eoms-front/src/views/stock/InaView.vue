<template>
  <div>
    <el-form :inline="true" :model="ina" class="demo-form-inline">
      <el-form-item style="float: right">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="ina"></selectedCentre>
      </el-form-item>
      <el-form-item label="杂项单号:">
        <el-input v-model="searchVo.code" placeholder="单号" clearable></el-input>
      </el-form-item>
    </el-form>
    <el-alert title="库存杂项发料 aimt301; WIP 杂项发料 aimt311; 库存杂项收料 aimt302; WIP 杂项收料 aimt312; 库存杂项报废 aimt303; WIP 杂项报废 aimt313" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60"></el-table-column>
      <el-table-column  label="单据类型" width="200">
        <template slot-scope="scope">
          {{scope.row.ina00 | formatInaType}}
        </template>
      </el-table-column>
      <el-table-column prop="ina01" label="单号" width="120"></el-table-column>
      <el-table-column prop="ina03" label="录入日期" width="120"></el-table-column>
      <el-table-column prop="ina02" label="扣账日期" width="120"></el-table-column>
      <el-table-column prop="gem02" label="部门" width="180"></el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="scope">
          {{scope.row.inaconf | formatVerifyStatus}}
        </template>
      </el-table-column>
      <el-table-column prop="pja02" label="项目" width="160"></el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchSlaveList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>

    <!-- 明细 -->
    <el-dialog :visible.sync="dialogTableVisible" width="1200px">
      <el-form label-position="left" :model="documents.master">
        <el-row >
          <el-col :span="7" >
            <el-form-item label="杂项单号:">
              <span style="float: left;">{{ documents.master.ina01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="录入时间:">
              {{ documents.master.ina03 }}
            </el-form-item>
            <el-form-item label="备注:">
              {{documents.master.ina07}}
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="项目:">
              <selectedPja v-model="documents.master.ina06" :centre="documents.master.centre"  :key="documents.master.ina01" @change="alterPja($event,documents.master)"></selectedPja>
            </el-form-item>
            <el-form-item label="部门:">
              <selectedGem v-model="documents.master.ina04" :centre="documents.master.centre" :key="documents.master.ina01" @change="alterGem($event,documents.master)"></selectedGem>
            </el-form-item>
            <el-form-item label="申请人:">
              <span style="float: left;">{{ documents.master.ina11 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="审核状态:">
              <selectedConf v-model="documents.master.inaconf" :key="documents.master.ina01"
                            @change="alterInaConf"></selectedConf>
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.ina08 | formatSignoff }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="350px">
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
import selectedConf from '@/components/selected/selected-conf.vue';

export default {
  name: 'ina',
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
    selectedGem,
    selectedAzf,
    selectedPja,
    selectedPjb,
    selectedConf
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true;
      this.$http.post('/api/ina/searchInaListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false);

    },
    searchSlaveList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/ina/searchInbList', {code: row.ina01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
    },
    alterInaConf(val) {
      this.$http.post('/api/ina/alterConf', {
        ina01: this.documents.master.ina01,
        centre: this.documents.master.centre,
        inaconf: val
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
      this.searchVo.size = val;
      this.search(1);
    }
  }
}
</script>

<style lang="scss" scoped>

</style>