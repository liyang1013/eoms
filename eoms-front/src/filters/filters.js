
/*********************************** stock ***********************************/

/**
 * 料件类型
 * @param code {string}
 * @returns
 */
export function formatImaType(code) {
    let type = 'undefine';
    if (code === 'C') type = '规格组件';
    if (code === 'T') type = '最后规格料件';
    if (code === 'D') type = '特性料件';
    if (code === 'A') type = '族群料件';
    if (code === 'M') type = '自制料件';
    if (code === 'P') type = '采购料件';
    if (code === 'X') type = '虚拟料件';
    if (code === 'K') type = '配件虚拟料件';
    if (code === 'U') type = '自制大宗料件';
    if (code === 'V') type = '采购大宗料件';
    if (code === 'R') type = '在制途料件';
    if (code === 'Z') type = '杂项料件';
    if (code === 'S') type = '厂外加工料件';
    return code + ': ' + type;
}


/**
 * 杂项作业内容
 * @param code
 * @returns {string}
 */
export function formatInaType(code) {
    if (code === '1') return '库存杂项发料';
    if (code === '2') return 'WIP 杂项发料';
    if (code === '3') return '库存杂项收料';
    if (code === '4') return 'WIP 杂项收料';
    if (code === '5') return '库存杂项报废';
    if (code === '6') return 'WIP 杂项报废';
}



export function formatChangeType(code) {
    if (code === '1') return '入库';
    if (code === '2') return '验退';
    if (code === '3') return '仓退';
}

/*********************************** 采购 ***********************************/

/**
 * 采购性质类型
 * @param {string} nature
 * @returns
 */
export function formatNature(nature) {

    if (nature === 'REG') return nature += ':原物料';
    if (nature === 'EXP') return nature += ':费用';
    if (nature === 'CAP') return nature += ':资产';
    if (nature === 'SUB') return nature += ':委外加工';
    if (nature === 'TRI') return nature += ':多角贸易';
    if (nature === 'TAP') return nature += ':多角贸易代采买';
    if (nature === 'BKR') return nature += '无交期性'

}



/**
 * 采购签核状况
 * @param {string} status
 * @returns
 */
export function formatSignoff(status) {

    if (status === 'X') return '计划订单转入';
    if (status === '0') return '开立';
    if (status === '1') return '已核准';
    if (status === '2') return '转成/发出采购单';
    if (status === '6') return '结案';
    if (status === '7') return '结案-结长';
    if (status === '8') return '结案-结短';
    if (status === '9') return '作废';
    if (status === 'S') return '送签中';
    if (status === 'R') return '送签退回';
    if (status === 'W') return '抽单';

}

/*********************************** 生产 ***********************************/


export function formatSfa26(code){
    if (code === '0') return code + ':不可被取替代';
    if (code === '1') return code + ':新料, 有旧料可取代';
    if (code === '2') return code + ':主料, 有副料可替代';
    if (code === '3') return code + ':新料, 已经被取代';
    if (code === '4') return code + ':主料, 已经被替代';
    if (code === '5') return code + ':主料, 可做SET替代';
    if (code === '6') return code + ':主料, 已做SET替代';
    if (code === 'U') return code + ':旧料(取代料件)';
    if (code === 'S') return code + ':副料(替代料件)';
    if (code === 'T') return code + ':SET替代料';
}

export function formatSfp06(code){
    if (code === '1') return code + ':成套发料';
    if (code === '2') return code + ':超领';
    if (code === '3') return code + ':补料';
    if (code === '4') return code + ':耗材领';
    if (code === '6') return code + ':成套退料';
    if (code === '7') return code + ':超领退';
    if (code === '8') return code + ':一般退';
    if (code === '9') return code + ':耗材退';
    if (code === 'A') return code + ':重复性生产发料';
    if (code === 'B') return code + ':重复性生产退料';
    if (code === 'C') return code + ':重复性生产领料';
}

export function formatSfb02(code) {

    if (code === 1) return '一般工单';
    if (code === 5) return '再加工工单';
    if (code === 7) return '委外工单';
    if (code === 8) return '重工委外工单';
    if (code === 11) return '拆件式工单';
    if (code === 13) return '预测工单';
    if (code === 15) return '试产工单';

}

