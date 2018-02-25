package com.panly.urm.auth.log.thread;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panly.urm.auth.dao.AuthLogDao;
import com.panly.urm.auth.model.AuthLog;

public class AuthLogThread extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(AuthLogThread.class);
	
	private BlockingQueue<AuthLog> queue;

	private AuthLogDao authLogDao;

	public AuthLogThread(BlockingQueue<AuthLog> queue,AuthLogDao authLogDao) {
		this.queue = queue;
		this.authLogDao = authLogDao;
	}

	@Override
	public void run() {
		while (true) {
			try {
				AuthLog log = queue.take();
				logger.info("执行操作插入");
				authLogDao.insert(log);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

}
