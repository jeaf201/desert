package com.bestfuture.desert.commons.dcd;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestfuture.desert.commons.dcd.api.annotation.ExcelColumn;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelTestVo {
    private static final String PLACEHOLDER = ",";
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
	@ExcelColumn(name="姓名", width = 30)
	private String name;
	@ExcelColumn(name="工资", format = "¥#,##0")
	private float salary;
	@ExcelColumn(name="生日", format = "yyyy-m-d")
	private Date birthday;
	@ExcelColumn(name="工作日")
	private String gzr;
	@ExcelColumn(name="年龄", format = "0") // int类型，xssf没问题，hssf导致数据看不到（格式不对），通用的就加上格式
	private int age;
	
	
	public String getCsvTitle() {
		StringBuilder builder = new StringBuilder();
		builder.append("姓名").append(PLACEHOLDER)
			.append("工资").append(PLACEHOLDER)
			.append("生日").append(PLACEHOLDER)
			.append("工作日").append(PLACEHOLDER)
			.append("年龄");
		
		return builder.toString();
	}
	
	public String getCsvValue() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.name).append(PLACEHOLDER)
			.append(this.salary).append(PLACEHOLDER)
			.append(sdf.format(this.birthday)).append(PLACEHOLDER)
			.append(this.gzr).append(PLACEHOLDER)
			.append(this.age);
		
		return builder.toString();
	}

/*
	public String getCsvTitle() {
		StringBuilder builder = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            
            if (field.isAnnotationPresent(ExcelColumn.class)) {
            	ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            	builder.append(excelColumn.name());
            	
            	if(i != fields.length - 1)
            		builder.append(PLACEHOLDER);
            }
		}
		return builder.toString();
	}
	
	public String getCsvValue() throws IllegalArgumentException, IllegalAccessException {
		StringBuilder builder = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            
            if (field.isAnnotationPresent(ExcelColumn.class)) {
            	builder.append(field.get(this));
            	
            	if(i != fields.length - 1)
            		builder.append(PLACEHOLDER);
            }
		}
		return builder.toString();
	}
*/
}
