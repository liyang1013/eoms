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
 * 单据审核状态
 */
export function formatStatus(conf){
    
    if(conf === 'N') return '未审核';
    if(conf === 'Y') return '已审核';
    if(conf === 'P') return '收/发料中';
    if(conf === 'F') return '收/发料完成';
    if(conf === 'X') return '作废';

}

/**
 * 采购性质类型
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
    if(nature === 'BKR') return nature += '无交期性'
    
}

/**
 * 确认状态
 * @param {string} status 
 * @returns 
 */
export function formatConfirmationStatus(status){
    if(status === 'N') return  '未确认';
    if(status === 'Y') return  '确认';
}

/**
 * 过账状态
 * @param code
 * @returns {string}
 */
export function formatPost(code){
    if(code === 'Y') return '过账';
    if(code === 'N') return '未过账'
}

/**
 * 采购签核状况
 * @param {string} status 
 * @returns 
 */
export function formatSignoff(status){

    if(status === 'X') return '计划订单转入';
    if(status === '0') return '开立';
    if(status === '1') return '已核准';
    if(status === '2') return '转成/发出采购单';
    if(status === '6') return '结案';
    if(status === '7') return '结案-结长';
    if(status === '8') return '结案-结短';
    if(status === '9') return '作废';
    if(status === 'S') return '送签中';
    if(status === 'R') return '送签退回';
    if(status === 'W') return '抽单';

}


/**
* 料件类型
* @param {string} type
* @returns
*/
export function formatImaType(type){

    if(type === 'C') return '规格组件';
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

/**
 * 杂项作业内容
 * @param code
 * @returns {string}
 */
export function formatInaType(code){
    if(code === '1') return '库存杂项发料 aimt301';
    if(code === '2') return 'WIP 杂项发料 aimt311';
    if(code === '3') return '库存杂项收料 aimt302';
    if(code === '4') return 'WIP 杂项收料 aimt312';
    if(code === '5') return '库存杂项报废 aimt303';
    if(code === '6') return 'WIP 杂项报废 aimt313';
}

export function pickType(code){
    if(code === '0') return 'FIFO 拣货';
    if(code === '1') return '指定托盘拣货';
    if(code === '2') return '分批拣货';
}

export function formatWdi54(code){
    if(code === '0') return '未下发';
    if(code === '1') return 'WMS 已下发';
    if(code === '2') return 'FLUX WMS 已分配';
    if(code === '3') return '已出库';
}

/**
 * 账款日起算基准
 * @param code
 * @returns {string}
 */
export function formatPma(code){

    if(code === '1') return '进货日';
    if(code === '2') return '发票日';
    if(code === '3') return '帐款日';
    if(code === '4') return '进货日次月初';
    if(code === '5') return '发票日次月初';
    if(code === '6') return '帐款日次月初';
    if(code === '7') return '应付款日';
    if(code === '8') return '应付款日次月初';

}

/**
 * 付款类型
 * @param code
 * @returns {string}
 */
export function formatPma11(code){

    if(code === '1') return '支票';
    if(code === '2') return '转帐';
    if(code === '3') return 'L/C';
    if(code === '4') return 'OA';
    if(code === '6') return 'DA';
    if(code === '7') return 'DP';
    if(code === '8') return 'LLC';

}

export function formatApproval(code){
    if(code === '0') return '已核准';
    if(code === '1') return '核准中';
    if(code === '2') return '未核准';
}



