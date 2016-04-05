package zx.soft.adt.api;

/**
 * 初始化环境资源
 */
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import zx.soft.adt.utils.Constant;
import zx.soft.utils.config.ConfigUtil;

public class InitEnviromentResource extends ServerResource {

	private ProgressApplication application;
	private Properties props = ConfigUtil.getProps("shell.properties");

	@Override
	public void doInit() {
		application = (ProgressApplication) getApplication();
	}

	@Get("json")
	public String initMysql() {
		Constant.CURRENT_NUM = new AtomicLong(0L);
		Constant.SUM_OF_DATA = 0;
		//int success = application.excuteShell(props.getProperty("init.mysql.shell.path"));
		int success = application.excuteShell("src/main/resources/adt.sh");
		return String.valueOf(success);
	}

}
