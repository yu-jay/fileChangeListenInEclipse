package com.github.yu_jay.fileChangeListenInEclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.ui.IStartup;

import com.github.yu_jay.jay_common.act.Log4jLogger;

/**
 * 入口
 * @author yujie
 *
 */
public class App implements IStartup {
	
	private static final Logger log = Logger.getLogger(App.class);

	@Override
	public void earlyStartup() {
		
		String path = "G:/Template/log4j.properties";
		
		Log4jLogger loggerConfiger = new Log4jLogger(path);
		
		//Logger l = loggerConfiger.getLogger(App.class);
		log.debug("启动了------------");
		
		Enumeration e = log.getAllAppenders();
		
		System.out.println(e);
		
	}

}
