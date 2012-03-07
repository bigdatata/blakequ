package cm.commons.dao.hiber.util;

/**
 * 连接符号
 * @author yangyi
 *
 * 2011-11-15
 * 
 * Link
 */
public enum Link {
	WHERE("where"),
	AND("and"),
	OR("or"),
	IN("in"),
	ORDER("order");
	
	private String value;

	private Link(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
