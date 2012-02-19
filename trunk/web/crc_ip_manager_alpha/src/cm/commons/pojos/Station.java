package cm.commons.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * Station entity. @author MyEclipse Persistence Tools
 */

public class Station implements java.io.Serializable {

	// Fields

	private Integer id;
	private Router router;
	private Computer computer;
	private String name;
	private Integer state;
	private String x;
	private String y;
	private Integer segmentNum;//表示该站点出去或进来的线段条数：一般为2（一进一出）
	private Set segmentsForStation2Id = new HashSet(0);
	private Set segmentsForStation1Id = new HashSet(0);

	// Constructors

	/** default constructor */
	public Station() {
	}

	/** minimal constructor */
	public Station(Router router, Computer computer, String name) {
		this.router = router;
		this.computer = computer;
		this.name = name;
	}

	/** full constructor */
	public Station(Router router, Computer computer, String name,
			Integer state, String x, String y, Integer segmentNum,
			Set segmentsForStation2Id, Set segmentsForStation1Id) {
		this.router = router;
		this.computer = computer;
		this.name = name;
		this.state = state;
		this.x = x;
		this.y = y;
		this.segmentNum = segmentNum;
		this.segmentsForStation2Id = segmentsForStation2Id;
		this.segmentsForStation1Id = segmentsForStation1Id;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Router getRouter() {
		return this.router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public Computer getComputer() {
		return this.computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return this.y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public Integer getSegmentNum() {
		return this.segmentNum;
	}

	public void setSegmentNum(Integer segmentNum) {
		this.segmentNum = segmentNum;
	}

	public Set getSegmentsForStation2Id() {
		return this.segmentsForStation2Id;
	}

	public void setSegmentsForStation2Id(Set segmentsForStation2Id) {
		this.segmentsForStation2Id = segmentsForStation2Id;
	}

	public Set getSegmentsForStation1Id() {
		return this.segmentsForStation1Id;
	}

	public void setSegmentsForStation1Id(Set segmentsForStation1Id) {
		this.segmentsForStation1Id = segmentsForStation1Id;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", name=" + name + ", segmentNum="
				+ segmentNum + ", state=" + state + ", x=" + x + ", y=" + y
				+ "]";
	}

}