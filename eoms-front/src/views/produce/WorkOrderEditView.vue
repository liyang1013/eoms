<template>
    <div>
        <el-form ref="form" :model="form" size="mini" :inline="true" class="demo-form-inline"
            style="margin-left: 30px;padding-top: 30px;">
            <el-form-item label="中心：">
                <selectedCentre v-model="form.centre"></selectedCentre>
            </el-form-item>
            <el-form-item label="工单号：">
                <el-input v-model="form.sfb01" style="width: 160px;"></el-input>
            </el-form-item>
            <el-form-item label="开单日期:">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.startdate" value-format="yyyy-MM-dd"
                    style="width: 140px;"></el-date-picker>
                <span> - </span>
                <el-date-picker type="date" placeholder="选择日期" v-model="form.enddate" value-format="yyyy-MM-dd"
                    style="width: 140px;"></el-date-picker>
            </el-form-item>
            <el-form-item label="物料：">
                <el-input v-model="form.sfb05" style="width: 160px;"></el-input>
            </el-form-item>
            <el-form-item label="发料单号：">
                <el-input v-model="form.sfp01" style="width: 160px;"></el-input>
            </el-form-item>
            <el-form-item label="入库单号：">
                <el-input v-model="form.sfu01" style="width: 160px;"></el-input>
            </el-form-item>
            <el-button size="mini" type="primary" @click="searchSfb()" round>查询</el-button>
            <br>
            <el-form-item label="修改工单日期：">
                <el-date-picker type="date" placeholder="选择日期" v-model="temp.ddate" value-format="yyyy-MM-dd"
                    style="width: 140px;"></el-date-picker>

                <el-switch v-model="temp.flag" active-text="改大" inactive-text="改小"></el-switch>

                <el-button size="mini" type="danger" @click="modifyDate()" round style="margin-left: 10px;"
                    :loading="modifyLoad">修改</el-button>
            </el-form-item>

        </el-form>
        <el-divider></el-divider>
        <el-table :data="sfbArr" size="mini" stripe border :header-cell-style="{ 'text-align': 'center' }"
            :cell-style="{ 'text-align': 'center' }" max-height="500px" tooltip-effect="dark"
            style="width: 1200px; margin: 0 auto;" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="sfb01" label="工单号">
            </el-table-column>
            <el-table-column prop="sfb81" label="开单日期">
            </el-table-column>
            <el-table-column prop="ima02" label="物料">
            </el-table-column>
            <el-table-column prop="sfb04" label="状态">
            </el-table-column>
            <el-table-column prop="gem02" label="部门">
            </el-table-column>
        </el-table>
        <el-pagination background layout="prev, pager, next" :total="form.total"
            style="text-align: center; margin-top: 10px;" @current-change="handleCurrentChange"
            :current-page.sync="form.currentPage" :page-size="form.size" :hide-on-single-page="true">
        </el-pagination>
    </div>
</template>

<script>
import selectedCentre from '@/components/selected-centre.vue'

export default {
    name: 'workOrderEdit',
    data() {
        return {
            form: {
                centre: 'WCTZ',
                sfb01: '',
                startdate: null,
                enddate: null,
                sfb05: '',
                sfp01: '',
                sfu01: '',
                currentPage: 1,
                size: 20,
                total: 0
            },
            sfbArr: [],
            temp: {
                sfbArr: [],
                ddate: null,
                centre: '',
                flag: false
            },
            modifyLoad: false
        }
    },
    components: {
        selectedCentre
    },
    methods: {
        searchSfb(val = 1) {
            this.form.currentPage = val;
            this.$http.post('/api/sfb/sfbArr', this.form).then(res => { this.sfbArr = res.data.result; this.form.total = res.data.total; })
        },
        handleCurrentChange(val) {
            this.searchSfb(val);
        },
        handleSelectionChange(rows) {
            this.temp.sfbArr = rows;
        },
        modifyDate() {
            if (this.temp.sfbArr.length === 0) {
                this.$message.warning("请勾选要修改的工单");
                return
            } else if (this.temp.ddate === null) {
                this.$message.warning("请选择要修改的日期");
                return
            }
            this.temp.centre = this.form.centre
            this.modifyLoad = true;
            this.$http.post('/api/sfb/modifyDate', this.temp).then().finally(() => this.modifyLoad = false)

        }
    }

}
</script>
<style lang="sass" scoped>

</style>