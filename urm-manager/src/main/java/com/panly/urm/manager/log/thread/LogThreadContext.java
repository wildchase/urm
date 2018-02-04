package com.panly.urm.manager.log.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panly.urm.manager.right.dao.UrmOperLogDao;
import com.panly.urm.manager.right.entity.UrmOperLog;

@Component
public class LogThreadContext {
	
	private static Logger logger = LoggerFactory.getLogger(LogThreadContext.class);
	
	private static BlockingQueue<UrmOperLog> logQueue = new LinkedBlockingQueue<>(1000);
	
	@Autowired
	private UrmOperLogDao urmOperLogDao;
	
	@PostConstruct
	private void init(){
		for (int i = 0; i < 2; i++) {
			LogThread l = new LogThread(logQueue,urmOperLogDao);
			l.setDaemon(true);
			l.setName("LogThread"+i);
			l.start();
			logger.info(l.getName()+"线程启动");
		}
	}
	
	public static void addLog(UrmOperLog log ){
		try {
			logQueue.offer(log, 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("添加日志失败",e);
		}
	}
	
	
	
	
	
	
}
