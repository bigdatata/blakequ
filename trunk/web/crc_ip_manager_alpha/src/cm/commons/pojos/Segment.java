package cm.commons.pojos;

/**
 * Segment entity. @author MyEclipse Persistence Tools
 */

public class Segment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Station stationByStation1Id;
	private Station stationByStation2Id;
	private Integer routeId;
	private Integer state;

	// Constructors

	/** default constructor */
	public Segment() {
	}

	/** minimal constructor */
	public Segment(Station stationByStation1Id, Station stationByStation2Id,
			Integer routeId) {
		this.stationByStation1Id = stationByStation1Id;
		this.stationByStation2Id = stationByStation2Id;
		this.routeId = routeId;
	}

	/** full constructor */
	public Segment(Station stationByStation1Id, Station stationByStation2Id,
			Integer routeId, Integer state) {
		this.stationByStation1Id = stationByStation1Id;
		this.stationByStation2Id = stationByStation2Id;
		this.routeId = routeId;
		this.state = state;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Station getStationByStation1Id() {
		return this.stationByStation1Id;
	}

	public void setStationByStation1Id(Station stationByStation1Id) {
		this.stationByStation1Id = stationByStation1Id;
	}

	public Station getStationByStation2Id() {
		return this.stationByStation2Id;
	}

	public void setStationByStation2Id(Station stationByStation2Id) {
		this.stationByStation2Id = stationByStation2Id;
	}

	public Integer getRouteId() {
		return this.routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Segment [id=" + id + ", routeId=" + routeId + ", state="
				+ state + ", stationByStation1Id=" + stationByStation1Id
				+ ", stationByStation2Id=" + stationByStation2Id + "]";
	}

}