package com.panly.urm.manager.log.thread;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panly.urm.manager.right.dao.UrmOperLogDao;
import com.panly.urm.manager.right.entity.UrmOperLog;

public class LogThread extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(LogThread.class);
	
	private BlockingQueue<UrmOperLog> queue;

	private UrmOperLogDao urmOperLogDao;

	public LogThread(BlockingQueue<UrmOperLog> queue,UrmOperLogDao urmOperLogDao) {
		this.queue = queue;
		this.urmOperLogDao = urmOperLogDao;
	}

	@Override
	public void run() {
		while (true) {
			try {
				UrmOperLog log = queue.take();
				logger.info("执行操作插入");
				urmOperLogDao.insert(log);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

}
