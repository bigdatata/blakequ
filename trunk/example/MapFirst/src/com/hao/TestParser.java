package com.hao;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.entity.SingleStep;

public class TestParser {
	XmlPullParserFactory factory = null;
	XmlPullParser parser = null;
	
	public static final String SUMMARY = "summary";
	public static final String STEP = "step";
	public static final String DURATION = "duration";
	public static final String VALUE = "value";
	public static final String TEXT = "text";
	public static final String HTML_INSTRUCTIONS = "html_instructions";
	public static final String DISTANCE = "distance";
	
	public TestParser() {
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			throw new IllegalStateException("Could not create a factory");
		}
	}
	
	public SingleStep parser1(InputStream is) throws XmlPullParserException, IOException{
		parser = factory.newPullParser();
		parser.setInput(is, "UTF-8");
		String tag = "";
		SingleStep step = new SingleStep();
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (eventType) {
				case XmlPullParser.START_TAG:
					if (nodeName.equals(DURATION)) {
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
					if (nodeName.equals(DISTANCE)) {
						tag = DISTANCE;
					}
					if (tag.equals(DISTANCE) && nodeName.equals(VALUE)) {
						step.setDistance_value(parser.nextText());
					}
					if (tag.equals(DISTANCE) && nodeName.equals(TEXT)) {
						step.setDistance_text(parser.nextText());
					}
				case XmlPullParser.END_TAG:
					break;
				default:
					break;
			}
			eventType = parser.next();
		}
		return step;
		
	}

}
