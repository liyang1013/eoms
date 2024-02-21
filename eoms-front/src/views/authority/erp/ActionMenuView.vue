<template>
  <el-collapse v-model="activeNames" accordion>
    <el-collapse-item v-for="(item,index) in action" :title="item.name" :name="item.code" :key="item.code">

      <el-tag @close="closeTag(item,tag)" type="success" effect="dark" size="medium" closable v-for="tag in item.tags">
        {{ tag }}
      </el-tag>
      <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="mini"
          @keyup.enter.native="handleInputConfirm(item)"
          @blur="handleInputConfirm(item)"
      >
      </el-input>
      <el-button else-if class="button-new-tag" type="success"  plain size="small" @click="showInput(index)">+ New Tag</el-button>
      <el-button round type="danger" style="float: right; margin: 5px" @click="saveAction(item)">保存</el-button>
    </el-collapse-item>
  </el-collapse>
</template>

<script>
export default {
  name: "ActionMenuView",
  data() {
    return {
      action: [],
      activeNames: null,
      inputValue: null,
      inputVisible: false
    }
  },
  methods: {
    closeTag(item, tag) {
      item.tags = item.tags.filter(e => e !== tag);
    },
    handleInputConfirm(item) {
      if(this.inputValue) {
        if(item.tags.filter(e => e === this.inputValue).length){
          this.$message.info('重复');
        }else{
          item.tags.push(this.inputValue)
        }
        this.inputValue = '';
      }
      this.inputVisible = false;
    },
    showInput(index) {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput[index].$refs.input.focus();
      });
    },
    saveAction(item){
      this.$http.post('/api/action/saveAction',item)
    },
    initAction(){
      this.$http.get('/api/action/initAction').then(res => {
        this.action = res.data.result;
      })
    }
  },
  mounted() {
    this.initAction();
  }

}
</script>
<style scoped>
::v-deep .el-collapse-item__content {
  padding-bottom: 10px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 30px;
  line-height: 28px;
  padding-top: 0;
  padding-bottom: 0;
  margin-top: 5px;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;

}

</style>