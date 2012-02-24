package cm.commons.json.constant;

/**
 * 请求数据之电脑
 * @author Administrator
 *
 */
public class ComputerJson {
	
	private String pc_ip;
	private String pc_state;
	private String pc_info;
	private String pc_cpu_usage;
	private String pc_mem_usage;
	public String getPc_ip() {
		return pc_ip;
	}
	public void setPc_ip(String pcIp) {
		pc_ip = pcIp;
	}
	public String getPc_state() {
		return pc_state;
	}
	public void setPc_state(String pcState) {
		pc_state = pcState;
	}
	public String getPc_info() {
		return pc_info;
	}
	public void setPc_info(String pcInfo) {
		pc_info = pcInfo;
	}
	public String getPc_cpu_usage() {
		return pc_cpu_usage;
	}
	public void setPc_cpu_usage(String pcCpuUsage) {
		pc_cpu_usage = pcCpuUsage;
	}
	public String getPc_mem_usage() {
		return pc_mem_usage;
	}
	public void setPc_mem_usage(String pcMemUsage) {
		pc_mem_usage = pcMemUsage;
	}
	@Override
	public String toString() {
		return "ComputerJson [pc_cpu_usage=" + pc_cpu_usage + ", pc_info="
				+ pc_info + ", pc_ip=" + pc_ip + ", pc_mem_usage="
				+ pc_mem_usage + ", pc_state=" + pc_state + "]";
	}
	
	
}
