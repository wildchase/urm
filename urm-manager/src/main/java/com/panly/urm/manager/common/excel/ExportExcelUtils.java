package com.panly.urm.manager.common.excel;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.panly.umr.common.DateUtil;
import com.panly.umr.common.ReflectUtil;


/**
 * Excel工具类
 * 
 * @author miU
 *
 */
public class ExportExcelUtils {

	/** 列默认宽度 */
	private static final int DEFAUL_COLUMN_WIDTH = 4000;
	
	
	/**
	 * 1.创建 workbook
0-	 */
	private Workbook createWorkBook() {
		return new XSSFWorkbook();
	}

	/**
	 * 2.创建 sheet
	 * 
	 */
	private Sheet createSheet(Workbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}

	/**
	 * 3.写入表头信息
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值
	 *            </p>
	 * @param title
	 *            标题
	 */
	private void writeHeader(Workbook workbook, Sheet sheet, String[] headers, String title) {

		// 头信息处理
		String[] newHeaders = headersHandler(headers);

		// 初始化标题和表头单元格样式
		CellStyle titleCellStyle = createTitleCellStyle(workbook);
		// 标题栏
		Row titleRow = sheet.createRow(0);
		titleRow.setHeight((short) 500);
		Cell titleCell = titleRow.createCell(0);
		
		// 设置标题文本
		titleCell.setCellValue(new XSSFRichTextString(title));
		// 设置单元格样式
		titleCell.setCellStyle(titleCellStyle);

		// 处理单元格合并，四个参数分别是：起始行，终止行，起始列，终止列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, (short) (newHeaders.length - 1)));

		// 设置合并后的单元格的样式
		titleRow.createCell(newHeaders.length - 1).setCellStyle(titleCellStyle);

		// 表头
		Row headRow = sheet.createRow(1);
		headRow.setHeight((short) 500);
		Cell headCell = null;
		String[] headInfo = null;
		// 处理excel表头
		for (int i = 0, len = newHeaders.length; i < len; i++) {
			headInfo = newHeaders[i].split("@");
			headCell = headRow.createCell(i);
			headCell.setCellValue(headInfo[0]);
			headCell.setCellStyle(titleCellStyle);
			// 设置列宽度
			setColumnWidth(i, headInfo, sheet);
		}
	}

	/**
	 * 写入表头信息
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值
	 *            </p>
	 * @param startIndex
	 *            起始行索引
	 */
	private void writeHeader(Workbook workbook, Sheet Sheet, String[] headers, int startIndex) {

		CellStyle headerCellStyle = createTitleCellStyle(workbook);

		// 表头
		Row headRow = Sheet.createRow(startIndex);
		headRow.setHeight((short) 500);
		Cell headCell = null;
		String[] headInfo = null;
		// 处理excel表头
		for (int i = 0, len = headers.length; i < len; i++) {
			headInfo = headers[i].split("@");
			headCell = headRow.createCell(i);
			headCell.setCellValue(headInfo[0]);
			headCell.setCellStyle(headerCellStyle);
			// 设置列宽度
			setColumnWidth(i, headInfo, Sheet);
		}
	}

	/**
	 * 头信息校验和处理
	 * 
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值
	 *            </p>
	 * @return 校验后的头信息
	 */
	private String[] headersHandler(String[] headers) {
		List<String> newHeaders = new ArrayList<String>();
		for (String string : headers) {
			if (StringUtils.isNotBlank(string)) {
				newHeaders.add(string);
			}
		}
		int size = newHeaders.size();

		return newHeaders.toArray(new String[size]);
	}

	/**
	 * 4.写入内容部分(默认从第三行开始写入)
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值
	 *            </p>
	 * @param dataList
	 *            要导出的数据集合
	 * @throws Exception
	 */
	private void writeContent(Workbook workbook, Sheet sheet, String[] headers, List<?> dataList)
			throws Exception {
		writeContent(workbook, sheet, headers, dataList, 2);
	}

