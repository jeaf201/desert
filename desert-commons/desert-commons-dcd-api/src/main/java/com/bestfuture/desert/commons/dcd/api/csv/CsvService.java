package com.bestfuture.desert.commons.dcd.api.csv;

import java.io.BufferedWriter;
import java.util.List;

/**
 * Csv dcd
 * @author David
 *
 */
public interface CsvService {

	public static final String CONTENT_TYPE = "text/csv";

	public static final String EXTENSION = ".csv";

	public static final String CHARSETNAME = "UTF-8";

	public static final String CHARSETNAME_GBK = "GBK";

	public static final String PLACEHOLDER = ",";
	
	/**
	 * 循环输出list-->>Csv格式数据
	 * @param list
	 * @param pageIndex 支持分页
	 * @param writer
	 * @param isExcelColumn 是否支持@ExcelColumn，默认true，否的话，需要在实体类中添加getCsvTitle()、getCsvValue()方法，用以提高性能
	 * @return
	 */
	public void buildCsvDocument(List<?> list, int pageIndex, BufferedWriter writer, boolean isExcelColumn);
}