export function formatSfb04(code) {

    if (code === '1') return '开立';
    if (code === '2') return '发放';
    if (code === '3') return '打印';
    if (code === '4') return '发料';
    if (code === '5') return 'WIP';
    if (code === '6') return 'FQC';
    if (code === '7') return '入库';
    if (code === '8') return '结案';
}



/*********************************** 销售 ***********************************/

/**
 * 销售类型
 * @param code {string}
 * @returns {string}
 */
export function formatSaleType(code){
    let type = 'undefine';
    if (code === '1') type = '内销';
    if (code === '2') type = '外销';
    if (code === '3') type = '视同外销';
    return code + ': ' + type;
}

/*********************************** 财务 ***********************************/


/**
 * 账款日起算基准
 * @param code
 * @returns {string}
 */
export function formatPma(code) {

    if (code === '1') return '进货日';
    if (code === '2') return '发票日';
    if (code === '3') return '帐款日';
    if (code === '4') return '进货日次月初';
    if (code === '5') return '发票日次月初';
    if (code === '6') return '帐款日次月初';
    if (code === '7') return '应付款日';
    if (code === '8') return '应付款日次月初';

}

/**
 * 付款类型
 * @param code
 * @returns {string}
 */
export function formatPma11(code) {

    if (code === '1') return '支票';
    if (code === '2') return '转帐';
    if (code === '3') return 'L/C';
    if (code === '4') return 'OA';
    if (code === '6') return 'DA';
    if (code === '7') return 'DP';
    if (code === '8') return 'LLC';

}


/*********************************** Common ***********************************/

/**
 * 状况码
 * @param code {string}
 * @returns {string}
 */
export function formatStatus(code) {
    let type = 'undefine';
    if (code === '0') type = '开立';
    if (code === '1') type = '已核准';
    if (code === '9') type = '作废';
    if (code === 'R') type = '送签退回';
    if (code === 'S') type = '送签中';
    if (code === 'W') type = '抽单';
    return code + ': ' + type;
}

/**
 * 审核状态
 * @param code {string}
 * @returns {string}
 */
export function formatVerifyStatus(code) {
    let type = 'undefine';
    if (code === 'N') type =  '未审核';
    if (code === 'Y') type =  '已审核';
    if (code === 'P') type =  '收/发料中';
    if (code === 'F') type =  '收/发料完成';
    if (code === 'X') type =  '作废';
    return code + ': ' + type;

}

/**
* 核准状态
* @param code {string}
* @returns {string}
*/
export function formatApproval(code) {
    let type = 'undefine';
    if (code === '0') type = '已核准';
    if (code === '1') type = '核准中';
    if (code === '2') type = '未核准';
    return code + ': ' + type;
}

/**
 * 确认状态
 * @param code {string}
 * @returns {string}
 */
export function formatConfirmationStatus(code) {
    let type = 'undefine';
    if (code === 'N') type = '未确认';
    if (code === 'Y') type = '确认';
    return code + ': ' + type;
}

/**
 * 过账状态
 * @param code {string}
 * @returns {string}
 */
export function formatPostStatus(code) {
    let type = 'undefine';
    if (code === 'N') return type = '未过账';
    if (code === 'Y') type = '过账';
    return code + ': ' + type;
}


/*********************************** wms ***********************************/

export function pickType(code) {
    if (code === '0') return 'FIFO 拣货';
    if (code === '1') return '指定托盘拣货';
    if (code === '2') return '分批拣货';
}

export function formatWdi54(code) {
    if (code === '0') return '未下发';
    if (code === '1') return 'WMS 已下发';
    if (code === '2') return 'FLUX WMS 已分配';
    if (code === '3') return '已出库';
}


/*********************************** 常用格式化函数 ***********************************/

/**
 * 时间格式化 yyyy/MM/dd hh:mm:ss
 * @param date {Date}
 * @returns {string}
 */
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
 * 不满2位数字前自动补0
 * @param num {number}
 * @returns {string|string}
 * @constructor
 */
function ZeroFill(num) {
    const str = num.toString()
    return str[1] ? str : `0${str}`
}