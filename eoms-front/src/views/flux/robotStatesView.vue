<template>
  <el-row :gutter="10">
    <el-col :span="8" v-for="(item,index) in status" :key="index">
      <el-card class="car" >
        <div slot="header">
          <p>
            设备编号: {{item.id}} &nbsp&nbsp
            设备IP: {{ item.ip }} &nbsp&nbsp
            设备类型: {{ item.agvType }} &nbsp&nbsp
          </p>
        </div>
        <div>
          <p style="overflow: hidden" class="span_50">
            <span>当前坐标：{{item.position.x}},{{item.position.y}}</span>
            <span>当前角度: {{item.position.angle + '°'}} </span>
          </p>
          <br>
          <p style="overflow: hidden" class="span_50">
            <span>
              目标坐标：{{item.target.x}},{{item.target.y}}
            </span>
            <span>
              目标角度: {{item.target.angle + '°'}}
            </span>
          </p><br>
          <p style="overflow: hidden" class="span_33">
            <span>
              设备启用：{{item.enable}}
            </span>
            <span>
              所在楼层：{{item.position.floorId}}
            </span>
            <span>
               所在地图：{{item.mapId}}
            </span>
          </p><br>
        </div>
        <div class="status-div">
          <span >
            <p>任务状态</p>
            <p>{{ item.taskState }}</p>
          </span>
          <span>
            <p>设备状态</p>
            <p>{{ item.agvState }}</p>
            </span>
          <span>
            <p>负载情况</p>
            <p>{{ item.loaded }}</p>
          </span>
          <span>
            <p>电池电量</p>
            <p>{{ item.battery }}</p>
            </span>
          <span>
            <p>是否在线</p>
            <p>{{ item.online }}</p>
          </span>
        </div>
      </el-card>
    </el-col>
  </el-row>

</template>

<script>

export default {
  name: "robotStates",
  data(){
    return {
      status: []
    }
  },
  methods: {
    timer(){
      this.$http.post('http://172.17.200.154:8090/rest/robots/states',{params: {id: []}}).then(res => this.status = res.data.data)
    }
  },
  activated(){
    this.start = setInterval(() => {
      this.timer()
    }, 1000)
  },
  deactivated(){
    clearInterval(this.start)
  }
}
</script>

<style scoped lang="scss">
.car{
  margin-bottom: 20px;
  ::v-deep .el-card__header{
    background-color: #909399;
    padding: 10px;
    p{
      font-size: 14px;
      color: white;
    }
  };
  ::v-deep .el-card__body{
    background-color: #DCDFE6;
    .span_33{
      span{
        float: left;
        width: 33.3%;
        text-align: center;
        font-size: 14px;
      }
    }
    .span_50{
      span{
        float: left;
        width: 50%;
        text-align: center;
        font-size: 14px;
      }
    }
  }
  .status-div{
    overflow: hidden;
    span{
      width: 20%;
      font-size: 14px;
      float: left;
      text-align: center;
    }
  }
};
</style>