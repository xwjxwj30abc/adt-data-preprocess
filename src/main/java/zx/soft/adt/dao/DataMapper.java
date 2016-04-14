package zx.soft.adt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.domain.VPNTraffic;
import zx.soft.adt.domain.WanIpv4;

public interface DataMapper {

	@Select("SELECT MAX(id) FROM  ${tablename}")
	public int getMaxId(@Param("tablename") String tablename);

	@Select("SELECT COUNT(*) FROM  ${tablename}")
	public int getCount(@Param("tablename") String tablename);

	@Select("SELECT id,Service_code,Rule_id,Rule_name,Matching_level,Rule_action,"
			+ "Service_type,Keyword1,Keyword2,Keyword3,Matching_word,"
			+ "Set_time,Validation_time,Manual_pause_time,Filter_method,Filter_argument FROM ${tablename}"
			+ " WHERE id >= #{from} AND id<(#{from}+1000)")
	public List<PlcNetInfo> getPlcNetInfoData(@Param("tablename") String tablename, @Param("from") int from);

	@Select("SELECT id,Service_code,Rule_id,Destination_ip,Net_ending_ip,Net_ending_mac,"
			+ "Destination_ipv6,Net_ending_ipv6,Matching_time,Service_type,Keyword1,"
			+ "Keyword2,Keyword3,User_name,Certificate_type," + "Certificate_code,Org_name,Country FROM ${tablename}"
			+ " WHERE id >= #{from} AND id<(#{from}+1000)")
	public List<AlertList> getAlertListData(@Param("tablename") String tablename, @Param("from") int from);

	@Select("SELECT Service_code,Service_name, Address,Zip, Principal,"
			+ "Principal_tel,Infor_man,Infor_man_tel,Infor_man_email,Producer_code,Status,Ending_number,"
			+ "Server_number,Ip,Net_type,Practitioner_number,Net_monitor_department,Net_monitor_man,"
			+ "Net_monitor_man_tel,Remark,NewSystem,UnitNo,SessionID,UdpHost,UdpPort,"
			+ "UdpVer,ComputerOnline,ClientTime,LogDays,CommStatus,CommNormal,CommTiming,AlertLogAttr,UserLogAttr,DefaultAccessRule,"
			+ "Device_ipv4,Device_ipv6,Device_port,Udp_online,Device_serial,"
			+ "Device_version,Device_flow1,Device_flow2,Device_note,User_name,Certificate_type,"
			+ "Certificate_code,Org_name,Country FROM ${tablename}  WHERE Service_code=#{Service_code}")
	public PlcClient getPlcClientData(@Param("tablename") String tablename, @Param("Service_code") long Service_code);

	@Select("SELECT id,Service_code,Net_ending_ip,Net_ending_name,Time,Net_ending_mac,Destination_ip,"
			+ "Port,Service_type,Keyword1,Keyword2,Keyword3,Mac,Source_port,Net_ending_ipv6,Destination_ipv6,"
			+ "Keyword11,Keyword12,Keyword13,Keyword14,Keyword15,Keyword21,Keyword22,Keyword23,"
			+ "Keyword24,Keyword25,vpn1_ip,vpn2_ip,vpn3_ip FROM ${tablename}"
			+ " WHERE id >= #{from} AND id<(#{from}+1000)")
	public List<AccessList> getAccessListData(@Param("tablename") String tablename, @Param("from") int from);

	@Select("SELECT COUNTRY,JD,WD FROM ${tablename} WHERE COUNTRY LIKE #{country}")
	public IP2GEO getGEO(@Param("tablename") String tablename, @Param("country") String country);

	@Select("SELECT COUNTRY,JD,WD FROM ${tablename} WHERE ID BETWEEN  0000000380 AND 0000000567")
	public List<IP2GEO> getALLGEO(@Param("tablename") String tablename);

	@Select("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA= #{db}")
	public List<String> getAllTableNames(@Param("db") String db);

	@Select("SELECT id,ipv4,begin_time,end_time,traffic FROM ${tablename} WHERE id >= #{from} AND id<(#{from}+1000)")
	public List<VPNTraffic> getVPNTrafficData(@Param("tablename") String tablename, @Param("from") int from);

	@Select("SELECT id,ipv4,add_time FROM ${tablename} WHERE id >= #{from} AND id<(#{from}+1000)")
	public List<WanIpv4> getWanIpv4Data(@Param("tablename") String tablename, @Param("from") int from);

	@Select("SELECT Service_code FROM ${tablename} LIMIT 1")
	public long getServiceCode(@Param("tablename") String tablename);

	@Select("INSERT INTO ${tablename}(Service_code,Service_name)VALUES(#{Service_code},#{Service_name})")
	public void insertServiceCode(@Param("tablename") String tablename, @Param("Service_code") long Service_code,
			@Param("Service_name") String Service_name);

	@Select("SELECT COUNT(*) FROM ${tablename} WHERE Service_code=#{Service_code}")
	public int existsServiceCode(@Param("tablename") String tablename, @Param("Service_code") long Service_code);

}
