package cm.commons.pojos;

/**
 * Route entity. @author MyEclipse Persistence Tools
 */

public class Route implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer stationNum;

	// Constructors

	/** default constructor */
	public Route() {
	}

	/** full constructor */
	public Route(String name, Integer stationNum) {
		this.name = name;
		this.stationNum = stationNum;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStationNum() {
		return this.stationNum;
	}

	public void setStationNum(Integer stationNum) {
		this.stationNum = stationNum;
	}

}