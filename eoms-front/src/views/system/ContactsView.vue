<template>
  <div>
    <el-form :inline="true" :model="contact" class="demo-form-inline" size="mini">
      <el-form-item label="中心:">
        <selectedCentre v-model="contact.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="contact.name" placeholder="员工姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search" icon="el-icon-search" round>查询</el-button>
        <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="contacts" border style="width: 100%" max-height="450px" v-loading="table_loading"
              element-loading-spinner="el-icon-loading">
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="centre" label="中心">
      </el-table-column>
      <el-table-column prop="department" label="部门">
      </el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="phone" label="电话号码">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>

import {mapState} from 'vuex';
import selectedCentre from '@/components/selected-centre';

export default ({
  name: 'contact',
  data() {
    return {
      contact: {
        centre: 'WCTZ',
        name: ''
      },
      contacts: [],
      table_loading: false
    }
  },
  components: {
    selectedCentre
  },
  methods: {
    search() {
      this.table_loading = true
      this.$http.post('/api/contact/searchContact', this.contact).then(res => {
        this.contacts = res.data.result
      }).finally(() => this.table_loading = false);
    },
    reset() {
      this.contact = {
        centre: 'WCTZ',
        name: ''
      }
    },
  },
})
</script>

<style scoped></style>