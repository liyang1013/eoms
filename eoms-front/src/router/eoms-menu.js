export const menu = [
    {
        name: '首页',
        path: 'home',
        icon: 'el-icon-s-platform',
        component: () => import('@/views/HomeView')
    },
    {
        path: 'purchase',
        name: '采购管理',
        icon: 'el-icon-goods',
        component: null,
        children: [
            {
                path: 'pma',
                name: '付款方式',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmaView')
            },
            {
                path: 'pmc',
                name: '供应商档案',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmcView')
            },
            {
                path: 'pmh',
                name: '供应商价格',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmhView')
            },
            {
                path: 'pmi',
                name: '核价单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmiView')
            },
            {
                path: 'pmk',
                name: '请购单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmkView')
            },
            {
                path: 'pmm',
                name: '采购单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmmView')
            },
            {
                path: 'rva',
                name: '收货单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/RvaView')
            },
            {
                path: 'rvu',
                name: '入库单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/RvuView')
            }
        ]
    },
    {
        path: 'produce',
        name: '生产管理',
        icon: 'el-icon-position',
        component: null,
        children: [
            {
                path: 'sfb',
                name: '工单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/SfbView')
            },
            {
                path: 'sfp',
                name: '发料单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/SfpView')
            },
            {
                path: 'shb',
                name: '报工单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/ShbView')
            },
            {
                path: 'qcf',
                name: 'FQC',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/QcfView')
            },
            {
                path: 'sfu',
                name: '入库单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/SfuView')
            },
            {
                path: 'sfbStatus',
                name: '工单状况',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/SfbStatusView.vue')
            }
        ]
    },
    {
        path: 'sale',
        name: '销售管理',
        icon: 'el-icon-box',
        component: null,
        children: [
            {
                path: 'occ',
                name: '客户档案',
                icon: 'el-icon-tickets',
                component: () => import('@/views/sale/OccView')
            },
            {
                path: 'oga',
                name: '销售出货单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/sale/OgaView')
            },
            {
                path: 'oha',
                name: '销售退货单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/sale/OhaView')
            }
        ]
    },
    {
        path: 'stock',
        name: '库存管理',
        icon: 'el-icon-s-home',
        component: null,
        children: [
            {
                path: 'ima',
                name: '料件信息',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/ImaView')
            },
            {
                path: 'currStock',
                name: '现存量查询',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/ImgView')
            },
            {
                path: 'EWStockGap',
                name: 'EW库存差异',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/EWStockGapView')
            },
            {
                path: 'ina',
                name: '库存杂项异动',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/InaView')
            }
        ]
    },
    {
        path: 'system',
        name: '系统管理',
        icon: 'el-icon-s-tools',
        component: null,
        children: [
            {
                path: 'zx',
                name: '人员档案',
                icon: 'el-icon-user-solid',
                component: () => import('@/views/system/ZxView')
            },
            {
                path: 'operation',
                name: '作业档案',
                icon: 'el-icon-tickets',
                component: () => import('@/views/system/GazView')
            },
            {
                path: 'smy',
                name: '单别档案',
                icon: 'el-icon-tickets',
                component: () => import('@/views/system/SmyView')
            },
            {
                path: 'import',
                name: '数据导入',
                icon: 'el-icon-upload2',
                component: () => import('@/views/system/ImportView')
            }
        ]
    },
    {
        path: 'flux',
        name: 'FLUX',
        icon: 'el-icon-paperclip',
        component: null,
        children: [
            {
                path: 'robotStates',
                name: 'RCS小车状态',
                icon: 'el-icon-truck',
                component: () => import('@/views/flux/robotStatesView')
            },
            {
                path: 'invTransfer',
                name: 'WMS库存控制',
                icon: 'el-icon-unlock',
                component: () => import('@/views/flux/InvTransferView')
            },
            {
                path: 'taskTime',
                name: '任务时间分析',
                icon: 'el-icon-s-data',
                component: () => import('@/views/flux/TaskTime')
            }
        ]
    }
]



