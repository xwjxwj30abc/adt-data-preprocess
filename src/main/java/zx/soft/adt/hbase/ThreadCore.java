package zx.soft.adt.hbase;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.threads.ApplyThreadPool;

public class ThreadCore {

	private static Logger logger = LoggerFactory.getLogger(ThreadCore.class);

	private static ThreadPoolExecutor pool;

	public ThreadCore() {
		pool = ApplyThreadPool.getThreadPoolExector(2);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
				logger.info("pool is shutdown");
			}
		}));
	}

	public void getAlertListData(HConnection conn, String tablename, int from) {
		if (!pool.isShutdown()) {
			try {
				pool.execute(new GetAlertListThread(conn, tablename, from));
			} catch (Exception e) {
				logger.error("pool executor error");
				throw new RuntimeException();
			}
		}
	}

	public void getPlcNetInfoData(HConnection conn, String tablename, int from) {
		if (!pool.isShutdown()) {
			try {
				pool.execute(new GetPlcNetInfoListThread(conn, tablename, from));
			} catch (Exception e) {
				logger.error("pool executor error");
				throw new RuntimeException();
			}
		}
	}

	public void getAccessListData(HConnection conn, String tablename, int from) {
		if (!pool.isShutdown()) {
			try {
				pool.execute(new GetAccessListThread(conn, tablename, from));
			} catch (Exception e) {
				logger.error("pool executor error");
				throw new RuntimeException();
			}
		}
	}

	public void getData(HConnection conn, String tablename, int from, Thread thread) {
		if (!pool.isShutdown()) {
			try {
				pool.execute(thread);
			} catch (Exception e) {
				logger.error("pool executor error");
				throw new RuntimeException();
			}
		}
	}

	public void close() {
		pool.shutdown();
		try {
			boolean loop = true;
			do { //等待所有任务完成
				loop = !pool.awaitTermination(10, TimeUnit.SECONDS); //阻塞，直到线程池里所有任务结束
			} while (loop);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//		try {
		//			pool.awaitTermination(20, TimeUnit.SECONDS);
		//		} catch (InterruptedException e) {
		//			logger.error(e.getMessage());
		//			throw new RuntimeException();
		//		}
	}

}
