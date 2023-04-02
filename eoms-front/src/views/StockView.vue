<template>
   <el-container class="img-container">
        <el-header class="img-header">
            <el-autocomplete placeholder="请输入物料编号" v-model="ima01" :fetch-suggestions="querySearchAsync"
                @select="handleSelect">
                <template slot-scope="{ item }">
                    <span style="float: left">{{ item.ima01 }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.ima02 }}</span>
                </template>
                <selectedCentre v-model="centre" slot="prepend"  class="centre_select">
           
                </selectedCentre>
                <el-button slot="append" icon="el-icon-search" @click="search" >搜索</el-button>
            </el-autocomplete>
        </el-header>
        <el-main class="img-main">
            <el-table :data="img" size="mini" stripe border :header-cell-style="{ 'text-align': 'center' }"
                :cell-style="{ 'text-align': 'center' }" max-height="550px">
                <el-table-column prop="img01" label="物料编码">
                </el-table-column>
                <el-table-column prop="ima02" label="物料">
                </el-table-column>
                <el-table-column prop="img03" label="储位">
                </el-table-column>
                <el-table-column prop="img04" label="批号">
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
            </el-table>
        </el-main>
    </el-container>
</template>

<script>

import selectedCentre from '@/components/selected-centre'

export default {
    name: 'StockView',
    data() {
        return {
            ima01: '',
            centre: 'WCTZ',
            img: []
        }
    },
    components:{
        selectedCentre
    },
    methods:{
        search() {
           
            this.$http.get('/api/ima/imgArr?ima01=' + this.ima01 + '&centre=' + this.centre)
            .then(res => this.img = res.data.status === 200 ? res.data.result : [])
            
        },
        handleSelect(val) {
            this.ima01 = val.ima01;
            this.search();
        },
        querySearchAsync(queryStr, cb) {
            if (queryStr.trim()) {
                this.$http.get('/api/ima/imaArr?queryStr=' + queryStr + '&centre=' + this.centre).then(res => cb(res.data.result))
            }
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
    .img-main{
        margin-top: 10px;
    }
}
</style>