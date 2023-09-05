<template>
  <div class="page">
    <el-autocomplete size="small" placeholder="请输入工单单号" v-model="searchVo.code" :fetch-suggestions="querySearchAsync"
                     @select="handleSelect">
      <template slot-scope="{ item }">
        <span style="float: left">{{ item.sfb01 }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.ima02 }}</span>
      </template>
      <selectedCentre v-model="searchVo.centre" slot="prepend" class="centre_select" key="produce"></selectedCentre>
      <el-button slot="append" icon="el-icon-search" @click="search" >搜索</el-button>
    </el-autocomplete>

    <el-timeline>
      <el-timeline-item :type="product.sfb?.sfb01 != null ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.sfb?.sfb81 }}</div>
        <h4>工单(asfi301)</h4>
        <el-table :data="[product.sfb]" stripe>
          <el-table-column prop="sfb01" label="工单单号" width="120">
          </el-table-column>
          <el-table-column prop="sfb08" label="生产数量" width="90">
          </el-table-column>
          <el-table-column prop="sfb081" label="已发数量" width="90">
          </el-table-column>
          <el-table-column prop="sfb09" label="完工数量" width="90">
          </el-table-column>
          <el-table-column prop="sfb81" label="开单日期" width="100">
          </el-table-column>
          <el-table-column prop="sfb13" label="预计开工日期" width="100">
          </el-table-column>
          <el-table-column prop="sfb15" label="预计完工日期" width="100">
          </el-table-column>
          <el-table-column prop="sfb25" label="实际开工日期" width="100">
          </el-table-column>
          <el-table-column prop="sfb36" label="发料结束日期" width="100">
          </el-table-column>
          <el-table-column prop="sfb38" label="结案日期" width="100">
          </el-table-column>
        </el-table>
      </el-timeline-item>
      <el-timeline-item :type="product.sfp.length > 0 ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.sfp[0]?.sfp02 }}</div>
        <h4>发料(asfi510)</h4>
        <el-table :data="product.sfp" stripe>
          <el-table-column prop="sfp01" label="发料单号" width="120">
          </el-table-column>
          <el-table-column prop="tlf907" label="发料类型" width="120">
          </el-table-column>
          <el-table-column prop="sfq03" label="发料数量" width="120">
          </el-table-column>
          <el-table-column prop="sfp02" label="录入日期" width="120">
          </el-table-column>
          <el-table-column prop="sfp03" label="扣账日期" width="120">
          </el-table-column>
          <el-table-column prop="tlf06" label="异动扣账日期" width="120">
          </el-table-column>
        </el-table>
      </el-timeline-item>
      <el-timeline-item :type="product.shb.length > 0 ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.shb[0]?.shb02 }}</div>
        <h4>报工(asft700)</h4>
        <el-table :data="product.shb" stripe>
          <el-table-column prop="shb01" label="报工单号" width="120">
          </el-table-column>
          <el-table-column prop="shb111" label="报工数" width="120">
          </el-table-column>
          <el-table-column prop="shb02" label="开工日期" width="120">
          </el-table-column>
          <el-table-column prop="shb03" label="报工日期" width="120">
          </el-table-column>
          <el-table-column prop="shb32" label="审核日期" width="120">
          </el-table-column>
        </el-table>
      </el-timeline-item>
      <el-timeline-item :type="product.qcf.length > 0 ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.qcf[0]?.qcf04 }}</div>
        <h4>FQC(aqct410)</h4>
        <el-table :data="product.qcf" stripe >
          <el-table-column prop="qcf01" label="FQC单号" width="120">
          </el-table-column>
          <el-table-column prop="qcf22" label="送检量" width="120">
          </el-table-column>
          <el-table-column prop="qcf091" label="合格量" width="120">
          </el-table-column>
          <el-table-column prop="qcf04" label="检验日期" width="120">
          </el-table-column>
          <el-table-column prop="qcf15" label="审核日期" width="120">
          </el-table-column>
        </el-table>
      </el-timeline-item>
      <el-timeline-item :type="product.sfu.length > 0 ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.sfu[0]?.sfu14 }}</div>
        <h4>入库(asft620)</h4>
        <el-table :data="product.sfu" stripe >
          <el-table-column prop="sfu01" label="入库单号" width="120">
          </el-table-column>
          <el-table-column prop="tlf907" label="入库类型" width="120">
          </el-table-column>
          <el-table-column prop="sfv09" label="入库量" width="120">
          </el-table-column>
          <el-table-column prop="sfu14" label="录入日期" width="120">
          </el-table-column>
          <el-table-column prop="sfu02" label="入库日期" width="120">
          </el-table-column>
          <el-table-column prop="tlf06" label="异动扣账日期" width="120">
          </el-table-column>
        </el-table>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import selectedCentre from '@/components/selected/selected-centre.vue'

export default {
  name: 'sfbStatus',
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
      product: {
        sfb: {},
        sfp: [],
        shb: [],
        qcf: [],
        sfu: []
      }
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search() {
      this.$http.post('/api/sfb/searchSfbStatus',this.searchVo)
          .then(res => this.product = res.data.status === 200 ? res.data.result : [])
    },
    handleSelect(val) {
      this.searchVo.code = val.sfb01;
      this.search();
    },
    querySearchAsync(queryStr, cb) {
      if (queryStr.trim()) {
        this.$http.post('/api/sfb/searchSfbList',{code: queryStr ,centre:  this.searchVo.centre}).then(res => cb(res.data.result))
      }
    }
  }
}
</script>

<style lang="scss" scoped>

.page {
  padding-left: 100px;

  .search-btn {
    margin-left: 60px;
  }

  .el-autocomplete {
    width: 80%;
    margin-bottom: 30px;
    text-align: center;

    .centre_select {
      width: 140px;
    }
  }

  .timeline-left {
    color: #409EFF;
    position: absolute;
    left: -100px;
    top: 3px;
  }
}

</style>
