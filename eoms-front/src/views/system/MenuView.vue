<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card>
        <el-input v-model="filterText" placeholder="过滤关键词" clearable></el-input>

        <el-tree class="filter-tree" :data="fullMenu" :props="{label: 'name', value: 'id'}"
                 :filter-node-method="filterNode" ref="tree" :expand-on-click-node="false">
        </el-tree>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <el-form :model="menu" label-position="left">
          <el-form-item label="父标签:" label-width="60px">
            <el-cascader v-model="menu.pid" @change="change($event)" :options="fullMenu" :show-all-levels="true"
                         :props="{ checkStrictly: true,value: 'id', label: 'name' }"></el-cascader>
          </el-form-item>
          <el-form-item label="名称:" label-width="60px">
            <el-input v-model="menu.name"></el-input>
          </el-form-item>
          <el-form-item label="图标:" label-width="60px">
            <el-input v-model="menu.icon"></el-input>
          </el-form-item>
          <el-form-item label="路由:" label-width="60px">
            <el-input v-model="menu.path"></el-input>
          </el-form-item>
          <el-form-item label="路径:" label-width="60px">
            <el-input v-model="menu.component"></el-input>
          </el-form-item>
          <el-form-item label="排序:" label-width="60px">
            <el-slider v-model="menu.sort" :step="1" show-stops :max="10"></el-slider>
          </el-form-item>
          <el-form-item label-width="60px" style="float: right">
            <el-button type="primary" round @click="addMenu">新增</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "my_menu",
  data() {
    return {
      menu: {
        pid: 0,
        name: null,
        icon: null,
        sort: 0,
        path: null,
        component: null
      },
      fullMenu: [],
      filterText: null
    }
  },
  methods: {
    getAllMenu() {
      this.$http.get('/api/menu/getAllMenu').then(res => {
        this.fullMenu = [{id: 0, name: 'root', children: res.data.result}]
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    change(row) {
      this.menu.pid = row[row.length - 1]
    },
    async addMenu() {
      await this.$http.post('/api/menu/addMenu', this.menu);
      this.getAllMenu();
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  activated() {
    this.getAllMenu();
  }
}
</script>

<style scoped lang="scss">
.filter-tree {
  margin-top: 10px;

  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
}

</style>