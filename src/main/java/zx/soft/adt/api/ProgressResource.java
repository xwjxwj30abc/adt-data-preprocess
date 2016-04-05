package zx.soft.adt.api;

/**
 * 显示导入数据进度资源
 */
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ProgressResource extends ServerResource {

	private ProgressApplication application;

	@Override
	public void doInit() {
		application = (ProgressApplication) getApplication();
	}

	@Get("json")
	public Object getProgress() {
		String num = application.getCurrentNum();
		String total = application.getTotal();
		return num + "/" + total;
	}

}
