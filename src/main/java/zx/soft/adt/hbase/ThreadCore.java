package zx.soft.adt.hbase;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.utils.SQLOperation;
import zx.soft.utils.threads.ApplyThreadPool;

public class ThreadCore {

	private static Logger logger = LoggerFactory.getLogger(ThreadCore.class);

	private static ThreadPoolExecutor pool;
	private HConnection conn;
	private SQLOperation sqlOperation;

	public ThreadCore(HConnection conn, SQLOperation sqlOperation) {
		pool = ApplyThreadPool.getThreadPoolExector(2);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
				logger.info("pool is shutdown");
			}
		}));
		this.conn = conn;
		this.sqlOperation = sqlOperation;
	}

	public void addRunnable(String tablename, int from) {
		Runnable runnable = new ImportDataThread(this.conn, this.sqlOperation, tablename, from);
		if (!pool.isShutdown()) {
			try {
				pool.execute(runnable);
			} catch (Exception e) {
				logger.error("pool executor error");
				throw new RuntimeException();
			}
		}
	}

	public void close() {
		pool.shutdown();
		do {
			try {
				TimeUnit.SECONDS.sleep(10);
				logger.info("线程池任务并没有完全结束，等待10s.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!pool.isTerminated());
	}
}
