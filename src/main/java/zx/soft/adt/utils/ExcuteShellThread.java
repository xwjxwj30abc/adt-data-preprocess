package zx.soft.adt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcuteShellThread extends Thread {

	private static Logger logger = LoggerFactory.getLogger(ExcuteShellThread.class);

	private String name;

	public ExcuteShellThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Process p = Runtime.getRuntime().exec(name);
			try {
				p.waitFor();
				logger.info("finished.");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			InputStream fis = p.getInputStream();
			final BufferedReader brError = new BufferedReader(new InputStreamReader(
					p.getErrorStream(), "UTF-8"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			final BufferedReader br = new BufferedReader(isr);
			Thread t1 = new Thread() {
				@Override
				public void run() {
					String line = null;
					try {
						while ((line = brError.readLine()) != null) {
							System.err.println(line);
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
			};
			Thread t2 = new Thread() {
				@Override
				public void run() {
					String line = null;
					try {
						while ((line = br.readLine()) != null) {
							logger.info(line);
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
			};
			t1.start();
			t2.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}

	}

	public static void main(String[] args) {
		//修改adt.sh可执行
		ExcuteShellThread thread = new ExcuteShellThread("src/main/resources/adt.sh");
		thread.start();
	}

}
