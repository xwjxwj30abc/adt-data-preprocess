package zx.soft.adt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcuteShell {

	private static Logger logger = LoggerFactory.getLogger(ExcuteShell.class);

	private String name;
	private int success = 1;

	public ExcuteShell(String name) {
		this.name = name;
	}

	public int run() {
		try {
			logger.info(System.currentTimeMillis() + "start.");
			Process p = Runtime.getRuntime().exec(name);
			PrintErrorThread errorThread = new PrintErrorThread(p.getErrorStream());
			PrintNormalThread normalThread = new PrintNormalThread(p.getInputStream());
			errorThread.start();
			normalThread.start();

			try {
				success = p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info(System.currentTimeMillis() + "finished.");

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return success;

	}

	public class PrintErrorThread extends Thread {

		private InputStream inputStream;

		public PrintErrorThread(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader brError = null;
			try {
				brError = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = brError.readLine()) != null) {
					logger.error("error:" + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (brError != null)
						brError.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public class PrintNormalThread extends Thread {
		private InputStream inputStream;

		public PrintNormalThread(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = br.readLine()) != null) {
					logger.info("normal:" + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		//修改adt.sh可执行
		ExcuteShell thread = new ExcuteShell("src/main/resources/transfer.sh");
		System.out.println(thread.run());
		//new Thread(thread).start();
	}

}
