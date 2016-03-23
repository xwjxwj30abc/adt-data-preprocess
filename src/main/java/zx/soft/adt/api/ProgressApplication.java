package zx.soft.adt.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.ExcuteShellThread;

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

	public String excuteShell(String shellPath) {
		ExcuteShellThread thread = new ExcuteShellThread(shellPath);
		thread.start();
		try {
			thread.join();
			return "shell执行成功";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "shell执行失败";
		} finally {
			//			thread.interrupt();
		}
	}

}
