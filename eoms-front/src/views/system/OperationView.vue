<template>
  <div>
    <el-form :inline="true" :model="operation" class="demo-form-inline">
      <el-form-item label="编码:">
        <el-input v-model="operation.code_1" placeholder="作业编码" clearable></el-input>
      </el-form-item>
      <el-form-item label="名称:">
        <el-input v-model="operation.code_2" placeholder="作业名称" clearable></el-input>
      </el-form-item>
      <el-form-item label="详情:">
        <el-input v-model="operation.code_3" placeholder="简介" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" icon="el-icon-plus" round @click="drawer = true">新增</el-button>
      </el-form-item>
    </el-form>
    <el-alert title="作业录入: p_zz; 自定义报表: p_query" type="success" :closable="false">
    </el-alert>
    <el-table :data="operations" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="code" label="作业编码">
      </el-table-column>
      <el-table-column prop="name" label="作业名称">
      </el-table-column>
      <el-table-column prop="tips" label="详情">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click.native.prevent="deleteOperation(scope.row)" type="text">移除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="operation.total"
                   style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="operation.sizes"
                   :current-page.sync="operation.currentPage" :page-size="operation.size">
    </el-pagination>

    <!-- 新增作业 -->
    <el-drawer title="新增作业" :visible.sync="drawer" size="50%" :modal="false">
      <el-table :data="tempOperation">
        <el-table-column type="index" label="序号" width="60">
        </el-table-column>
        <el-table-column prop="code" label="作业编码">
          <template slot-scope="scope">
            <el-input v-model="scope.row.code"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="作业名称">
          <template slot-scope="scope">
            <el-input v-model="scope.row.name"></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="tips" label="详情">
          <template slot-scope="scope">
            <el-input type="textarea" :rows="1" v-model="scope.row.tips"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" :disabled="tempOperation.length === 1" @click="removeRow(scope)">移除</el-button>
            <el-button type="text" @click="addRow">新增</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style=" float: right;margin: 10px">
        <el-button type="primary" round icon="el-icon-upload2" @click="dialogTableVisible = true">参照导入</el-button>
        <el-button type="primary" round icon="el-icon-check" @click="addOperation">保存</el-button>
      </div>
    </el-drawer>

    <el-dialog :visible.sync="dialogTableVisible" width="1000px">

      <el-form :inline="true" :model="gaz" class="demo-form-inline">
        <el-form-item label="作业:">
          <el-input v-model="gaz.code_1" placeholder="作业编号/名称" clearable ></el-input>
        </el-form-item>
        <el-form-item label="目的:">
          <el-input v-model="gaz.code_2" placeholder="作业目的" clearable ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary"  icon="el-icon-search" @click="searchGazList(1)" round>查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="gazList" border max-height="350px" ref="gazTable">
        <el-table-column type="selection" width="60"></el-table-column>
        <el-table-column type="index" label="序号" width="60"></el-table-column>
        <el-table-column prop="gaz01" label="作业编码" width="120"></el-table-column>
        <el-table-column prop="gaz03" label="作业" width="200"></el-table-column>
        <el-table-column prop="gaz04" label="目的"></el-table-column>
      </el-table>
      <el-pagination background layout="total, sizes, prev, pager, next" :total="gaz.total"
                     style=" margin-top: 10px;"
                     @current-change="handleCurrentChangeGaz" @size-change="handleSizeChangeGaz" :page-sizes="gaz.sizes"
                     :current-page.sync="gaz.currentPage" :page-size="gaz.size">
      </el-pagination>
      <el-divider></el-divider>
      <div style="text-align: right;">
        <el-button type="primary" round @click="gaz2Operation">导入</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "operation",
  data() {
    return {
      operation: {
        code_1: null,
        code_2: null,
        code_3: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      gaz:{
        code_1: null,
        code_2: null,
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      gazList: [],
      operations: [],
      table_loading: false,
      drawer: false,
      dialogTableVisible: false,
      tempOperation: [
          {code: null, name: null, tips: null}
      ]
    }
  },
  methods: {
    search(val) {
      this.operation.currentPage = val;
      this.table_loading = true
      this.$http.post('/api/operation/searchOperationListPageHelper', this.operation).then(res => {
        this.operations = res.data.result;
        this.operation.total = res.data.total
      }).finally(() => this.table_loading = false);
    },
    searchGazList(val){
      this.gaz.currentPage = val;
      this.$http.post('/api/gaz/searchGazListPageHelper', this.gaz).then(res => {
        this.gazList = res.data.result;
        this.gaz.total = res.data.total
      });
    },
    deleteOperation(row){
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/api/operation/deletOperation',row).then(() =>{
          this.$message({type: 'success', message: '删除成功!'});
          this.search(1);
        })
      }).catch(() =>
        this.$message({type: 'info', message: '已取消删除'})
      );
    },
    addOperation(){
      this.$http.post('/api/operation/addOperation',this.tempOperation).then((res) =>{
        if(res.data.status === 200) {
          this.tempOperation = [{code: null, name: null, tips: null}]
        }
      });
    },
    gaz2Operation(){
      this.$refs.gazTable.selection.forEach(item =>{
        let flag = false
        this.tempOperation.forEach(e =>{
          if(item.gaz01 === e.code){
            e.name = item.gaz03
            e.tips = item.gaz04
            flag = true
          }
        })
        if(!flag) this.tempOperation.push({code: item.gaz01,name: item.gaz03,tips: item.gaz04})
      })
      if(this.$refs.gazTable.selection.length){
        this.tempOperation = this.tempOperation.filter(item =>item.code || item.name || item.tips)
        this.dialogTableVisible = false
      }
    },
    handleCurrentChange(val) {
      this.search(val);
    },
    handleSizeChange(val) {
      this.operation.size = val;
      this.search(1);
    },
    handleCurrentChangeGaz(val) {
      this.searchGazList(val);
    },
    handleSizeChangeGaz(val) {
      this.gaz.size = val;
      this.searchGazList(1);
    },
    removeRow(scope){
      this.tempOperation.splice(scope.$index,1);
    },
    addRow(){
      this.tempOperation.push({code: null, name: null, tips: null})
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
}
</style>