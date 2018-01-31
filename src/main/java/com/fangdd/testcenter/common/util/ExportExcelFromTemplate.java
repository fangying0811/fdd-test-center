package com.fangdd.testcenter.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

public class ExportExcelFromTemplate {
	private Logger logger = LoggerFactory.getLogger(ExportExcelFromTemplate.class);

	public static final String POSTIFIX = ".xls";

	protected String reportName;
	protected Workbook workBook = new HSSFWorkbook();
	protected Sheet sheet;
	protected String sheetName;
	protected InputStream excelStream;

	public ExportExcelFromTemplate(String reportName, String sheetName) {
		super();
		this.reportName = reportName;
		this.sheetName = sheetName;
		sheet = workBook.createSheet(sheetName);
	}

	public ExportExcelFromTemplate() {
		super();
	}

	public InputStream makeReportFromTemplet(String templetFileName, Map beans) {
		logger.info("templetFileName->{}", templetFileName);
		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		InputStream is = null;
		try {
			is = new FileInputStream(templetFileName);
			try {
				workBook = transformer.transformXLS(is, beans);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			// 产生POI输出流
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workBook.write(os);
			excelStream = new ByteArrayInputStream(os.toByteArray());
			os.flush();
			os.close();
			is.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return excelStream;
	}

}
