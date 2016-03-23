package zx.soft.adt.domain;


/**
 * 规则表
 *
 * @author wanggang
 *
 */
public class PlcNetInfo {

	private long Service_code;
	private String Rule_id = "";
	private String Rule_name = "";
	private int Matching_level;
	private int Rule_action;
	private int Service_type;
	private String Keyword1 = "";
	private String Keyword2 = "";
	private String Keyword3 = "";
	private int Matching_word;
	private long Set_time;
	private long Validation_time;
	private long Manual_pause_time;
	private int Filter_method;
	private String Filter_argument = "";

	public PlcNetInfo() {
	}

	public long getService_code() {
		return Service_code;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public String getRule_id() {
		return Rule_id;
	}

	public void setRule_id(String rule_id) {
		Rule_id = rule_id;
	}

	public String getRule_name() {
		return Rule_name;
	}

	public void setRule_name(String rule_name) {
		Rule_name = rule_name;
	}

	public int getMatching_level() {
		return Matching_level;
	}

	public void setMatching_level(int matching_level) {
		Matching_level = matching_level;
	}

	public int getRule_action() {
		return Rule_action;
	}

	public void setRule_action(int rule_action) {
		Rule_action = rule_action;
	}

	public int getService_type() {
		return Service_type;
	}

	public void setService_type(int service_type) {
		Service_type = service_type;
	}

	public String getKeyword1() {
		return Keyword1;
	}

	public void setKeyword1(String keyword1) {
		Keyword1 = keyword1;
	}

	public String getKeyword2() {
		return Keyword2;
	}

	public void setKeyword2(String keyword2) {
		Keyword2 = keyword2;
	}

	public String getKeyword3() {
		return Keyword3;
	}

	public void setKeyword3(String keyword3) {
		Keyword3 = keyword3;
	}

	public int getMatching_word() {
		return Matching_word;
	}

	public void setMatching_word(int matching_word) {
		Matching_word = matching_word;
	}

	public long getSet_time() {
		return Set_time;
	}

	public void setSet_time(long set_time) {
		Set_time = set_time;
	}

	public long getValidation_time() {
		return Validation_time;
	}

	public void setValidation_time(long validation_time) {
		Validation_time = validation_time;
	}

	public long getManual_pause_time() {
		return Manual_pause_time;
	}

	public void setManual_pause_time(long manual_pause_time) {
		Manual_pause_time = manual_pause_time;
	}

	public int getFilter_method() {
		return Filter_method;
	}

	public void setFilter_method(int filter_method) {
		Filter_method = filter_method;
	}

	public String getFilter_argument() {
		return Filter_argument;
	}

	public void setFilter_argument(String filter_argument) {
		Filter_argument = filter_argument;
	}

	@Override
	public String toString() {
		return "PlcNetInfo [Service_code=" + Service_code + ", Rule_id=" + Rule_id + ", Rule_name="
				+ Rule_name + ", Matching_level=" + Matching_level + ", Rule_action=" + Rule_action
				+ ", Service_type=" + Service_type + ", Keyword1=" + Keyword1 + ", Keyword2="
				+ Keyword2 + ", Keyword3=" + Keyword3 + ", Matching_word=" + Matching_word
				+ ", Set_time=" + Set_time + ", Validation_time=" + Validation_time
				+ ", Manual_pause_time=" + Manual_pause_time + ", Filter_method=" + Filter_method
				+ ", Filter_argument=" + Filter_argument + "]";
	}

}
