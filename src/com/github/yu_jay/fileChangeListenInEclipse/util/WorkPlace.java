package com.github.yu_jay.fileChangeListenInEclipse.util;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Platform;

import com.github.yu_jay.jay_compiler.iter.IWorkPalce;
import com.github.yu_jay.jay_compiler.pojo.DocumentOrganization;

/**
 * 工作空间对象
 * @author yujie
 *
 */
public class WorkPlace implements IWorkPalce {
	
	/**
	 * 工作空间路径
	 */
	public static final String WORK_PLACE_PATH;
	
	/**
	 * 工作空间插件路径
	 */
	public static final String WORK_PLACE_PLUG_PATH;
	
	/**
	 * 插件安装位置
	 */
	public static final String PLUG_LOCATION;
	
	/**
	 * 插件名
	 */
	public static final String PLUG_NAME = "jayu.fileChangeListenInEclipse";
	
	private static final Logger log = Logger.getLogger(WorkPlace.class);
	
	static {
		WORK_PLACE_PATH = Platform.getInstanceLocation().getURL().getPath();
		System.out.println("init workPlacePath: " + WORK_PLACE_PATH);
		WORK_PLACE_PLUG_PATH = WORK_PLACE_PATH + ".metadata/.plugins/"
				+ PLUG_NAME + "/";
		System.out.println("init workPlacePlugPath: " + WORK_PLACE_PLUG_PATH);
		PLUG_LOCATION = Platform.getInstallLocation().getURL().getPath() + 
				"plugins/" + PLUG_NAME + "/";
		System.out.println("init plugLocation: " + PLUG_LOCATION);
	}
	
	/**
	 * 初始化
	 */
	public static void init() {
		File f1 = new File(WORK_PLACE_PLUG_PATH);
		if(!f1.exists()) {
			log.debug("create workPlacePlugPath: " + WORK_PLACE_PLUG_PATH);
			f1.mkdirs();
		}else {
			log.debug("init2 workPlacePlugPath: " + WORK_PLACE_PLUG_PATH);
		}
		File f2 = new File(PLUG_LOCATION);
		if(!f2.exists()) {
			log.debug("create plugLocation: " + PLUG_LOCATION);
			f2.mkdirs();
		}else {
			log.debug("init2 plugLocation: " + PLUG_LOCATION);
		}
	}
	
	/**
	 * 通过文件名查找配置文件，首先去工作空间找，如果找不到去eclipse安装路径下面找
	 * @param name 文件名
	 * @return
	 */
	public static String findConfigPath(String name) {
		if(null != haveFile(WORK_PLACE_PLUG_PATH + name)) {
			return WORK_PLACE_PLUG_PATH + name;
		}
		if(null != haveFile(PLUG_LOCATION + name)) {
			return PLUG_LOCATION + name;
		}
		return null;
	}

	@Override
	public String getWebAbsoluteOutFile(DocumentOrganization doc) {
		if(null != doc.getWebOutPath() 
				&& null != doc.getOutName()) {
			return WORK_PLACE_PATH + ".metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/" + 
					doc.getWebpackConfig().getRecentProjectName() + doc.getWebOutPath() + doc.getOutName();
		}
		return null;
	}
	
	private static String haveFile(String path) {
		if(new File(path).exists()) {
			return path;
		}
		log.debug(path + "is null");
		return null;
	}

}
