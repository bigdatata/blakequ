package cm.commons.pojos;

import java.util.Date;


/**
 * Port entity. @author MyEclipse Persistence Tools
 */

public class Port implements java.io.Serializable {

	// Fields

	private Integer id;
	private Router router;
	private Date getTime;
	private Integer routerState;
	private Integer inFlow;
	private Integer outFlow;
	private Integer portNumber;

	// Constructors

	/** default constructor */
	public Port() {
	}

	/** minimal constructor */
	public Port(Router router) {
		this.router = router;
	}

	/** full constructor */
	public Port(Router router, Date getTime, Integer routerState,
			Integer inFlow, Integer outFlow, Integer portNumber) {
		this.router = router;
		this.getTime = getTime;
		this.routerState = routerState;
		this.inFlow = inFlow;
		this.outFlow = outFlow;
		this.portNumber = portNumber;
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

	public Date getGetTime() {
		return this.getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Integer getRouterState() {
		return this.routerState;
	}

	public void setRouterState(Integer routerState) {
		this.routerState = routerState;
	}

	public Integer getInFlow() {
		return this.inFlow;
	}

	public void setInFlow(Integer inFlow) {
		this.inFlow = inFlow;
	}

	public Integer getOutFlow() {
		return this.outFlow;
	}

	public void setOutFlow(Integer outFlow) {
		this.outFlow = outFlow;
	}

	public Integer getPortNumber() {
		return this.portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	@Override
	public String toString() {
		return "Port [getTime=" + getTime + ", id=" + id + ", inFlow=" + inFlow
				+ ", outFlow=" + outFlow + ", portNumber=" + portNumber
				+ ", router=" + router + ", routerState=" + routerState + "]";
	}

}