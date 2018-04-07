package com.dytra.quakerx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class SitesXmlPullParser {


	static final String KEY_GEMPA = "gempa";
	static final String KEY_TANGGAL = "Tanggal";
	static final String KEY_JAM = "Jam";
	static final String KEY_COORDINATES = "coordinates";
	static final String KEY_LINTANG = "Lintang";
	static final String KEY_BUJUR = "Bujur";
	static final String KEY_MAGNITUDE = "Magnitude";
	static final String KEY_KEDALAMAN = "Kedalaman";
	static final String KEY_WILAYAH = "Wilayah";



	public static List<Gempa> getStackSitesFromFile(Context ctx) {

		// List of StackSites that we will return
		List<Gempa> gempas;
		gempas = new ArrayList<Gempa>();

		// temp holder for current Gempa while parsing
		Gempa curGempa = null;
		// temp holder for current text value while parsing
		String curText = "";

		try {
			// Get our factory and PullParser
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();

			// Open up InputStream and Reader of our file.
			FileInputStream fis = ctx.openFileInput("datagempa.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

			// point the parser to our file.
			xpp.setInput(reader);

			// get initial eventType
			int eventType = xpp.getEventType();

			// Loop through pull events until we reach END_DOCUMENT
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// Get the current tag
				String tagname = xpp.getName();

				// React to different event types appropriately
				switch (eventType) {
					case XmlPullParser.START_TAG:
						if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
							// If we are starting a new <site> block we need
							//a new Gempa object to represent it
							curGempa = new Gempa();
						}
						break;

					case XmlPullParser.TEXT:
						//grab the current text so we can use it in END_TAG event
						curText = xpp.getText();
						break;

					case XmlPullParser.END_TAG:
						if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
							// if </site> then we are done with current Site
							// add it to the list.
							gempas.add(curGempa);
						} else if (tagname.equalsIgnoreCase(KEY_TANGGAL)) {
							// if </name> use setName() on curSite
							curGempa.setTanggal(curText);
						} else if (tagname.equalsIgnoreCase(KEY_JAM)) {
							// if </link> use setLink() on curSite
							curGempa.setJam(curText);
						} else if (tagname.equalsIgnoreCase(KEY_COORDINATES)) {
							// if </link> use setLink() on curSite
							curGempa.setCoordinates(curText);
						} else if (tagname.equalsIgnoreCase(KEY_LINTANG)) {
							// if </name> use setName() on curSite
							curGempa.setLintang(curText);
						} else if (tagname.equalsIgnoreCase(KEY_BUJUR)) {
							// if </link> use setLink() on curSite
							curGempa.setBujur(curText);
						} else if (tagname.equalsIgnoreCase(KEY_MAGNITUDE)) {
							// if </about> use setAbout() on curSite
							curGempa.setMagnitude(curText);
						} else if (tagname.equalsIgnoreCase(KEY_KEDALAMAN)) {
							// if </about> use setAbout() on curSite
							curGempa.setKedalaman(curText);
						} else if (tagname.equalsIgnoreCase(KEY_WILAYAH)) {
							// if </about> use setAbout() on curSite
							curGempa.setWilayah(curText);
						}
						break;

					default:
						break;
				}
				//move on to next iteration
				eventType = xpp.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return the populated list.
		return gempas;
	}
}