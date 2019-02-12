package com.github.yu_jay.fileChangeListenInEclipse;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.eclipse.ui.IStartup;

import com.github.yu_jay.jay_common.act.Log4jLogger;
import com.github.yu_jay.jay_common.iter.ILogger;

/**
 * 插件入口文件
 * @author yujie
 *
 */
public class App implements IStartup {
	
	private Logger log = Logger.getLogger(App.class);
	
	static {
		String logPath = "G:/Template/log4j.properties";
        ILogger logger = new Log4jLogger(logPath);
	}

	@Override
	public void earlyStartup() {
		
		
		
		String code = Charset.defaultCharset().name();
		System.out.println("----code---: " + code);
		
		System.out.println("流浪地球");
		log.debug("流浪地球。");
	}

}
