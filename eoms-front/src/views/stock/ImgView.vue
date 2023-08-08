<template>
  <div>
    <el-form :inline="true" :model="img" class="demo-form-inline">
      <el-form-item label="中心:">
        <selectedCentre v-model="img.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="仓库:">
        <selectedImd v-model="img.imd" :centre="img.centre"></selectedImd>
      </el-form-item>
      <el-form-item label="料件:">
        <selectedIma v-model="img.ima" :centre="img.centre" ></selectedIma>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
        <el-button type="danger"  icon="el-icon-set-up" @click="repairImg" round>现存量修复</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="imgList" stripe border max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column prop="img01" label="物料编码">
      </el-table-column>
      <el-table-column prop="ima02" label="物料">
      </el-table-column>
      <el-table-column prop="img02" label="仓库编码">
      </el-table-column>
      <el-table-column prop="imd02" label="仓库">
      </el-table-column>
      <el-table-column prop="img23" label="是否可用">
      </el-table-column>
      <el-table-column prop="img10" label="库存数量">
      </el-table-column>
      <el-table-column prop="img09" label="库存单位">
      </el-table-column>
      <el-table-column prop="img03" label="储位">
      </el-table-column>
      <el-table-column prop="img04" label="批号">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="img.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="img.sizes"
                   :current-page.sync="img.currentPage" :page-size="img.size">
    </el-pagination>
  </div>
</template>

<script>

import selectedCentre from '@/components/selected/selected-centre.vue'
import selectedImd from "@/components/selected/selected-imd.vue";
import selectedIma from "@/components/selected/selected-ima.vue";

export default {
  name: 'StockView',
  data() {
    return {
      img: {
        ima: null,
        imd: null,
        centre: 'WCTZ',
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      imgList: [],
      table_loading: false
    }
  },
  components: {
    selectedCentre,
    selectedImd,
    selectedIma
  },
  methods: {
    search(val) {
      this.img.currentPage = val;
      this.table_loading = true;
      this.$http.post('/api/img/searchImgListPageHelper', this.img)
          .then(res => {this.imgList = res.data.result; this.img.total = res.data.total;}).finally(() => this.table_loading = false)
    },
    repairImg(){
      this.$http.post('/api/img/repairImg',this.img);
    },
    handleSelect(val) {
      this.ima01 = val.ima01;
      this.search();
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.img.size = val;
      this.search(1);
    },
  }

}

</script>

<style lang="scss" scoped>
.img-container {
  // width: 86%;
  margin: 0 auto;

  .img-header {
    text-align: center;
    padding: 10px;

    .search-btn {
      margin-left: 60px;
    }

    .el-autocomplete {
      width: 60%;

      .centre_select {
        width: 140px;
      }
    }
  }

  .img-main {
    margin-top: 10px;
  }
}
</style>