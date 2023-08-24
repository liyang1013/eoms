<template>
  <div>
    <el-form :inline="true" :model="searchVo" class="demo-form-inline" >
      <el-form-item style="float:right; ">
        <el-button type="primary" @click="search()" icon="el-icon-search" round>查询</el-button>
      </el-form-item>
      <el-form-item label="小车编号:">
        <el-select v-model="searchVo.carid" placeholder="请选择小车"  multiple collapse-tags style="width: 140px">
          <el-option v-for="item in 11" :key="item" :label="item + '号小车'" :value="item" ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务编号:">
        <el-input v-model="searchVo.taskid" style="width: 160px;" clearable placeholder="任务编号"></el-input>
      </el-form-item>
      <el-form-item label="任务类型:">
        <el-select v-model="searchVo.tasktype" placeholder="请选择任务类型"  multiple collapse-tags >
          <el-option v-for="item in taskType" :key="item.value" :label="item.label" :value="item.value" >
            <span style="float: left">{{ item.value }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.label }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="WMS任务类型:">
        <el-select v-model="searchVo.wmstasktype" placeholder="请选择WMS任务类型"  multiple collapse-tags >
          <el-option v-for="item in wmsTaskType" :key="item.value" :label="item.label" :value="item.value" >
            <span style="float: left">{{ item.value }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.label }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="日期:">
        <el-date-picker v-model="searchVo.startdate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择开始日期" style="width: 160px"></el-date-picker>
        至
        <el-date-picker v-model="searchVo.enddate" format="yyyy年MM月dd日" value-format="yyyy-MM-dd"
                        placeholder="选择结束日期" style="width: 160px">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <el-alert title="小车任务时间分析表" type="success" :closable="false">
    </el-alert>
    <el-table :data="documentList" border style="width: 100%" max-height="420px" v-loading="tableLoading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="taskid" label="任务编号">
      </el-table-column>
      <el-table-column prop="ddate" label="日期">
      </el-table-column>
      <el-table-column prop="carid" label="小车编号">
      </el-table-column>
      <el-table-column prop="tasktype" label="任务类型">
      </el-table-column>
      <el-table-column prop="wmstasktype" label="wms任务类型">
      </el-table-column>
      <el-table-column prop="startdate" label="任务开始时间">
      </el-table-column>
      <el-table-column prop="enddate" label="任务结束时间">
      </el-table-column>
      <el-table-column prop="tasktime" label="任务耗时">
      </el-table-column>
    </el-table>
    <el-pagination background layout="total, sizes, prev, pager, next" :total="searchVo.total" style=" margin-top: 10px;"
                   @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="searchVo.sizes"
                   :current-page.sync="searchVo.currentPage" :page-size="searchVo.size">
    </el-pagination>
  </div>
</template>

<script>

export default {
  name: "taskTime",
  data() {
    return {
      searchVo: {
        carid: [1],
        taskid: null,
        tasktype: [],
        wmstasktype: [],
        startdate: new Date(),
        enddate: new Date(),
        currentPage: 1,
        sizes: [20, 50, 100, 500],
        size: 20,
        total: 0
      },
      taskType: [
        {value: 'ASRS_IN',label: '入库'},
        {value: 'ASRS_MOVE',label: '移动'},
        {value: 'ASRS_OUT',label: '出库'},
        {value: 'ASRS_PICK',label: '拣选'},
        {value: 'CAR_HC',label: '换层'}
      ],
      wmsTaskType: [
        {value: 'OT',label: '指定下架'},
        {value: 'PA',label: '入库上架'},
        {value: 'PI',label: '母托盘入库'},
        {value: 'PK',label: '出库下架'},
        {value: 'PO',label: '母托盘出库'},
        {value: 'PT',label: '指定上架'},
        {value: 'RT',label: '回库'},
        {value: 'RZ',label: '回中转库位'},
        {value: 'TI',label: '移入'},
        {value: 'TO',label: '移出'}
      ],
      documentList: [],
      tableLoading: false
    }
  },
  methods: {
    search(val = 1) {
      this.searchVo.currentPage = val;
      this.tableLoading = true
      this.$http.post('/api/wcs/taskTimePageHelper', this.searchVo).then(res => {
        this.documentList = res.data.result
        this.searchVo.total = res.data.total
      }).finally(() => this.tableLoading = false);
    },
    handleCurrentChange(val){
      this.search(val);
    },
    handleSizeChange(val){
      this.searchVo.size = val;
      this.search(1);
    }
  }
}
</script>
