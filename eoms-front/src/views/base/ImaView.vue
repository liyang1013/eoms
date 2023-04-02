<template>
    <div>
        <el-form :inline="true" :model="ima" class="demo-form-inline" size="mini">
            <el-form-item label="中心:">
                <selectedCentre v-model="ima.centre"></selectedCentre>
            </el-form-item>
            <el-form-item label="料件:">
                <el-input v-model="ima.code_1" placeholder="料件编码/名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="search(1)" icon="el-icon-search" round>查询</el-button>
                <el-button type="primary" @click="reset" icon="el-icon-refresh-right" round>重置</el-button>
            </el-form-item>
        </el-form>
        <el-alert title="料件基本资料: aimi100; " type="success" class="tip_alert" :closable="false">
        </el-alert>
        <el-table :data="imaList" border style="width: 100%" max-height="450px" v-loading="table_loading"
            element-loading-spinner="el-icon-loading">
            <el-table-column type="index" label="序号" width="60">
            </el-table-column>
            <el-table-column prop="ima01" label="料件编码" width="160">
            </el-table-column>
            <el-table-column prop="ima02" label="品名" width="160">
            </el-table-column>
            <el-table-column prop="ima021" label="规格" width="240">
            </el-table-column>
            <el-table-column prop="imz02" label="料件大类" width="120">
            </el-table-column>
            <el-table-column prop="ima08" label="料件类型" width="120">
                <template slot-scope="scope">
                    {{ scope.row.ima08 | formatImaType }}
                </template>
            </el-table-column>
            <el-table-column prop="ima1010" label="审核状态" width="90">
                <template slot-scope="scope">
                    {{ scope.row.ima1010 | formatSignoff }}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="90">
                <template >
                    <el-button type="text">查看</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination background layout="total, sizes, prev, pager, next" :total="ima.total" style=" margin-top: 10px;"
            @current-change="handleCurrentChange" @size-change="handleSizeChange" :page-sizes="ima.sizes"
            :current-page.sync="ima.currentPage" :page-size="ima.size">
        </el-pagination>

    </div>
</template>

<script>
import selectedCentre from '@/components/selected-centre';

export default {
    name: 'ima',
    data() {
        return {
            ima: {
                code_1: null,
                centre: 'WCTZ',
                currentPage: 1,
                sizes: [20, 50, 100, 500],
                size: 20,
                total: 0
            },
            imaList: [],
            table_loading: false,
        }
    },
    components: {
        selectedCentre
    },
    methods: {
        search(val) {
            this.ima.currentPage = val;
            this.table_loading = true;
            this.$http.post('/api/ima/searchImaList', this.ima).then(res => {
                this.imaList = res.data.result
                this.ima.total = res.data.total;
            }).finally(() => this.table_loading = false);

        },
        reset() {
            this.ima.code_1 = null;
            this.ima.centre = 'WCTZ';
        },
        handleCurrentChange(val) {
            this.search(val);
        },
        handleSizeChange(val) {
            this.ima.size = val;
            this.search(1);
        }
    }
}
</script>

<style lang="scss" scoped>
.tip_alert {
    margin-bottom: 15px;
}
</style>