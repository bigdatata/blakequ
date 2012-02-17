package cm.commons.controller.form;

public class StationInfoForm {
	private int rid;//所属线路id
	private int st;//所包含站点数目
	private int sg;//所包含线段数目
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public int getSg() {
		return sg;
	}
	public void setSg(int sg) {
		this.sg = sg;
	}
	@Override
	public String toString() {
		return "StationInfoForm [rid=" + rid + ", sg=" + sg + ", st=" + st
				+ "]";
	}
	
	
}
