<template>
  <div>
    <el-form ref="form" :model="searchVo" :inline="true" class="demo-form-inline">
      <el-form-item style="float:right;">
        <el-button type="primary" @click="search()" round>查询</el-button>
        <el-button type="danger" @click="closeOut" round style="margin-left: 10px;">结案</el-button>
      </el-form-item>
      <el-form-item label="中心:">
        <selectedCentre v-model="searchVo.centre" key="sfb"></selectedCentre>
      </el-form-item>
      <el-form-item label="工单号:">
        <el-input v-model="searchVo.code" style="width: 160px;" clearable placeholder="工单号"></el-input>
      </el-form-item>
      <el-form-item label="单据日期:">
        <el-date-picker v-model="searchVo.startdate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择开始日期" style="width: 160px"></el-date-picker>
        至
        <el-date-picker v-model="searchVo.enddate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择结束日期" style="width: 160px">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="日期修正:">
        <el-date-picker type="date" placeholder="选择日期" v-model="sfbModify.ddate" value-format="yyyy-MM-dd" style="width: 140px;"></el-date-picker>
        &nbsp;&nbsp;
        <el-switch v-model="sfbModify.flag" active-text="改大" inactive-text="改小"></el-switch>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="danger" @click="modifyDate()" round style="margin-left: 10px;">修改
        </el-button>
      </el-form-item>
    </el-form>
    <el-alert title="工单维护: asfi301; 工单状况: asfq301; 工单逐张结案: asfp400; 工单批结案: asfp401;" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" stripe border max-height="450px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="sfb01" label="工单号" width="160">
      </el-table-column>
      <el-table-column label="工单类型" width="120">
        <template slot-scope="scope">
          {{scope.row.sfb02 | formatSfb02 }}
        </template>
      </el-table-column>
      <el-table-column prop="sfb81" label="开单日期" width="120">
      </el-table-column>
      <el-table-column prop="gem02" label="成本中心" width="120">
      </el-table-column>
      <el-table-column prop="gen02" label="申请人" width="120">
      </el-table-column>
      <el-table-column prop="ima02" label="物料" width="160">
      </el-table-column>
      <el-table-column prop="sfb08" label="生产数量" width="120">
      </el-table-column>
      <el-table-column label="状态" width="120">
        <template slot-scope="scope">
          {{scope.row.sfb04 | formatSfb04 }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="90">
        <template slot-scope="scope">
          <el-button @click="searchSfaList(scope.row)" type="text">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total"
                   @current-change="handleCurrentChange" style=" margin-top: 10px;"
                   @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>

    <el-dialog :visible.sync="dialogTableVisible" width="1200px">
      <el-form label-position="left"  :model="documents.master">
        <el-row >
          <el-col :span="7" >
            <el-form-item label="工单号:">
              <span style="float: left;">{{ documents.master.sfb01 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.smydesc }}</span>
            </el-form-item>
            <el-form-item label="成本中心:">
              <span style="float: left;">{{ documents.master.sfb98 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px"> {{ documents.master.gem02 }}</span>
            </el-form-item>
            <el-form-item label="产品:">
              <span style="float: left;">{{ documents.master.sfb05 }}</span>
              <span style="margin-left: 10px; color: #8492a6; font-size: 13px"> {{ documents.master.ima02 }}</span>
            </el-form-item>
            <el-form-item label="产线:">
              {{documents.master.sfb102}}
            </el-form-item>
            <el-form-item label="工艺编号:">
              {{documents.master.sfb06}}
            </el-form-item>
          </el-col>
          <el-col :span="7"  :offset="1">
            <el-form-item label="工单类型:">
              {{documents.master.sfb02 | formatSfb02}}
            </el-form-item>
            <el-form-item label="开单日期:">
              {{ documents.master.sfb81 }}
            </el-form-item>
            <el-form-item label="人员:">
              <span style="float: left;">{{ documents.master.sfb44 }}</span>
              <span style=" margin-left: 10px; color: #8492a6; font-size: 13px">{{ documents.master.gen02 }}</span>
            </el-form-item>
            <el-form-item label="审核状态:">
              {{ documents.master.sfb87 | formatStatus }}
            </el-form-item>
            <el-form-item label="状况码:">
              {{ documents.master.sfb04 | formatSfb04 }}
            </el-form-item>

          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="生产数量:">
              {{documents.master.sfb08}}
            </el-form-item>
            <el-form-item label="已发数量:">
              {{documents.master.sfb081}}
            </el-form-item>
            <el-form-item label="完工数量:">
              {{documents.master.sfb09}}
            </el-form-item>
            <el-form-item label="报废数量:">
              {{documents.master.sfb12}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-table :data="documents.slave" border max-height="250px">
        <el-table-column prop="sfa27" label="BOM料号" width="120"></el-table-column>
        <el-table-column prop="sfa26" label="替代码" width="120">
          <template slot-scope="scope">
            {{scope.row.sfa26  | formatSfa26}}
          </template>
        </el-table-column>
        <el-table-column prop="sfa03" label="发料料号" width="120"></el-table-column>
        <el-table-column prop="ima02" label="品名" width="160"></el-table-column>
        <el-table-column prop="sfa12" label="单位" width="120"></el-table-column>
        <el-table-column prop="sfa05" label="应发数量" width="120"></el-table-column>
        <el-table-column prop="sfa06" label="已发数量" width="120"></el-table-column>
        <el-table-column  label="欠料" width="120">
          <template slot-scope="scope">
            {{scope.row.sfa05 - scope.row.sfa06}}
          </template>
        </el-table-column>
        <el-table-column prop="imd02" label="仓库" width="120"></el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre'
import {formatSfb02} from "@/filters/filters";

export default {
  name: 'sfb',
  data() {
    return {
      searchVo: {
        centre: 'WCTZ',
        code: null,
        startdate: null,
        enddate: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      sfbModify: {
        sfbArr: [],
        ddate: null,
        centre: '',
        flag: false
      },
      documentList: [],
      selectedDocumentList: [],
      documents: {
        master: {},
        slave: []
      },
      tableLoading: false,
      dialogTableVisible: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search(val = 1) {
      this.tableLoading = true;
      this.searchVo.currentPage = val;
      this.$http.post('/api/sfb/searchSfbListPageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result;
        this.searchVo.total = res.data.total;
      }).finally(() => this.tableLoading = false)
    },
    searchSfaList(row) {
      this.dialogTableVisible = true;
      this.documents.master = {...row};
      this.$http.post('/api/sfb/searchSfaList', {code: row.sfb01, centre: row.centre}).then(res => {
        this.documents.slave = res.data.result;
      })
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.searchVo.size = val;
      this.search(1);
    },
    handleSelectionChange(rows) {
      this.selectedDocumentList = rows;
    },
    modifyDate() {
      if (this.selectedDocumentList.length === 0) {
        this.$message.warning("请勾选要修改日期的工单");
        return
      } else if (this.sfbModify.ddate === null) {
        this.$message.warning("请选择要修改的日期");
        return
      }
      this.sfbModify.sfbArr = this.selectedDocumentList;
      this.$http.post('/api/sfb/modifySfbDate', this.sfbModify)
    },
    closeOut() {
      if (this.selectedDocumentList.length === 0) {
        this.$message.warning("请勾选要结案的工单");
        return;
      }
      this.$http.post('/api/sfb/closeOut', this.selectedDocumentList)
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