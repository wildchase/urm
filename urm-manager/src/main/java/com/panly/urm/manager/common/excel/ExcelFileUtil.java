package com.panly.urm.manager.common.excel;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;


public class ExcelFileUtil {

	private final static String tempDir = System.getProperty("java.io.tmpdir") + "excel/";
	
	private static ScheduledExecutorService ex = new ScheduledThreadPoolExecutor(1);
	
	private final static long DELETE_TIME = 15*60*1000;

	static {
		try {
			File f = new File(tempDir);
			FileUtils.forceMkdir(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Runnable runnable = new Runnable() {
			public void run() {
				File directory = new File(tempDir);
				
				FileFilter fileFilter = new FileFilter() {
					@Override
					public boolean accept(File file) {
						long mod = System.currentTimeMillis()- file.lastModified();
						if(mod>DELETE_TIME){
							return true;
						}else{
							return false;
						}
					}
				};
				
				if(directory.isDirectory()){
					File[] files =directory.listFiles(fileFilter);
					if(files!=null){
						for (int i = 0; i < files.length; i++) {
							try {
								FileUtils.forceDelete(files[i]);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		};
		ex.scheduleWithFixedDelay(runnable, 5*60*1000, DELETE_TIME, TimeUnit.MILLISECONDS);
	}

	public static String createExcelFile(String fileName) {
		String filePath = createExcelFile(fileName, null);
		return filePath;
	}
	
	public static String createExcelFile(String fileName,Integer i) {
		String filePath ="";
		if(i==null){
			filePath = tempDir+fileName+".xlsx";
		}else{
			filePath = tempDir+fileName+i+".xlsx";
		}
		File f = new File(filePath);
		if(f.exists()){
			if(i==null){
				i=0;
			}else{
				i=i+1;
			}
			return createExcelFile(fileName,i);
		}else{
			return filePath;
		}
	}

}
