package com.bestfuture.desert.commons.dcd;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bestfuture.desert.commons.dcd.api.csv.CsvService;
import com.bestfuture.desert.commons.dcd.csv.CsvServiceImpl;

public class CsvServiceTest {

	public static void main(String[] args) throws IOException {
		CsvService csvService = new CsvServiceImpl();

		List<ExcelTestVo> list = new ArrayList<ExcelTestVo>();
		Calendar c = Calendar.getInstance();
		c.set(2012,3-1,18); // Don't forget months are 0 based on Calendar
		
		list.add(new ExcelTestVo("李元芳", 300000, c.getTime(), "2001-1-1", 22));
		list.add(new ExcelTestVo("david", 50000, new Date(), "2001-1-1", 33));
		list.add(new ExcelTestVo("吴勇", 100000, new Date(), "2001-1-1", 26));
		list.add(new ExcelTestVo("tom", 6000, new Date(), "2001-1-1", 21));
		
		int bufferSize = 1024;
		
		OutputStream fos1 = new FileOutputStream("d:/ss1" + CsvService.EXTENSION);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos1, CsvService.CHARSETNAME_GBK), bufferSize);
		csvService.buildCsvDocument(list, 1, writer, true);

		
		csvService.buildCsvDocument(list, 2, writer, true);
		fos1.flush();
		fos1.close();
		fos1 = null;
		writer.flush();
		writer.close();
		writer = null;
		System.out.println("--fos1-->" + fos1);
		System.out.println("--writer-->" + writer);

		OutputStream fos2 = new FileOutputStream("d:/ss2" + CsvService.EXTENSION);
		writer = new BufferedWriter(new OutputStreamWriter(fos2, CsvService.CHARSETNAME_GBK), bufferSize);
		csvService.buildCsvDocument(list, 1, writer, false);

		list.clear();
		list.add(new ExcelTestVo("李元芳", 10000, c.getTime(), "2002-1-1", 26));
		list.add(new ExcelTestVo("david", 30000, new Date(), "2002-1-1", 32));
		list.add(new ExcelTestVo("吴勇", 100000, new Date(), "2002-1-1", 28));
		list.add(new ExcelTestVo("tom", 30000, new Date(), "2002-1-1", 22));

		csvService.buildCsvDocument(list, 2, writer, false);
		fos2.flush();
		fos2.close();
		fos2 = null;
		writer.flush();
		writer.close();
		writer = null;
		
		System.out.println("--writer-->" + writer);
	}

}
