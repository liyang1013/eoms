<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card>
        <el-form :inline="true" :model="menu" class="demo-form-inline">
          <el-form-item label="名称:">
            <el-input v-model="menu.name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="search" icon="el-icon-search" round>查询</el-button>
            <el-button :type=" isDraggable ? 'success' : 'primary'" @click="editMenu"
                       :icon="isDraggable ? 'el-icon-document-checked' : 'el-icon-edit-outline' " round>
              {{ isDraggable ? '保存' : '编辑' }}
            </el-button>
          </el-form-item>
        </el-form>
        <el-tree
            class="filter-tree"
            :data="data"
            :props="defaultProps"
            default-expand-all
            show-checkbox
            :filter-node-method="filterNode"
            :draggable="isDraggable"
            ref="tree">
        </el-tree>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <el-form :model="menu" label-position="left">
          <el-form-item label="父标签:" label-width="60px">
            <el-cascader :options="options" :props="{ checkStrictly: true,value: 'id', label: 'name',lazy: true,lazyLoad: getChildrenMenu }" clearable></el-cascader>
          </el-form-item>
          <el-form-item label="名称:" label-width="60px">
            <el-input v-model="menu.name"></el-input>
          </el-form-item>
          <el-form-item label="图标:" label-width="60px">
            <el-input v-model="menu.icon"></el-input>
          </el-form-item>
          <el-form-item label="path:" label-width="60px">
            <el-input v-model="menu.path"></el-input>
          </el-form-item>
          <el-form-item label="组件:" label-width="60px">
            <el-input v-model="menu.component"></el-input>
          </el-form-item>
          <el-form-item label="排序:" label-width="60px">
            <el-slider v-model="menu.sort" :step="1" show-stops :max="10"></el-slider>
          </el-form-item>
          <el-form-item label-width="60px" style="float: right">
            <el-button type="primary" round>新增</el-button>
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
        name: null,
        icon: null,
        sort: 0,
        path: null,
        component: null
      },
      data: [
        {
          id: 1,
          label: '一级 1',
          children: [{
            id: 4,
            label: '二级 1-1',
            children: [{
              id: 9,
              label: '三级 1-1-1'
            }, {
              id: 10,
              label: '三级 1-1-2'
            }]
          }]
        }, {
          id: 2,
          label: '一级 2',
          children: [{
            id: 5,
            label: '二级 2-1'
          }, {
            id: 6,
            label: '二级 2-2'
          }]
        }, {
          id: 3,
          label: '一级 3',
          children: [{
            id: 7,
            label: '二级 3-1'
          }, {
            id: 8,
            label: '二级 3-2'
          }]
        }],
      options: [{name: '根目录', id: '0'}],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      isDraggable: false
    }
  },
  methods: {
    filterNode() {
    },
    search() {
      this.$http.get('/api/menu/getAllMenu').then(res => this.data = res.data.result)
    },
    editMenu() {
      if (!this.isDraggable) {
        this.isDraggable = true;
      } else {
        this.isDraggable = false;
      }
    },
    getChildrenMenu(node, resolve){
      this.$http.get('/api/menu/getChildrenMenu?pid='+node.value).then(res => resolve([res.data.result]))

    }
  }
}
</script>

<style scoped>

</style>