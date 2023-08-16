import FileSaver from "file-saver";
import XLSX from "xlsx";

/**
 * el-table导出excel带样式
 * @param tableElement ref
 * @param name
 * @returns {any}
 */
export function useExcel(tableElement, name) {
    const fileName = name
        ? name + ".xlsx"
        : new Date().toLocaleDateString() + ".xlsx";

    /* generate workbook object from table */
    const wb = XLSX.utils.table_to_book(tableElement);
    /* get binary string as output */
    const wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array",
    });
    try {
        FileSaver.saveAs(
            new Blob([wbout], { type: "application/octet-stream" }),
            fileName
        );
    } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
    }
    return wbout;
}