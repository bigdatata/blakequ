package cm.commons.stat.dao.impl;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.pojos.Route;
import cm.commons.stat.dao.RouteDao;

public class RouteDaoImpl extends BasicDaoImpl<Integer, Route> implements RouteDao<Integer, Route> {

	public RouteDaoImpl(){
		super(Route.class);
	}
}
