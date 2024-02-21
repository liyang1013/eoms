export const menu = [
    {
        name: '首页',
        path: 'home',
        icon: 'el-icon-house',
        component: () => import('@/views/HomeView')
    },
    {
        path: 'erp-authority',
        name: 'ERP权限评审',
        icon: 'el-icon-s-check',
        component: null,
        children: [
            {
                path: 'position-records',
                name: '权限记录',
                icon: 'el-icon-tickets',
                component: () => import('@/views/authority/erp/PositionRecordsView.vue')
            },
            {
                path: 'position-contrast',
                name: '权限对比',
                icon: 'el-icon-data-analysis',
                component: () => import('@/views/authority/erp/PositionContrastView.vue')
            },
            {
                path: 'personal-position-records',
                name: '员工权限记录',
                icon: 'el-icon-tickets',
                component: () => import('@/views/authority/erp/PersonalPositionRecordsView.vue')
            },
            {
                path: 'personal-position-contrast',
                name: '员工权限对比',
                icon: 'el-icon-data-analysis',
                component: () => import('@/views/authority/erp/PersonalPositionContrastView.vue')
            },
            {
                path: 'action',
                name: 'action设置',
                icon: 'el-icon-s-tools',
                component: () => import('@/views/authority/erp/ActionMenuView.vue')
            }
        ]
    }
]