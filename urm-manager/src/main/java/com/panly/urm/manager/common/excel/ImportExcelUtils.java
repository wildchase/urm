package com.panly.urm.manager.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 功能: [POI实现把Excel数据导入到数据库] 作者: JML 版本: 1.0
 * </p>
 * 
 * @project core
 * @class ImportExcelUtils
 * @author a@panly.me
 * @date 2017年6月15日下午4:49:42
 */
public class ImportExcelUtils {

	/** 数字格式化 */
	private static NumberFormat format = NumberFormat.getInstance();

	// 正则表达式 用于匹配属性的第一个字母
	private static final String REGEX = "[a-zA-Z]";

	/**
	 * 功能: Excel数据导入到数据库 参数: excelPath[Excel表的所在路径] 参数: startRow[从第几行开始] 参数: 参数:
	 * clazz[要返回的对象集合的类型]
	 */
	public static <T> List<T> importExcel(String originUrl, int startRow,
			Class<T> clazz) throws IOException {
		// 是否打印提示信息
		boolean showInfo = false;
		return doImportExcel(originUrl, startRow, showInfo, clazz, null);
	}

	/**
	 * 功能: Excel数据导入到数据库 参数: excelPath[Excel表的所在路径] 参数: startRow[从第几行开始] 参数: 参数:
	 * clazz[要返回的对象集合的类型] 参数 : params
	 * [beanFieldName，beanFieldName]，每列数据对应对象field
	 */
	public static <T> List<T> importExcel(String originUrl, int startRow,
			Class<T> clazz, String[] params) throws IOException {
		// 是否打印提示信息
		boolean showInfo = false;
		return doImportExcel(originUrl, startRow, showInfo, clazz, params);
	}

