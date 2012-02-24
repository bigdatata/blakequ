package cm.commons.json.constant;

/**
 * 路由信息
 * @author Administrator
 *
 */
public class RouterJson {

	private String router_ip;
	private String router_info;
	private String router_state;
	private String router_cup_usage;
	private String router_mem_usage;
	private String router_port_number;
	public String getRouter_ip() {
		return router_ip;
	}
	public void setRouter_ip(String routerIp) {
		router_ip = routerIp;
	}
	public String getRouter_info() {
		return router_info;
	}
	public void setRouter_info(String routerInfo) {
		router_info = routerInfo;
	}
	public String getRouter_state() {
		return router_state;
	}
	public void setRouter_state(String routerState) {
		router_state = routerState;
	}
	public String getRouter_cup_usage() {
		return router_cup_usage;
	}
	public void setRouter_cup_usage(String routerCupUsage) {
		router_cup_usage = routerCupUsage;
	}
	public String getRouter_mem_usage() {
		return router_mem_usage;
	}
	public void setRouter_mem_usage(String routerMemUsage) {
		router_mem_usage = routerMemUsage;
	}
	public String getRouter_port_number() {
		return router_port_number;
	}
	public void setRouter_port_number(String routerPortNumber) {
		router_port_number = routerPortNumber;
	}
	@Override
	public String toString() {
		return "RouterJson [router_cup_usage=" + router_cup_usage
				+ ", router_info=" + router_info + ", router_ip=" + router_ip
				+ ", router_mem_usage=" + router_mem_usage
				+ ", router_port_number=" + router_port_number
				+ ", router_state=" + router_state + "]";
	}
	
	
}