	/**
	 * 4.写入内容部分
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值
	 *            </p>
	 * @param dataList
	 *            要导出的数据集合
	 * @param startIndex
	 *            起始行的索引
	 * @throws Exception
	 */
	private void writeContent(Workbook workbook, Sheet sheet, String[] headers, List<?> dataList,
			int startIndex) throws Exception {
		// 当没有数据的时候，把原来抛异常的方式修改成返回一个只有头信息，没有数据的空Excel
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		Row row = null;
		Cell cell = null;
		// 单元格的值
		Object cellValue = null;
		// 数据写入行索引
		int rownum = startIndex;
		// 单元格样式
		CellStyle cellStyle = createContentCellStyle(workbook);
		// 遍历集合，处理数据
		for (int j = 0, size = dataList.size(); j < size; j++) {
			row = sheet.createRow(rownum);
			for (int i = 0, len = headers.length; i < len; i++) {
				cell = row.createCell(i);
				cellValue =ReflectUtil.forceGetProperty(dataList.get(j), headers[i].split("@")[1]);
				cellValueHandler(cell, cellValue);
				cell.setCellStyle(cellStyle);
			}
			rownum++;
		}
	}

	
	/**
	 * 设置列宽度
	 * 
	 * @param i
	 *            列的索引号
	 * @param headInfo
	 *            表头信息，其中包含了用户需要设置的列宽
	 */
	private void setColumnWidth(int i, String[] headInfo, Sheet sheet) {
		if (headInfo.length < 3) {
			// 用户没有设置列宽，使用默认宽度
			sheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
			return;
		}
		if (StringUtils.isBlank(headInfo[2])) {
			// 使用默认宽度
			sheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
			return;
		}
		// 使用用户设置的列宽进行设置
		sheet.setColumnWidth(i, Integer.parseInt(headInfo[2]));
	}

	/**
	 * 单元格写值处理器
	 * 
	 * @param {{@link
	 * 			Cell}
	 * @param cellValue
	 *            单元格值
	 */
	private void cellValueHandler(Cell cell, Object cellValue) {
		// 判断cellValue是否为空，否则在cellValue.toString()会出现空指针异常
		if (cellValue == null) {
			cell.setCellValue("");
			return;
		}
		if (cellValue instanceof String) {
			cell.setCellValue((String) cellValue);
		} else if (cellValue instanceof Boolean) {
			cell.setCellValue((Boolean) cellValue);
		} else if (cellValue instanceof Calendar) {
			Calendar c = (Calendar) cellValue;
			cell.setCellValue((DateUtil.formatDate(c.getTime(),DateUtil.TIME_PATTERN)));
		}else if (cellValue instanceof Date) {
			Date c = (Date) cellValue;
			cell.setCellValue((DateUtil.formatDate(c,DateUtil.TIME_PATTERN)));
		} else if (cellValue instanceof Double) {
			cell.setCellValue((Double) cellValue);
		} else if (cellValue instanceof Integer || cellValue instanceof Long || cellValue instanceof Short
				|| cellValue instanceof Float) {
			cell.setCellValue((Double.parseDouble(cellValue.toString())));
		} else if (cellValue instanceof RichTextString) {
			cell.setCellValue((RichTextString) cellValue);
		}else{
			cell.setCellValue(cellValue.toString());
		}
	}

