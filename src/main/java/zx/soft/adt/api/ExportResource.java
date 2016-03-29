package zx.soft.adt.api;

import java.util.Properties;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.hbase.WriteToHBase;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.ExcuteShell;
import zx.soft.utils.config.ConfigUtil;

public class ExportResource extends ServerResource {

	private ProgressApplication application;

	@Override
	public void doInit() {
		application = (ProgressApplication) getApplication();
	}

	@Get("json")
	public String exportData() {
		ExecExport thread = new ExecExport(ConfigUtil.getProps("shell.properties"));
		new Thread(thread).start();
		return "ok";
	}

	private static class ExecExport implements Runnable {
		private static Logger logger = LoggerFactory.getLogger(ExecExport.class);
		private Properties props;

		public ExecExport(Properties props) {
			this.props = props;
		}

		@Override
		public void run() {
			WriteToHBase writer = new WriteToHBase();
			String dataTransferShellPath = this.props.getProperty("data.transfer.shell.path");
			ExcuteShell shell = new ExcuteShell(dataTransferShellPath);
			try {
				writer.write();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			int success = shell.run();
			if (success == 0) {
				Constant.CURRENT_NUM.addAndGet(1L);
			} else {
				Constant.CURRENT_NUM.addAndGet(2L);
			}

		}
	}

	public static String getResult() {
		Properties props = ConfigUtil.getProps("shell.properties");
		String dataTransferShellPath = "src/main/resources/transfer.sh";
		props.setProperty("data.transfer.shell.path", dataTransferShellPath);
		ExecExport thread = new ExecExport(props);
		thread.run();
		return "ok";
	}

	public static void main(String[] args) {
		System.out.println(ExportResource.getResult());
	}

}
