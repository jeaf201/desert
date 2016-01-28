package com.bestfuture.desert.commons.dcd.csv;

import java.io.BufferedWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bestfuture.desert.commons.dcd.api.annotation.ExcelColumn;
import com.bestfuture.desert.commons.dcd.api.csv.CsvService;

public class CsvServiceImpl implements CsvService {

	@Override
	public void buildCsvDocument(List<?> list, int pageIndex, BufferedWriter writer, boolean isExcelColumn) {
		Method method = null;
		Object element = null;
		try {
			for (int i = 0; i < list.size(); i++) {
	            element = list.get(i);
	
	            // 标题
            	if(pageIndex == 1 && i == 0) {
	        		if(isExcelColumn) {
		        		writer.write(getCsvTitle(element));
	        		} else {
						method = element.getClass().getMethod("getCsvTitle");
		        		writer.write(method.invoke(element).toString());
	        		}
	        		writer.newLine();
            	}
        		
            	// 内容
        		if(isExcelColumn) {
	        		writer.write(getCsvValue(element));
        		} else {
                    method = element.getClass().getMethod("getCsvValue");
            		writer.write(method.invoke(element).toString());
        		}
        		writer.newLine();
	        }
	        
	        writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCsvTitle(Object o) {
		StringBuilder builder = new StringBuilder();
		Field[] fields = o.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            
            if (field.isAnnotationPresent(ExcelColumn.class)) {
            	ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            	builder.append(excelColumn.name()).append(PLACEHOLDER);
            }
		}
		builder.deleteCharAt(builder.length() - 1);
		
		return builder.toString();
	}
	
	public String getCsvValue(Object o) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder builder = new StringBuilder();
		Field[] fields = o.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            
            if (field.isAnnotationPresent(ExcelColumn.class)) {
            	field.setAccessible(true);
            	builder.append(getValue(field, o)).append(PLACEHOLDER);
            }
		}
		builder.deleteCharAt(builder.length() - 1);
		
		return builder.toString();
	}
	
	private Object getValue(Field field, Object o) throws IllegalArgumentException, IllegalAccessException {
		Object value = field.get(o);
		ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
		if(value.getClass().equals(Date.class)) {
			SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(excelColumn.format()));
			
			return sdf.format(value);
		}
		
		return value;
	}
	
	
	// 可以建立一个映射关系
	private String getDateFormat(String format) {
//		if("yyyy-m-d".equals(format))
//			return "yyyy-MM-dd";

		// default format
		return "yyyy-MM-dd";
	}
	
}
