package com.dr.common.utils.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.dr.common.constant.Constant;
import com.dr.common.errorcode.BaseCode;
import com.dr.common.exception.BizException;
import com.dr.common.log.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author rui.duan
 * @version 1.0
 * @className ExcelUtil
 * @description excel导入导出方法
 * @date 2019-05-28 15:15
 */
@Slf4j
public class ExcelUtil {

    /**
     * 表格标题行数,默认0
     */
    public final static Integer DEFAULT_TITLE_ROWS = 0;
    /**
     * 表头行数,默认1
     */
    public final static Integer DEFAULT_HEAD_ROWS = 1;


    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";


    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }


    /**
     * @description: 导出excel文件
     * @params:
     * @return:
     * @author: rui.duan
     * @date: 2019-05-28
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException(BaseCode.EXPORT_OBJECT_EMPTY);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        ServletOutputStream outStream = null;
        try {

            response.reset();//清空输出流
            String finalFileName = URLEncoder.encode(fileName, "UTF8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + finalFileName +"."+ExcelUtil.EXCEL_XLS);
            outStream = response.getOutputStream();
            workbook.write(outStream);
            outStream.close();
        } catch (IOException e) {
            throw new BizException(BaseCode.EXCEL_DEAL_ERROR.getCode(), e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }


    /**
     * @description: 从本地读取解析excel文件
     * @params:
     * @return:
     * @author: rui.duan
     * @date: 2019-05-28
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new BizException(BaseCode.EXCEL_TEMPLE_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(BaseCode.EXCEL_DEAL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }


    /**
     * @param file       http流文件
     * @param titleRows  表格标题行数,默认0
     * @param headerRows 表头行数,默认1
     * @param <T>        需要解析的类型
     * @description: 导入excel文件
     * @return:
     * @author: rui.duan
     * @date: 2019-05-28
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
       return importExcel(file, titleRows, headerRows, pojoClass,null);
    }

    /**
     * 添加自定义校验规则
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param excelVerifyHandler
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass,IExcelVerifyHandler excelVerifyHandler)
        {
        if (file == null) {
            return null;
        }
        if (checkFile(file)) {
            throw new BizException(BaseCode.EXCEL_TYPE_ERROR);
        }
        //设置导入参数
        ImportParams params = new ImportParams();
        //读取时标题行号
        params.setTitleRows(titleRows);
        //读取时头部行号
        params.setHeadRows(headerRows);
        //是否开启规则校验
        params.setNeedVerfiy(true);
        if(null != excelVerifyHandler){
            //是否使用自定义校验
            params.setVerifyHandler(excelVerifyHandler);
        }
        List<T> list = null;
        try {
            ExcelImportResult excelImportResult = new ExcelImportService().importExcelByIs(file.getInputStream(), pojoClass, params, true);
            //拿到错误行信息
            List failList = excelImportResult.getFailList();
            if(!CollectionUtils.isEmpty(failList)){
                LoggerUtil.error(log, "导入数据格式有误", failList);
            }
            //获取正确行信息
            list = excelImportResult.getList();
            if (CollectionUtils.isEmpty(list)) {
                throw new BizException(BaseCode.EXCEL_NOT_EMPTY_ERROR);
            } else if (list.size() > Constant.LIMIT_IMPORT_COUNT) {
                throw new BizException(BaseCode.EXCEL_OVERFLOW);
            }
        } catch (NoSuchElementException e) {
            throw new BizException(BaseCode.EXCEL_NOT_EMPTY_ERROR);
        } catch (Exception e) {
            throw new BizException(BaseCode.EXCEL_DEAL_ERROR.getCode(), e.getMessage());
        }
        return list;
    }

    /**
     *    检查该文件是否是xls，还是xlsx类型
     */
    private static boolean checkFile(MultipartFile file) {
        boolean flg = false;
        String name = file.getName();
        if (name.endsWith(EXCEL_XLSX) || name.endsWith(EXCEL_XLS)) {
            flg = true;
        }
        return flg;

    }

}
