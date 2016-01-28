package com.bestfuture.desert.commons.dcd.api.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel dcd
 * @author David
 *
 */
public interface ExcelService {
	/** The content type for an Excel response */
	public static final String CONTENT_TYPE = "application/vnd.ms-excel";
	
	/** The extension to look for existing templates */
	public static final String EXTENSION = ".xls";
	
	public static final String EXTENSION_EXT = ".xlsx";

	public static final String CHARSETNAME = "UTF-8";

	/**
	 * buildExcel文档
	 * @param list
	 * @return
	 */
	public Workbook buildExcelDocument(List<?> list);

	/**
	 * buildExcel文档
	 * @param list
	 * @param workbook
	 */
	public void buildExcelDocument(List<?> list, Workbook workbook);
}
