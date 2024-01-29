export const menu = [
    {
        name: '首页',
        path: 'home',
        icon: 'el-icon-house',
        component: () => import('@/views/HomeView')
    },
    {
        path: 'identify',
        name: '发票识别',
        component: null,
        icon: 'el-icon-picture',
        children: [
            {
                path: 'vat-identify',
                name: '增值税发票',
                icon: 'el-icon-picture-outline',
                component: () => import('@/views/invoice/VatIdentifyView')
            }
        ]
    },
    {
        path: 'records',
        name: '发票管理',
        component: null,
        icon: 'el-icon-tickets',
        children: [
            {
                path: 'vat-records',
                name: '增值税发票管理',
                icon: 'el-icon-s-ticket',
                component: () => import('@/views/invoice/VatInvoiceView')
            },
        ]
    }
]