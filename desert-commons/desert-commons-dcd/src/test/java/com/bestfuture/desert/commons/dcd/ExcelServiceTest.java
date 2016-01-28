package com.bestfuture.desert.commons.dcd;

import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bestfuture.desert.commons.dcd.api.excel.ExcelService;
import com.bestfuture.desert.commons.dcd.excel.ExcelServiceImpl;

public class ExcelServiceTest {

	public static void main(String[] args) throws IOException {
		ExcelService excelService = new ExcelServiceImpl();
		List<ExcelTestVo> list = new ArrayList<ExcelTestVo>();
		
		Calendar c = Calendar.getInstance();
		c.set(2015, 3-1, 18); // Don't forget months are 0 based on Calendar

		list.add(new ExcelTestVo("李元芳", 300000, c.getTime(), "2001-1-1", 22));
		list.add(new ExcelTestVo("david", 50000, new Date(), "2001-1-1", 33));
		list.add(new ExcelTestVo("吴勇", 100000, new Date(), "2001-1-1", 26));
		list.add(new ExcelTestVo("tom", 6000, new Date(), "2001-1-1", 21));
		
		Workbook workbook = null;
		
//		workbook = excelService.buildExcelDocument(list);

		workbook = new HSSFWorkbook();
//		workbook = new XSSFWorkbook();
		excelService.buildExcelDocument(list, workbook);
		
//		String fileName = null;
//		
//		if(workbook instanceof XSSFWorkbook)
//			fileName = "E:/aa" + ExcelService.EXTENSION_EXT;
//		else if(workbook instanceof HSSFWorkbook)
//			fileName = "E:/aa" + ExcelService.EXTENSION;
//		
		// Save
		   String filename = "d:/aa" + ExcelService.EXTENSION;
		   if(workbook instanceof XSSFWorkbook) {
		     filename = filename + "x";
		   }
		
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
		fos = null;

	}
	
/*
    public static void output(String filename, List<?> list, HttpServletResponse response) throws Exception {
		ExcelService excelService = new ExcelServiceImpl();
		
        // 生成提示信息，
        response.setContentType(ExcelService.CONTENT_TYPE);
        String codedFileName = null;
        OutputStream out = null;
        try {
            // 进行转码，使其支持中文文件名
            codedFileName = URLEncoder.encode(filename, ExcelService.CHARSETNAME);
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + "(" + getFileName() + ")" + ExcelService.EXTENSION_EXT);
            out = response.getOutputStream();
            XSSFWorkbook workbook = excelService.buildExcelDocument(list);
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
//            log.error("excel file export failure , detail", e);
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
//                log.error("excel file export failure , detail",e);
            }
        }
    }
    
    *//**
     * 根据本地时间获得文件名称，精确到毫秒。
     *
     * @return .xls文件名
     *//*
    private static String getFileName() {
        SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        Date time = new Date();
        String name = datetime.format(time);
        return name;
    }
*/
}
