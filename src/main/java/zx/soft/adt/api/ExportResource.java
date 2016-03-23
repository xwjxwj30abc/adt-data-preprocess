package zx.soft.adt.api;

import java.io.IOException;
import java.util.Properties;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import zx.soft.adt.hbase.WriteToHBase;
import zx.soft.utils.config.ConfigUtil;

import com.google.protobuf.ServiceException;

public class ExportResource extends ServerResource {

	private ProgressApplication application;
	private Properties props = ConfigUtil.getProps("shell.properties");

	@Override
	public void doInit() {
		application = (ProgressApplication) getApplication();
	}

	@Get("json")
	public void exportData() {
		try {
			WriteToHBase writer = new WriteToHBase();
			writer.write();
			String dataTransferShellPath = props.getProperty("data.transfer.shell.path");
			application.excuteShell(dataTransferShellPath);
		} catch (IOException | ServiceException e1) {
			e1.printStackTrace();
		}
	}

}
