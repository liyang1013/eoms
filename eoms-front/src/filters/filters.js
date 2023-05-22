//数字转字符串
function formatNumber(n) {
    const str = n.toString()
    return str[1] ? str : `0${str}`
}

//格式化时间格式
export function formatTime(date) {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()

    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()

    const t1 = [year, month, day].map(formatNumber).join('/')
    const t2 = [hour, minute, second].map(formatNumber).join(':')

    return `${t1} ${t2}`
}

/**
 * 全局单据状态格式化返回 
 */
export function formatStatus(conf){
    
    if(conf === 'N') return '未审核';
    if(conf === 'Y') return '已审核';
    if(conf === 'P') return '收/发料中';
    if(conf === 'F') return '收/发料完成';
    if(conf === 'X') return '作废';

}

/**
 * 返回采购性质类型
 * @param {string} nature 
 * @returns 
 */
export function formatNature(nature){
    
    if(nature === 'REG') return nature += ':原物料';
    if(nature === 'EXP') return nature += ':费用';
    if(nature === 'CAP') return nature += ':资产';
    if(nature === 'SUB') return nature += ':委外加工';
    if(nature === 'TRI') return nature += ':多角贸易';
    if(nature === 'TAP') return nature += ':多角贸易代采买';
    
}

/**
 * 返回确认状态
 * @param {string} status 
 * @returns 
 */
export function formatConfirmationStatus(status){
   
    if(status === 'N') return  '未确认';
    if(status === 'Y') return  '确认';
   
}

/**
 * 返回签核状况
 * @param {string} status 
 * @returns 
 */
export function formatSignoff(status){
    
    if(status === '0') return '开立';
    if(status === '1') return '已核准';
    if(status === '9') return '作废';
    if(status === 'S') return '送签中';
    if(status === 'R') return '送签退回';
    if(status === 'W') return '抽单';
   
}


/**
 * 返回料件类型
 * @param {string}  
 * @returns 
 */
export function formatImaType(type){

    if(type === 'C') return  '规格组件';
    if(type === 'T') return '最后规格料件';
    if(type === 'D') return '特性料件';
    if(type === 'A') return '族群料件';
    if(type === 'M') return '自制料件';
    if(type === 'P') return '采购料件';
    if(type === 'X') return '虚拟料件';
    if(type === 'K') return '配件虚拟料件';
    if(type === 'U') return '自制大宗料件';
    if(type === 'V') return '采购大宗料件';
    if(type === 'R') return '在制途料件';
    if(type === 'Z') return '杂项料件';
    if(type === 'S') return '厂外加工料件';
}
