package cm.commons.stat.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cm.commons.dao.basic.BasicDaoImpl;
import cm.commons.pojos.Segment;
import cm.commons.stat.dao.SegmentDao;

public class SegmentDaoImpl extends BasicDaoImpl<Integer, Segment> implements
		SegmentDao<Integer, Segment> {
	private static Log log = LogFactory.getLog(SegmentDaoImpl.class);
	public SegmentDaoImpl(){
		super(Segment.class);
	}

}