	/**
	 * 功能:真正实现导入
	 */
	private static <T> List<T> doImportExcel(String excelPath, int startRow,
			boolean showInfo, Class<T> clazz, String[] params)
			throws IOException {

		// 判断文件是否存在
		File file = new File(excelPath);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}
		Workbook wb = null;
		FileInputStream is = null;
		List<Row> rowList = new ArrayList<Row>();
		try {
			is = FileUtils.openInputStream(file);
			// 去读Excel
			wb = WorkbookFactory.create(is);

			Sheet sheet = wb.getSheetAt(0);
			// 获取最后行号
			int lastRowNum = sheet.getLastRowNum();
			if (lastRowNum > 0) { // 如果>0，表示有数据
				out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：", showInfo);
			}
			Row row = null;
			// 实际上存在的行数
			int rowNum = sheet.getPhysicalNumberOfRows();
			// 循环读取
			for (int i = startRow - 1; i <= rowNum; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					rowList.add(row);
					out("第" + (i + 1) + "行：", showInfo, false);
					// 获取每一单元格的值
					for (int j = 0; j < row.getLastCellNum(); j++) {
						String value = getCellValue(row.getCell(j));
						if (!value.equals("")) {
							out(value + " | ", showInfo, false);
						}
					}
					out("", showInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(wb);
		}
		if (params == null) {
			return returnObjectList(rowList, clazz);
		} else {
			return returnObjectList(rowList, clazz, params);
		}
	}

	/**
	 * 功能:获取单元格的值
	 */
	private static String getCellValue(Cell cell) {
		String result = "";
		if (cell == null) {
			return "";
		}
		CellType cellType = cell.getCellTypeEnum();
		if (cellType != null) {
			switch (cellType) {
			case STRING:
				result = cell.getStringCellValue();
				break;
			case NUMERIC:
				result = String.valueOf(
						format.format(cell.getNumericCellValue())).replace(",",
						"");
				break;
			case BOOLEAN:
				result = String.valueOf(cell.getBooleanCellValue());
				break;
			case FORMULA:
				result = cell.getCellFormula();
				break;
			case ERROR:
				result = String.valueOf(cell.getErrorCellValue());
				break;
			case BLANK:
				break;
			default:
				break;
			}
		}
		return result;
	}

	/**
	 * 功能:返回指定的对象集合
	 */
	private static <T> List<T> returnObjectList(List<Row> rowList,
			Class<T> clazz, String[] params) {
		List<T> list = null;
		T t = null;
		try {
			list = new ArrayList<T>();
			for (Row row : rowList) {
				t = clazz.newInstance();
				for (int i = 0; i < params.length; i++) {
					String beanFieldName = params[i];
					String value = getCellValue(row.getCell(i));
					setAttrributeValue(t, beanFieldName, value);
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 功能:返回指定的对象集合
	 */
	private static <T> List<T> returnObjectList(List<Row> rowList,
			Class<T> clazz) {
		List<T> list = null;
		T t = null;
		String attribute = null;
		String value = null;
		int j = 0;
		try {
			list = new ArrayList<T>();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Row row : rowList) {
				j = 0;
				t = clazz.newInstance();
				for (Field field : declaredFields) {
					attribute = field.getName().toString();
					value = getCellValue(row.getCell(j));
					setAttrributeValue(t, attribute, value);
					j++;
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 功能:给指定对象的指定属性赋值
	 */
	private static void setAttrributeValue(Object obj, String attribute,
			String value) {
		// 得到该属性的set方法名
		String method_name = convertToMethodName(attribute, obj.getClass(),
				true);
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			/**
			 * 因为这里只是调用bean中属性的set方法，属性名称不能重复 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
			 * （注：在java中，锁定一个方法的条件是方法名及参数）
			 */
			if (method.getName().equals(method_name)) {
				Class<?>[] parameterC = method.getParameterTypes();
				try {
					/**
					 * 如果是(整型,浮点型,布尔型,字节型,时间类型), 按照各自的规则把value值转换成各自的类型
					 * 否则一律按类型强制转换(比如:String类型)
					 */
					if (parameterC[0] == int.class
							|| parameterC[0] == java.lang.Integer.class) {
						if (value.indexOf(".") != -1) {
							value = value.substring(0, value.lastIndexOf("."));
						}
						method.invoke(obj, Integer.valueOf(value));
						break;
					} else if (parameterC[0] == float.class
							|| parameterC[0] == java.lang.Float.class) {
						method.invoke(obj, Float.valueOf(value));
						break;
					} else if (parameterC[0] == double.class
							|| parameterC[0] == java.lang.Double.class) {
						method.invoke(obj, Double.valueOf(value));
						break;
					} else if (parameterC[0] == byte.class
							|| parameterC[0] == java.lang.Byte.class) {
						method.invoke(obj, Byte.valueOf(value));
						break;
					} else if (parameterC[0] == boolean.class
							|| parameterC[0] == java.lang.Boolean.class) {
						method.invoke(obj, Boolean.valueOf(value));
						break;
					} else if (parameterC[0] == java.util.Date.class) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date date = null;
						try {
							date = sdf.parse(value);
						} catch (Exception e) {
							e.printStackTrace();
						}
						method.invoke(obj, date);
						break;
					} else {
						method.invoke(obj, parameterC[0].cast(value));
						break;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 功能:根据属性生成对应的set/get方法
	 */
	private static String convertToMethodName(String attribute,
			Class<?> objClass, boolean isSet) {
		/** 通过正则表达式来匹配第一个字符 **/
		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(attribute);
		StringBuilder sb = new StringBuilder();
		/** 如果是set方法名称 **/
		if (isSet) {
			sb.append("set");
		} else {
			/** get方法名称 **/
			try {
				Field attributeField = objClass.getDeclaredField(attribute);
				/** 如果类型为boolean **/
				if (attributeField.getType() == boolean.class
						|| attributeField.getType() == Boolean.class) {
					sb.append("is");
				} else {
					sb.append("get");
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		/** 针对以下划线开头的属性 **/
		if (attribute.charAt(0) != '_' && m.find()) {
			sb.append(m.replaceFirst(m.group().toUpperCase()));
		} else {
			sb.append(attribute);
		}
		return sb.toString();
	}

	/**
	 * 功能:输出提示信息(普通信息打印)
	 */
	private static void out(String info, boolean showInfo) {
		if (showInfo) {
			System.out.print(info + (showInfo ? "\n" : ""));
		}
	}

	/**
	 * 功能:输出提示信息(同一行的不同单元格信息打印)
	 */
	private static void out(String info, boolean showInfo, boolean nextLine) {
		if (showInfo) {
			if (nextLine) {
				System.out.print(info + (showInfo ? "\n" : ""));
			} else {
				System.out.print(info);
			}
		}
	}

	/**
	 * 根据文件路径读取excel文件，默认读取第0个sheet
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount) throws Exception {
		return readExcel(excelPath, skipRows, columnCount, 0, null);
	}

	/**
	 * 根据文件路径读取excel文件的指定sheet
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @param sheetNo
	 *            要读取的sheet的索引，从0开始
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount, int sheetNo) throws Exception {
		return readExcel(excelPath, skipRows, columnCount, sheetNo, null);
	}

	/**
	 * 根据文件路径读取excel文件的指定sheet，并封装空值单位各的坐标，默认读取第0个sheet
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @param noneCellValuePositionList
	 *            存储空值的单元格的坐标，每个坐标以x-y的形式拼接，如2-5表示第二行第五列
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount, List<String> noneCellValuePositionList)
			throws Exception {
		return readExcel(excelPath, skipRows, columnCount, 0,
				noneCellValuePositionList);
	}

	/**
	 * 根据文件路径读取excel文件的指定sheet，并封装空值单位各的坐标，默认读取第0个sheet
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @param columnNumberForSkipValueValidateSet
	 *            不需要做空值验证的列的索引集合
	 * @param noneCellValuePositionList
	 *            存储空值的单元格的坐标，每个坐标以x-y的形式拼接，如2-5表示第二行第五列
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount, Set<Integer> columnNumberForSkipValueValidateSet,
			List<String> noneCellValuePositionList) throws Exception {
		return readExcel(excelPath, skipRows, columnCount, 0,
				columnNumberForSkipValueValidateSet, noneCellValuePositionList);
	}

	/**
	 * 根据文件路径读取excel文件的指定sheet，并封装空值单位各的坐标
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @param sheetNo
	 *            要读取的sheet的索引，从0开始
	 * @param noneCellValuePositionList
	 *            存储空值的单元格的坐标，每个坐标以x-y的形式拼接，如2-5表示第二行第五列
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount, int sheetNo, List<String> noneCellValuePositionList)
			throws Exception {
		return readExcel(excelPath, skipRows, columnCount, sheetNo, null,
				noneCellValuePositionList);
	}

	/**
	 * 根据文件路径读取excel文件的指定sheet，并封装空值单位各的坐标
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param skipRows
	 *            需要跳过的行数
	 * @param columnCount
	 *            列数量
	 * @param sheetNo
	 *            要读取的sheet的索引，从0开始
	 * @param columnNumberForSkipValueValidateSet
	 *            不需要做空值验证的列的索引集合
	 * @param noneCellValuePositionList
	 *            存储空值的单元格的坐标，每个坐标以x-y的形式拼接，如2-5表示第二行第五列
	 * @return List<String[]> 集合中每一个元素是一个数组，按单元格索引存储每个单元格的值，一个元素可以封装成一个需要的java
	 *         bean
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excelPath, int skipRows,
			int columnCount, int sheetNo,
			Set<Integer> columnNumberForSkipValueValidateSet,
			List<String> noneCellValuePositionList) throws Exception {
		if (StringUtils.isBlank(excelPath)) {
			return new ArrayList<String[]>();
		}
		List<String[]> list = new ArrayList<String[]>();
		FileInputStream is = null;
		Workbook wb = null;
		try {
			File file = new File(excelPath);
			is = FileUtils.openInputStream(file);
			wb = WorkbookFactory.create(is);

			Sheet sheet = wb.getSheetAt(sheetNo);
			// 得到总共的行数
			int rowNum = sheet.getPhysicalNumberOfRows();
			for (int i = skipRows; i < rowNum; i++) {
				String[] vals = new String[columnCount];
				Row row = sheet.getRow(i);
				if (null == row) {
					continue;
				}
				for (int j = 0; j < columnCount; j++) {
					Cell cell = row.getCell(j);
					String val = getCellValue(cell);
					// 没有需要跳过校验的列索引
					if (CollectionUtils
							.isEmpty(columnNumberForSkipValueValidateSet)) {
						if (noneCellValuePositionList != null
								&& StringUtils.isBlank(val)) {
							// 封装空值单元格的坐标
							noneCellValuePositionList.add((i + 1) + "-" + j);
						}
					} else {
						// 如果需要校验空值的单元格、当前列索引不在需要跳过校验的索引集合中
						if (noneCellValuePositionList != null
								&& StringUtils.isBlank(val)
								&& !columnNumberForSkipValueValidateSet
										.contains(j)) {
							// 封装空值单元格的坐标
							noneCellValuePositionList.add((i + 1) + "-" + j);
						}
					}

					vals[j] = val;
				}
				list.add(vals);
			}
		} catch (Exception e) {
			throw new RuntimeException("Excel解析失败");
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(is);
		}
		return list;
	}
	
}