package zx.soft.adt.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.ExcuteShell;

public class ProgressApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(this.getContext());
		router.attach("/progress", ProgressResource.class);
		router.attach("/init_env", InitEnviromentResource.class);
		router.attach("/export", ExportResource.class);
		router.attach("/clean", RecoverEnvResource.class);
		return router;
	}

	public String getTotal() {
		return String.valueOf(Constant.SUM_OF_DATA);
	}

	public String getCurrentNum() {
		return String.valueOf(Constant.CURRENT_NUM);
	}

	public int excuteShell(String shellPath) {
		ExcuteShell thread = new ExcuteShell(shellPath);
		int succss = thread.run();
		return succss;
	}

}
