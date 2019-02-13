package com.github.yu_jay.fileChangeListenInEclipse;

import org.apache.log4j.Logger;
import org.eclipse.core.internal.events.ResourceDelta;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.github.yu_jay.fileChangeListenInEclipse.util.LogUtil;
import com.github.yu_jay.fileChangeListenInEclipse.util.WorkPlace;
import com.github.yu_jay.jay_compiler.act.FileChangeInfo;
import com.github.yu_jay.jay_compiler.act.WebpackJsCompiler;
import com.github.yu_jay.jay_compiler.pojo.FileChangeType;
import com.github.yu_jay.jay_compiler.util.Judger;

/**
 * 入口
 * @author yujie
 *
 */
public class App implements IStartup {
	
	private static final Logger log = LogUtil.getLogger(App.class);
	
	/**
	 * webpack 编译器
	 */
	private static final WebpackJsCompiler webpackJsCompiler = new WebpackJsCompiler();

	@Override
	public void earlyStartup() {
		
		log.debug("开始加载插件");
		
		beforeEarlyStartup();
		
		/**
		 * 判断webpack 配置是否正确，如果正确将注册核心监听事件
		 */
		if(Judger.checkConfig(webpackJsCompiler.getConfig())) {
			/**
			 * 注册核心监听事件
			 */
			JavaCore.addElementChangedListener(new IElementChangedListener() {
				
				@Override
				public void elementChanged(ElementChangedEvent event) {
					
					if(event != null && null != PlatformUI.getWorkbench().getActiveWorkbenchWindow()) {
						FileChangeInfo info = convertByWindow(event);
						if(null != info) {
							log.info(info);
							
							/**
							 * 执行一个js编译器
							 */
							if(Judger.checkProject(webpackJsCompiler.getConfig(), info)
									&& Judger.mateContext(webpackJsCompiler.getConfig(), info)) {
								webpackJsCompiler.compile(info);   //开始编译js文件
							}else {
								log.debug("配置不匹配，不进行编译");
							}
							
						}
					}
				}
			}, 1);
		}else {
			log.info("webpack配置文件不正确，无法绑定监听事件");
		}
		
		afterEarlyStartup();
		
		log.debug("插件加载完成");
		
	}
	
	/**
	 * early startup 之前执行
	 */
	private void beforeEarlyStartup() {
		log.debug("开始执行:beforeEarlyStartup");
		//初始化工作空间
		WorkPlace.init();
		log.debug("初始化空间完成");
		//初始化编译器
		webpackJsCompiler.setWorkPlace(new WorkPlace());
		String configPath = WorkPlace.findConfigPath("config.properties");
		if(null != configPath) {
			webpackJsCompiler.init(configPath);
		}else {
			log.debug("configPath: " + configPath);
		}
		log.debug("初始化编译器完成");
		log.debug("结束执行:beforeEarlyStartup");
	}
	
	/**
	 * early startup 之后执行
	 */
	private void afterEarlyStartup() {
		log.debug("开始执行：afterEarlyStartup");
		log.debug("结束执行：afterEarlyStartup");
	}
	
	/**
	 * 转化为文件修改描述信息类
	 * @param event
	 * @return
	 */
	private FileChangeInfo convertByWindow(ElementChangedEvent event) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorPart editor = page.getActiveEditor();
        if(null != editor) {
        	FileEditorInput input = (FileEditorInput) editor.getEditorInput();
        	IFile file = input.getFile();
        	return new FileChangeInfo(getKind(event.getDelta().getKind()), file.getFileExtension(), file.getName(), 
        			file.getFullPath().toString(), file.getLocation().toString(), file.getProject().getName(), 
        			file.getProject().getLocation().toString());
//        	return new FileChangeInfo(getKind(event.getDelta().getKind()), file.getFullPath().toString(), 
//        			file.getFileExtension(), file.getName(), input.getStorage().toString(), file.getLocation().toString(), 
//        			file.getProject().getName(), file.getProject().getLocation().toString());
        }
        return null;
	}
	
	@SuppressWarnings("restriction")
	private FileChangeType getKind(int code) {
		switch(code) {
			case ResourceDelta.ADDED:
				return FileChangeType.ADD;
			case ResourceDelta.CHANGED:
				return FileChangeType.CHANGE;
			case ResourceDelta.REMOVED:
				return FileChangeType.REMOVE;
		}
		return null;
	}

}
