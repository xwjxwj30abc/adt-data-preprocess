package zx.soft.adt.api;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import zx.soft.utils.config.ConfigUtil;

public class ProcessorServer {

	private final Component component;
	private final ProgressApplication application;
	private final int port;

	public ProcessorServer() {
		Properties pros = ConfigUtil.getProps("web-server.properties");
		component = new Component();
		application = new ProgressApplication();
		port = Integer.parseInt(pros.getProperty("api.port"));
	}

	public static void main(String[] args) {
		ProcessorServer server = new ProcessorServer();
		server.start();
	}

	public void start() {
		component.getServers().add(Protocol.HTTP, port);
		try {
			component.getDefaultHost().attach("/shell", application);
			component.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			component.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
