package cm.commons.pojos;

/**
 * System entity. @author MyEclipse Persistence Tools
 */

public class System implements java.io.Serializable {

	// Fields

	private Integer id;
	private String configKey;
	private String configValue;

	// Constructors

	/** default constructor */
	public System() {
	}

	/** full constructor */
	public System(String configKey, String configValue) {
		this.configKey = configKey;
		this.configValue = configValue;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigKey() {
		return this.configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return this.configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	@Override
	public String toString() {
		return "System [configKey=" + configKey + ", configValue="
				+ configValue + ", id=" + id + "]";
	}

}