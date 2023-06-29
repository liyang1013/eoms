export const menu = [
    {
        name: '首页',
        path: 'home',
        icon: 'el-icon-s-platform',
        component: () => import('@/views/HomeView.vue')
    },
    {
        path: 'purchase',
        name: '采购管理',
        icon: 'el-icon-goods',
        component: null,
        children: [
            {
                path: 'pmi',
                name: '核价单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/PmiView')
            },
            {
                path: 'rva',
                name: '收货单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/purchase/RvaView.vue')
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
                path: 'workOrder',
                name: '工单日期查询',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/ProduceView.vue'),
                children: []
            },
            {
                path: 'workOrderEdit',
                name: '工单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/produce/SfbView.vue'),
                children: []
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
                path: 'wdi',
                name: '拣货单',
                icon: 'el-icon-tickets',
                component: () => import('@/views/sale/WdiView.vue'),
                children: []
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
                component: () => import('@/views/stock/ImaView.vue'),
                children: []
            },
            {
                path: 'currStock',
                name: '现存量查询',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/ImgView.vue'),
                children: []
            },
            {
                path: 'ina',
                name: '库存杂项异动',
                icon: 'el-icon-tickets',
                component: () => import('@/views/stock/InaView.vue'),
                children: []
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
                path: 'contacts',
                name: '人员档案',
                icon: 'el-icon-user-solid',
                component: () => import('@/views/system/ZxView.vue'),
                children: []
            },
            {
                path: 'operation',
                name: '作业档案',
                icon: 'el-icon-tickets',
                component: () => import('@/views/system/GazView.vue'),
                children: []
            },
            {
                path: 'import',
                name: '数据导入',
                icon: 'el-icon-upload2',
                component: () => import('@/views/system/ImportView.vue'),
                children: []
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
                component: () => import('@/views/flux/robotStatesView.vue'),
                children: []
            },
            {
                path: 'invTransfer',
                name: 'WMS库存控制',
                icon: 'el-icon-unlock',
                component: () => import('@/views/flux/InvTransferView.vue'),
                children: []
            },
            {
                path: 'taskAnalysis',
                name: 'FLUX任务时间分析',
                icon: 'el-icon-time',
                component: () => import('@/views/flux/TaskAnalysis'),
                children: []
            }
        ]
    }
]



