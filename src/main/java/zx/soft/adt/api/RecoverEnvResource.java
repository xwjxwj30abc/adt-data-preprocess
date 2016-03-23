package zx.soft.adt.api;

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
	public Object recoverEnv() {
		Constant.SUM_OF_DATA = 0;
		Constant.CURRENT_NUM = new AtomicLong(0L);
		return application.excuteShell(props.getProperty("mysql.cleanup.shell.path"));
	}

}