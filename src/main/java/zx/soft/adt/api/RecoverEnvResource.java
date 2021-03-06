package zx.soft.adt.api;

/**
 * 恢复环境，安全移除设备资源
 */
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import zx.soft.adt.utils.Constant;
import zx.soft.utils.config.ConfigUtil;

public class RecoverEnvResource extends ServerResource {

	private ProgressApplication application;
	private Properties props = ConfigUtil.getProps("shell.properties");

	@Override
	public void doInit() {
		application = (ProgressApplication) getApplication();
	}

	@Get("json")
	public String recoverEnv() {
		int success = application.excuteShell(props.getProperty("mysql.cleanup.shell.path"));
		Constant.SUM_OF_DATA = 0;
		Constant.CURRENT_NUM = new AtomicLong(0L);
		return String.valueOf(success);
	}
}