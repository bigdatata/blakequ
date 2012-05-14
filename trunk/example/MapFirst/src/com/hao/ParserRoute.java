package com.hao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.entity.RouteDetail;
import com.entity.SingleStep;

//http://blog.csdn.net/qyq24836910000/article/details/4014631 Xpath½âÎö·½·¨
public class ParserRoute {
	XmlPullParserFactory factory = null;
	XmlPullParser parser = null;

	int steps = 0;
	List<SingleStep> listSteps = null;
	public static final String STATUS = "status";
	// public static final String ROUTE = "route";
	public static final String SUMMARY = "summary";
	public static final String LEG = "leg";
	public static final String STEP = "step";
	// public static final String TRAVEL_MODEL = "travel_mode";
	public static final String START_LOCATION = "start_location";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String DURATION = "duration";
	public static final String VALUE = "value";
	public static final String TEXT = "text";
	public static final String HTML_INSTRUCTIONS = "html_instructions";
	public static final String DISTANCE = "distance";
	public static final String START_ADDRESS = "start_address";
	public static final String END_ADDRESS = "end_address";
	public static final String OVERVIEW_POLYLINE = "overview_polyline";
	public static final String POINTS = "points";
	public static final String WAARING = "warning";

	public ParserRoute() {
		try {
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<SingleStep> parserStep(InputStream is)
			throws XmlPullParserException, IOException {
//		parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(is, "UTF-8");
		SingleStep step = null;
		String status = "", tag = "";
		boolean flag = false;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (nodeName.equals(STATUS)) {
					status = parser.nextText();
				}
				if (status.equals("OK")) {
					if (nodeName.equals(LEG)) {
						listSteps = new ArrayList<SingleStep>();
					}
					if (nodeName.equals(STEP)) {
						step = new SingleStep();
						step.setId(steps);
						steps++;
						flag = true;
					}
					if(nodeName.equals(START_LOCATION)){
						tag = START_LOCATION;
					}
					if(tag.equals(START_LOCATION) && nodeName.equals(LAT)){
						step.setStart_location_lat(parser.nextText());
					}
					if(tag.equals(START_LOCATION) && nodeName.equals(LNG)){
						step.setStart_location_lng(parser.nextText());
					}
					// single step
					if (flag && nodeName.equals(DURATION)) {
						tag = DURATION;
					}
					if (tag.equals(DURATION) && nodeName.equals(VALUE)) {
						step.setDuration_value(parser.nextText());
					}
					if (tag.equals(DURATION) && nodeName.equals(TEXT)) {
						step.setDuration_text(parser.nextText());
					}
					if (nodeName.equals(HTML_INSTRUCTIONS)) {
						step.setHtml_instructions(parser.nextText());
					}
					if (flag && nodeName.equals(DISTANCE)) {
						tag = DISTANCE;
					}
					if (tag.equals(DISTANCE) && nodeName.equals(VALUE)) {
						step.setDistance_value(parser.nextText());
					}
					if (tag.equals(DISTANCE) && nodeName.equals(TEXT)) {
						step.setDistance_text(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (nodeName.equals(STEP)) {
					listSteps.add(step);
					flag = false;
				}
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		return listSteps;
	}

	public RouteDetail parserRoute(InputStream is)
			throws XmlPullParserException, IOException {
		RouteDetail routeDetail = null;
//		parser = factory.newPullParser();
		parser.setInput(is, "UTF-8");
		String status = "", route = "";
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (nodeName.equals(STATUS)) {
					routeDetail = new RouteDetail();
					status = parser.nextText();
					routeDetail.setStatus(status);
				}
				if (status.equals("OK")) {
					if (nodeName.equals(SUMMARY)) {
						routeDetail.setSummary(parser.nextText());
					}
					if (nodeName.equals(STEP)) {
						break;
					}
					// total route
					if (nodeName.equals(DURATION)) {
						route = "route_duration";
					}
					if (route.equals("route_duration")
							&& nodeName.equals(VALUE)) {
						routeDetail.setDuration_value(parser.nextText());
					}
					if (route.equals("route_duration") && nodeName.equals(TEXT)) {
						routeDetail.setDuration_text(parser.nextText());
					}
					if (nodeName.equals(DISTANCE)) {
						route = "route_distance";
					}
					if (route.equals("route_distance")
							&& nodeName.equals(VALUE)) {
						routeDetail.setDistance_value(parser.nextText());
					}
					if (route.equals("route_distance") && nodeName.equals(TEXT)) {
						routeDetail.setDistance_text(parser.nextText());
					}
					if (nodeName.equals(START_ADDRESS)) {
						routeDetail.setStart_address(parser.nextText());
					}
					if (nodeName.equals(END_ADDRESS)) {
						routeDetail.setEnd_address(parser.nextText());
					}
					if (nodeName.equals(OVERVIEW_POLYLINE)) {
						route = OVERVIEW_POLYLINE;
					}
					if (route.equals(OVERVIEW_POLYLINE)
							&& nodeName.equals(POINTS)) {
						routeDetail.setPoints(parser.nextText());
					}
					if (nodeName.equals(WAARING)) {
						routeDetail.setWarning(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			default:
				break;
			}
			eventType = parser.next();

		}
		return routeDetail;
	}

}
