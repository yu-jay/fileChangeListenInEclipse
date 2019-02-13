package com.github.yu_jay.fileChangeListenInEclipse.util;

import org.apache.log4j.Logger;

import com.github.yu_jay.jay_common.act.Log4jLogger;

/**
 * 日志管理类
 * @author yujie
 *
 */
public class LogUtil {
	
	private static Log4jLogger logManager;
	
	static {
		String logConfigPath = WorkPlace.findConfigPath("log4j.properties");
		System.out.println("logConfigPath: " + logConfigPath);
		if(null != logConfigPath) {
			logManager = new Log4jLogger(logConfigPath);
			Logger log = logManager.getLogger(LogUtil.class);
			log.debug("logger加载完成");
		}
	}

	public static Logger getLogger(Class<?> clazz) {
		return Logger.getLogger(clazz);
	}
	
}
