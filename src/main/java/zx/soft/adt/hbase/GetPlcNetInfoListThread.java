package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;

public class GetPlcNetInfoListThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(GetPlcNetInfoListThread.class);

	private int from;
	private String mysqlTablename;
	private HConnection conn;

	public GetPlcNetInfoListThread(HConnection conn, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		try {
			SQLOperation sqlOperation = new SQLOperation();
			List<PlcNetInfo> plcnetinfos = sqlOperation.getPlcNetInfoData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_plcnetinfo_table_name);
			for (PlcNetInfo plcnetinfo : plcnetinfos) {
				String rowKey = plcnetinfo.getRule_id() + Constant.Service_code;
				hbaseTable.put(rowKey, Constant.adt_cf, "sc", Constant.Service_code);
				hbaseTable.put(rowKey, Constant.adt_cf, "rn", plcnetinfo.getRule_name());
				hbaseTable.put(rowKey, Constant.adt_cf, "ml", plcnetinfo.getMatching_level());
				hbaseTable.put(rowKey, Constant.adt_cf, "ra", plcnetinfo.getRule_action());
				hbaseTable.put(rowKey, Constant.adt_cf, "st", plcnetinfo.getService_type());
				hbaseTable.put(rowKey, Constant.adt_cf, "k1", plcnetinfo.getKeyword1());
				hbaseTable.put(rowKey, Constant.adt_cf, "k2", plcnetinfo.getKeyword2());
				hbaseTable.put(rowKey, Constant.adt_cf, "k3", plcnetinfo.getKeyword3());
				hbaseTable.put(rowKey, Constant.adt_cf, "mw", plcnetinfo.getMatching_word());
				hbaseTable.put(rowKey, Constant.adt_cf, "set", plcnetinfo.getSet_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "vt", plcnetinfo.getValidation_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "mpt", plcnetinfo.getManual_pause_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "fm", plcnetinfo.getFilter_method());
				hbaseTable.put(rowKey, Constant.adt_cf, "fa", plcnetinfo.getFilter_argument());
			}
			hbaseTable.execute();
			hbaseTable.close();
			logger.info("succeed insert" + plcnetinfos.size());
			Constant.CURRENT_NUM.addAndGet(plcnetinfos.size());
			logger.info("插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
