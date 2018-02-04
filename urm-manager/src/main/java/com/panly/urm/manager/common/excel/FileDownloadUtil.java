package com.panly.urm.manager.common.excel;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author : JiaYun
 * @Description : FileDownload
 * @date : 2018/1/11 11:35
 */
public class FileDownloadUtil {

	/**
	 * 下载服务器文件
	 * 
	 * @param response
	 *            response
	 * @param info
	 *            info 文件信息
	 */
	public static void exportToExcel(HttpServletResponse response, String sheetName, String title,
			String fileName, String[] headers, List<?> dataList) {
		InputStream input = null;
		try {
			String excel2FilePath = ExportExcelUtils.createExcel2FilePath(
					sheetName, title, fileName, headers, dataList);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(fileName, "UTF-8"));
			input = new BufferedInputStream(new FileInputStream(excel2FilePath));
			IOUtils.copy(input, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(input);
		}
	}

}
