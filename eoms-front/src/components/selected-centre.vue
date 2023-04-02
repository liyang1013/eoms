<!-- 通用中心下拉框组件 -->
<template>
    <el-select v-model="tempval" @change="$emit('change', $event)" placeholder="选择中心">
        <el-option v-for="item in centres" :key="item.value" :label="item.label" :value="item.value">
            <span style="float: left">{{ item.label }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
        </el-option>
    </el-select>
</template>

<script>
import { mapState } from 'vuex'
export default {
    name: 'selectedCentre',
    data() {
        return {
            tempval: this.centre
        }
    },
    props: {
        centre: {
            type: String
        }
    },
    model: {
        prop: 'centre',
        event: 'change'
    },
    computed: {
        ...mapState(['centres'])
    }
}
</script>

<style lang="scss" scoped></style>