package com.panly.urm.auth.log.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panly.urm.auth.dao.AuthLogDao;
import com.panly.urm.auth.model.AuthLog;


@Component
public class AuthLogThreadContext {
	
	private static Logger logger = LoggerFactory.getLogger(AuthLogThreadContext.class);
	
	private static BlockingQueue<AuthLog> logQueue = new LinkedBlockingQueue<>(1000);
	
	@Autowired
	private AuthLogDao authLogDao;
	
	@PostConstruct
	private void init(){
		for (int i = 0; i < 2; i++) {
			AuthLogThread l = new AuthLogThread(logQueue,authLogDao);
			l.setDaemon(true);
			l.setName("LogThread"+i);
			l.start();
			logger.info(l.getName()+"线程启动");
		}
	}
	
	public static void addLog(AuthLog log ){
		try {
			logQueue.offer(log, 500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("添加日志失败",e);
		}
	}
	
	
}
