package com.fangdd.testcenter.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangdd.qa.framework.utils.common.CommonUtil;

/**
 * 导出Excel公共方法
 * 
 * @version 1.0
 * 
 * @author hexin
 * @param <T>
 * 
 */
@SuppressWarnings("deprecation")
public class ExportExcel {
	private Logger logger = LoggerFactory.getLogger(ExportExcel.class);

	// 显示的导出表的标题
	private String title;
	// 导出表的列名
	private String[] rowName;

	private List<Object[]> dataList = new ArrayList<Object[]>();

	HttpServletResponse response;
	private int addMergedRegion;

	// 构造方法，传入要导出的数据
	public ExportExcel(String title, String[] rowName, List<Object[]> dataList) {
		this.dataList = dataList;
		this.rowName = rowName;
		this.title = title;
	}

	/*
	 * 导出数据
	 */
	@SuppressWarnings("deprecation")
	public HSSFWorkbook export() throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
			sheet.setDefaultColumnWidth(25);

			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象

			addMergedRegion = sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			// 定义所需列数
			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}

			/** ***************以下是EXCEL正文数据********************* */
			int listLength = dataList.size();
			for (int rownum = 0; rownum < dataList.size(); rownum++) {
				Object[] obj = dataList.get(rownum);// 遍历每个对象
				HSSFRow row = sheet.createRow(rownum + 3);// 创建所需的行数
				int length = obj.length;
				for (int colnum = 0; colnum < length; colnum++) {
					HSSFCell cell = null; // 设置单元格的数据类型
					cell = row.createCell(colnum, HSSFCell.CELL_TYPE_STRING);
					if (CommonUtil.isEmpty(obj[colnum].toString())) {
						cell.setCellValue("");// 设置单元格的值
					} else {
						cell.setCellValue(obj[colnum].toString());// 设置单元格的值
					}
					cell.setCellValue(obj[colnum].toString());// 设置单元格的值
					cell.setCellStyle(style);// 设置单元格样式
				}
			}

			// // 让列宽随着导出的列长自动适应
			// for (int colNum = 0; colNum < columnNum; colNum++) {
			// int columnWidth = 20;
			// for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
			// HSSFRow currentRow;
			// // 当前行未被使用过
			// if (sheet.getRow(rowNum) == null) {
			// currentRow = sheet.createRow(rowNum);
			// } else {
			// currentRow = sheet.getRow(rowNum);
			// }
			// if (currentRow.getCell(colNum) != null) {
			// HSSFCell currentCell = currentRow.getCell(colNum);
			// if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			// int length = currentCell.getStringCellValue()
			// .getBytes().length;
			// if (length <= columnWidth) {
			// columnWidth = length;
			// }
			// }
			// }
			// }
			// sheet.setColumnWidth(colNum, columnWidth * 256);
			// }
			return workbook;
		} catch (Exception e) {
			logger.error("export excel error,", e);
		}
		return null;

	}

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}

	public static void main(String[] args) {
		System.out.println("内容专题活动".getBytes().length);
	}
}