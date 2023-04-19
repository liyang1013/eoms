<template>
  <div class="page">
    <el-autocomplete placeholder="请输入工单单号" v-model="sfb01" :fetch-suggestions="querySearchAsync"
                     @select="handleSelect">
      <template slot-scope="{ item }">
        <span style="float: left">{{ item.sfb01 }}</span>
        <span style="float: right; color: #8492a6; font-size: 13px">{{ item.ima02 }}</span>
      </template>
      <selectedCentre v-model="centre" slot="prepend" class="centre_select"></selectedCentre>
      <el-button slot="append" icon="el-icon-search" @click="search" :loading="search_loading">搜索</el-button>
    </el-autocomplete>

    <el-timeline>
      <el-timeline-item :type="product.sfb?.sfb01 != null ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.sfb?.sfb81 }}</div>
        <h4>开立(asfi301)</h4>
        <el-table :data="[product.sfb]" stripe>
          <el-table-column prop="sfb01" label="工单单号" width="120">
          </el-table-column>
          <el-table-column prop="sfb81" label="开单日期" width="120">
          </el-table-column>
          <el-table-column prop="sfb13" label="预计开工日期" width="120">
          </el-table-column>
          <el-table-column prop="sfb15" label="预计完工日期" width="120">
          </el-table-column>
          <el-table-column prop="sfb25" label="实际开工日期" width="120">
          </el-table-column>
          <el-table-column prop="sfb36" label="发料结束日期" width="120">
          </el-table-column>
          <el-table-column prop="sfb38" label="结案日期" width="120">
          </el-table-column>
        </el-table>
      </el-timeline-item>
      <el-timeline-item :type="product.sfp.length > 0 ? 'success' : ''" size="large">
        <div class="timeline-left">{{ product.sfp[0]?.sfp02 }}</div>
        <h4>发料(asfi510)</h4>
        <el-table :data="product.sfp" size="mini" stripe :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="sfp01" label="发料单号" width="120">
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
        <el-table :data="product.shb" size="mini" stripe :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="shb01" label="报工单号" width="120">
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
        <el-table :data="product.qcf" size="mini" stripe :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="qcf01" label="FQC单号" width="120">
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
        <el-table :data="product.sfu" size="mini" stripe :header-cell-style="{ 'text-align': 'center' }"
                  :cell-style="{ 'text-align': 'center' }">
          <el-table-column prop="sfu01" label="入库单号" width="120">
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
  name: 'ProduceView',
  data() {
    return {
      sfb01: '',
      centre: 'WCTZ',
      product: {
        sfb: {},
        sfp: [],
        shb: [],
        qcf: [],
        sfu: []
      },
      search_loading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search() {
      this.search_loading = true;
      this.$http.get('/api/sfb/sfbProcess?sfb01=' + this.sfb01 + '&centre=' + this.centre)
          .then(res => this.product = res.data.status === 200 ? res.data.result : []).finally(() => this.search_loading = false)

    },
    handleSelect(val) {
      this.sfb01 = val.sfb01;
      this.search();
    },
    querySearchAsync(queryStr, cb) {
      if (queryStr.trim()) {
        this.$http.get('/api/sfb/sfb01Arr?queryStr=' + queryStr + '&centre=' + this.centre).then(res => cb(res.data.result))
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
    width: 60%;
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
