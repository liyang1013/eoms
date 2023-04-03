<template>
  <div>
    <el-form :inline="true" :model="contact" class="demo-form-inline" size="mini">
      <el-form-item label="中心:">
        <selectedCentre v-model="contact.centre"></selectedCentre>
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="contact.name" placeholder="员工姓名"></el-input>
      </el-form-item>
    </el-form>

    <el-table :data="search()" border style="width: 100%" max-height="450px" >
      <el-table-column type="index" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="centre" label="中心">
      </el-table-column>
      <el-table-column prop="dep" label="部门">
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
      }
    }
  },
  components: {
    selectedCentre
  },
  computed: {
    ...mapState(['contacts'])
  },
  methods: {
    search() {
      return this.contacts.filter(item => {
        return ( item.centre === this.contact.centre
            && item.name.indexOf(this.contact.name) !== -1
        )
      })
    }
  },
})
</script>

<style scoped></style>