	/**
	 * 创建标题和表头单元格样式
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @return {@link CellStyle}
	 */
	private CellStyle createTitleCellStyle(Workbook workbook) {
		// 单元格的样式
		CellStyle cellStyle = workbook.createCellStyle();
		// 设置字体样式，改为变粗
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 13);
		cellStyle.setFont(font);
		// 单元格垂直居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置通用的单元格属性
		setCommonCellStyle(cellStyle);
		return cellStyle;
	}

	/**
	 * 创建内容单元格样式
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @return {@link CellStyle}
	 */
	private CellStyle createContentCellStyle(Workbook Workbook) {
		// 单元格的样式
		CellStyle cellStyle = Workbook.createCellStyle();
		// 设置字体样式，改为不变粗
		Font font = Workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);
		// 设置单元格自动换行
		cellStyle.setWrapText(true);
		// 单元格垂直居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 水平居中
		// cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置通用的单元格属性
		setCommonCellStyle(cellStyle);
		return cellStyle;
	}

	/**
	 * 设置通用的单元格属性
	 * 
	 * @param cellStyle
	 *            要设置属性的单元格
	 */
	private void setCommonCellStyle(CellStyle cellStyle) {
		// 居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置边框
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
	}

	/**
	 * 将生成的Excel输出到指定目录
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param filePath
	 *            文件输出目录，包括文件名（.xlsx）
	 */
	private String write2FilePath(Workbook workbook, String fileName) {
		FileOutputStream fileOut = null;
		try {
			String filePath = ExcelFileUtil.createExcelFile(fileName);
			fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			return filePath;
		} catch (Exception e) {
			throw new RuntimeException("将生成的Excel输出到指定目录失败");
		} finally {
			IOUtils.closeQuietly(fileOut);
			IOUtils.closeQuietly(workbook);
		}
	}

	/**
	 * 生成Excel，存放到指定目录
	 * 
	 * @param sheetName
	 *            sheet名称
	 * @param title
	 *            标题
	 * @param fileName
	 *            文件的名称
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值，默认4000
	 *            </p>
	 * @param dataList
	 *            要导出数据的集合
	 * @throws Exception
	 */
	public static String createExcel2FilePath(String sheetName, String title, String fileName, String[] headers,
			List<?> dataList) throws Exception {
		if (ArrayUtils.isEmpty(headers)) {
			throw new RuntimeException("表头不能为空");
		}
		ExportExcelUtils poiExcelUtil = new ExportExcelUtils();
		// 1.创建 workBook
		Workbook workBook = poiExcelUtil.createWorkBook();
		// 2.创建 Sheet
		Sheet sheet = poiExcelUtil.createSheet(workBook, sheetName);
		// 3.写入 head
		poiExcelUtil.writeHeader(workBook, sheet, headers, title);
		
		// 4,冻结单元格，多行
		poiExcelUtil.freezeHeader(2,sheet);
		
		// 5.写入内容
		poiExcelUtil.writeContent(workBook, sheet, headers, dataList);
		
		// 6.保存文件到fileName 对应的filePath中
		String filePath = poiExcelUtil.write2FilePath(workBook, fileName);
		return filePath;
	}

	/**
	 * 生成Excel，存放到指定目录
	 * 
	 * @param sheetName
	 *            sheet名称
	 * @param title
	 *            标题
	 * @param fileName
	 *            要导出的excel的名称
	 * @param mainDataFields
	 *            主表数据需要展示的字段集合
	 *            <p>
	 *            如{"字段1@beanFieldName1","字段2@beanFieldName2",字段3@beanFieldName3
	 *            "}
	 *            </p>
	 * @param mainData
	 *            主表数据
	 * @param headers
	 *            列标题，数组形式
	 *            <p>
	 *            如{"列标题1@beanFieldName1@columnWidth",
	 *            "列标题2@beanFieldName2@columnWidth",
	 *            "列标题3@beanFieldName3@columnWidth"}
	 *            </p>
	 *            <p>
	 *            其中参数@columnWidth可选，columnWidth为整型数值，默认4000
	 *            </p>
	 * @param detailDataList
	 *            要导出数据的集合
	 * @param needExportDate
	 *            是否需要显示“导出日期”
	 * @throws Exception
	 */
	public static String createExcel2FilePath(String sheetName, String title, String fileName, String[] mainDataFields,
			Object mainData, String[] headers, List<?> detailDataList, final boolean needExportDate) throws Exception {
		if (ArrayUtils.isEmpty(headers)) {
			throw new IllegalArgumentException("headers");
		}
		if (ArrayUtils.isEmpty(mainDataFields)) {
			throw new IllegalArgumentException("mainDataFields");
		}
		if (mainData == null) {
			throw new IllegalArgumentException("mainData");
		}

		ExportExcelUtils poiExcelUtil = new ExportExcelUtils();
		// 1.创建 Workbook
		Workbook workbook = poiExcelUtil.createWorkBook();
		// 2.创建 Sheet
		Sheet sheet = poiExcelUtil.createSheet(workbook, sheetName);
		// 3.写标题
		headers = poiExcelUtil.writeTitle(workbook, sheet, headers, title);
		// 4.写主表（mainData）数据
		int usedRows = poiExcelUtil.writeMainData(workbook, sheet, headers.length, mainDataFields, mainData, 1,
				needExportDate);
		// 5.写入 head 这里默认将title写入到了第一行，所以header的起始行索引为usedRows + 1
		poiExcelUtil.writeHeader(workbook, sheet, headers, usedRows + 1);
		
		// 6,冻结单元格，多行
		poiExcelUtil.freezeHeader(usedRows+2,sheet);
		
		// 7,写从表（detailDataList）内容
		poiExcelUtil.writeContent(workbook, sheet, headers, detailDataList, usedRows + 2);
		
		// 8.保存文件到fileName对应的filePath中
		String filePath = poiExcelUtil.write2FilePath(workbook, fileName);
		return filePath;
	}

	private void freezeHeader(int freezeRow,Sheet sheet) {
		sheet.createFreezePane(0, freezeRow);
	}

	/**
	 * 写标题
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            表头
	 * @param title
	 *            标题
	 * @return 去除无效表头后的新表头集合
	 */
	private String[] writeTitle(Workbook Workbook, Sheet Sheet, String[] headers, String title) {
		return writeTitle(Workbook, Sheet, headers, title, 0);
	}
	
	private RichTextString createRichTextStringCell(Cell cell,String value){
		if(cell instanceof XSSFCell){
			return new XSSFRichTextString(value);
		}else{
			return new HSSFRichTextString(value);
		}
	}

	/**
	 * 写标题
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param headers
	 *            表头
	 * @param title
	 *            标题
	 * @param titleRowIndex
	 *            标题行的索引
	 * @return 去除无效表头后的新表头集合
	 */
	private String[] writeTitle(Workbook Workbook, Sheet Sheet, String[] headers, String title,
			int titleRowIndex) {
		// 头信息处理
		String[] newHeaders = headersHandler(headers);

		// 初始化标题和表头单元格样式
		CellStyle titleCellStyle = createTitleCellStyle(Workbook);
		// 标题栏
		Row titleRow = Sheet.createRow(titleRowIndex);
		titleRow.setHeight((short) 500);
		Cell titleCell = titleRow.createCell(0);
		
		// 设置标题文本
		titleCell.setCellValue(createRichTextStringCell(titleCell, title));
		// 设置单元格样式
		titleCell.setCellStyle(titleCellStyle);

		// 处理单元格合并，四个参数分别是：起始行，终止行，起始列，终止列
		Sheet.addMergedRegion(
				new CellRangeAddress(titleRowIndex, titleRowIndex, (short) 0, (short) (newHeaders.length - 1)));

		// 设置合并后的单元格的样式
		titleRow.createCell(newHeaders.length - 1).setCellStyle(titleCellStyle);

		return newHeaders;
	}
	

	/**
	 * 写主表（mainData）数据
	 * 
	 * @param Workbook
	 *            {@link Workbook}
	 * @param Sheet
	 *            {@link Sheet}
	 * @param columnSize
	 *            列数
	 * @param mainDataFields  
	 *            主表数据需要展示的字段集合 {"字段1@beanFieldName1","字段2@beanFieldName2",字段3@beanFieldName3"}
	 *            
	 * @param mainData
	 *            主表数据对象
	 * @param startIndex
	 *            起始行索引
	 * @param needExportDate
	 *            是否需要输出“导出日期”
	 * @return 主表数据使用了多少行
	 * @throws Exception
	 */
	private int writeMainData(Workbook workbook, Sheet sheet, int columnSize, String[] mainDataFields,
			Object mainData, int startIndex, boolean needExportDate) throws Exception {
		
		//要提前写入的数据
		LinkedHashMap<String, Object> mainDataMap = new LinkedHashMap<>();
		
		for (String mainDataField : mainDataFields) {
			String[] fieldsArray = mainDataField.split("@");
			mainDataMap.put(fieldsArray[1], ReflectUtil.forceGetProperty(mainData,fieldsArray[1]));
		}
		if(needExportDate){
			mainDataMap.put("now", DateUtil.formatDate(new Date(), DateUtil.TIME_PATTERN));
			String[] temp = new String[mainDataFields.length+1];
			System.arraycopy(mainDataFields, 0, temp, 0, mainDataFields.length);
			temp[mainDataFields.length]="导出日期@now";
			mainDataFields = temp;
		}
		
		// 1. 要展示的主字段有多少个
		int mainDataSize = mainDataFields.length;
		
		// 2, 每列可以显示的字段数量
		int filedSizeInOneRow = mainDataSize * 2 < columnSize ? mainDataSize : columnSize / 2;
		
		// 3， 获取需要展示的行数
		int needRows = getNeedRow(mainDataSize,filedSizeInOneRow);

		Row row = null;
		Cell cell4FiledName = null;
		Cell cell4Value = null;

		// 数据写入行索引
		int rownum = startIndex;
		
		// 单元格样式
		CellStyle cellStyle = createContentCellStyle(workbook);
		
		// 每一行的单元格的索引
		int cellIndex = 0;
		// 主表字段的数组索引
		int fieldIndex = 0;
		
		for (int i = 0; i < needRows; i++) {
			row = sheet.createRow(rownum);
			for (int j = 0; j < filedSizeInOneRow; j++) {
				if (fieldIndex == mainDataSize) {
					break;
				}
				// 取出对应索引的主表字段，然后切割成字符串数组
				String[] fieldsArray = mainDataFields[fieldIndex].split("@");
				fieldIndex++;
				// 每个字段对应的单元格的索引
				cellIndex = j * 2;
				// 字段描述的单元格
				cell4FiledName = row.createCell(cellIndex);
				cellValueHandler(cell4FiledName, fieldsArray[0]);
				cell4FiledName.setCellStyle(cellStyle);
				// 字段值的单元格
				cell4Value = row.createCell(cellIndex + 1);
				cellValueHandler(cell4Value, ReflectUtil.forceGetProperty(mainDataMap, fieldsArray[1]));
				cell4Value.setCellStyle(cellStyle);
				// 每一列的最后一行，如果还有空格，则进行合并操作
				if(j == filedSizeInOneRow - 1&&columnSize-1>cellIndex + 1){
					int startCellIndex = cellIndex + 1;
					// 处理单元格合并，四个参数分别是：起始行，终止行，起始列，终止列
					sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, startCellIndex,columnSize - 1));
					// 设置合并后的单元格的样式
					setMergedCellStyle(row, startCellIndex, columnSize - 1, cellStyle);
				}
			}
			rownum++;
		}
		return needRows;
	}

	private int getNeedRow(int mainDataSize, int filedSizeInOneRow) {
		double a = Double.valueOf(mainDataSize);
		double b = Double.valueOf(filedSizeInOneRow);
		double c = Math.ceil(a/b);
		return Double.valueOf(c).intValue();
	}

	/**
	 * 设置合并后的单元格的样式
	 * 
	 * @param row
	 *            {@link Row}
	 * @param beginCellIdnex
	 *            合并开始的单元格
	 * @param endCellIndex
	 *            合并结束的单元格
	 * @param cellStyle
	 *            {@link CellStyle}
	 */
	private void setMergedCellStyle(Row row, int beginCellIdnex, int endCellIndex, CellStyle cellStyle) {
		for (int i = beginCellIdnex + 1; i <= endCellIndex; i++) {
			row.createCell(i).setCellStyle(cellStyle);
		}
	}



}