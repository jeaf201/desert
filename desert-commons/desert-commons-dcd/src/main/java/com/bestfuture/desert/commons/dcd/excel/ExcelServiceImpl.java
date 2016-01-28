package com.bestfuture.desert.commons.dcd.excel;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bestfuture.desert.commons.dcd.api.annotation.ExcelColumn;
import com.bestfuture.desert.commons.dcd.api.excel.ExcelService;

public class ExcelServiceImpl implements ExcelService {

	@Override
	public Workbook buildExcelDocument(List<?> list) {
		if(list == null || list.size() <= 0)
			return null;
		
        // 产生工作簿对象
		Workbook workbook = new XSSFWorkbook();
		
		return build(list, workbook);
	}

	@Override
	public void buildExcelDocument(List<?> list, Workbook workbook) {
		if(list == null || list.size() <= 0)
			return;

		build(list, workbook);
	}
	

	private Workbook build(List<?> list, Workbook workbook) {
        //设置第一个工作表的名称为name
        //产生Excel表头
        Sheet sheet = workbook.createSheet("sheet1");

        Field[] fields = list.get(0).getClass().getDeclaredFields();
        
        CellStyle[] cellStyles = new CellStyle[fields.length];
        
        for (int i = 0; i < list.size(); i++) {
            Object element = list.get(i);
            int r = 0;
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];
                
                if (field.isAnnotationPresent(ExcelColumn.class)) {
                    if(i == 0) { // 标题列
                        //获取该字段的注解对象
                        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
                        setTitle(workbook, sheet, cellStyles, excelColumn, r, j);
                    }
                    
                    //填充数据
                    try {
                        field.setAccessible(true);
	                    setValue(getCell(sheet, i + 1, r++), field.get(element), cellStyles[j]);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    
                }
            }
        }
        
		return workbook;
	}
	

	private void setTitle(Workbook workbook, Sheet sheet, CellStyle[] cellStyles, ExcelColumn excelColumn, int i, int j) {
        DataFormat fmt = workbook.createDataFormat();
        String format = excelColumn.format();
        
        sheet.setColumnWidth(i, excelColumn.width() * 256);
        setValue(getCell(sheet, 0, i), excelColumn.name());
        
        //set data format
        if(format.length() > 0) {
        	CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(fmt.getFormat(format));
            cellStyles[j] = cellStyle;
        }
	}
	


	/**
	 * Convenient method to obtain the cell in the given sheet, row and column.
	 * <p>Creates the row and the cell if they still doesn't already exist.
	 * Thus, the column can be passed as an int, the method making the needed downcasts.
	 * @param sheet a sheet object. The first sheet is usually obtained by workbook.getSheetAt(0)
	 * @param row thr row number
	 * @param col the column number
	 * @return the Cell
	 */
	protected Cell getCell(Sheet sheet, int row, int col) {
		Row sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		Cell cell = sheetRow.getCell(col);
		if (cell == null) {
			cell = sheetRow.createCell(col);
		}
		return cell;
	}

	
	private void setValue(Cell cell, Object o) {
		this.setValue(cell, o, null);
	}
	
	private void setValue(Cell cell, Object o, CellStyle cellStyle) {
		if(o.getClass().equals(Date.class)) {
			this.setDate(cell, (Date)o, cellStyle);
			return;
		}
		
		if(o.getClass().equals(Long.class) || o.getClass().equals(Integer.class)
				|| o.getClass().equals(Float.class) || o.getClass().equals(Double.class)) {
			this.setNumeric(cell, Double.valueOf(o.toString()), cellStyle);
			return;
		}
		
		this.setText(cell, o.toString(), cellStyle);
	}


	protected void setText(Cell cell, String value, CellStyle cellStyle) {
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}

	protected void setDate(Cell cell, Date value, CellStyle cellStyle) {
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}

	protected void setNumeric(Cell cell, double value, CellStyle cellStyle) {
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
	}
	
}
