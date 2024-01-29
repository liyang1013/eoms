export const menu = [
    {
        name: '首页',
        path: 'home',
        icon: 'el-icon-house',
        component: () => import('@/views/HomeView')
    },
    {
        path: 'review',
        name: 'ERP权限评审',
        icon: 'el-icon-s-check',
        component: null,
        children: [
            {
                path: 'records',
                name: '权限记录',
                icon: 'el-icon-tickets',
                component: () => import('@/views/authority/RecordsView')
            },
            {
                path: 'contrast',
                name: '权限对比',
                icon: 'el-icon-data-analysis',
                component: () => import('@/views/authority/ContrastView')
            },
            {
                path: 'action',
                name: 'action设置',
                icon: 'el-icon-s-tools',
                component: () => import('@/views/authority/ActionMenuView')
            }
        ]
    }
]