package com.jalvaro.velobleu.client.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.location.Location;
import android.text.util.Linkify;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;

public class AndroidUtils {
	public static Location convertGeoPointToLocation(GeoPoint geoPoint) {
		Location location = new Location("");
		location.setLatitude((double) (((double) geoPoint.getLatitudeE6()) / 1000000));
		location.setLongitude((double) (((double) geoPoint.getLongitudeE6()) / 1000000));

		return location;
	}

	public static byte convertBooleanToByte(boolean myBoolean) {
		byte myByte;
		if (myBoolean) {
			myByte = 1;
		} else {
			myByte = 0;
		}
		return myByte;
	}

	public static boolean convertByteToBoolean(byte myByte) {
		boolean myBoolean;
		if (myByte == 1) {
			myBoolean = true;
		} else {
			myBoolean = false;
		}
		return myBoolean;
	}

	public static void addLink(TextView textView, final String link) {
		AndroidUtils.addLink(textView, ".+", link);
	}

	public static void addLink(TextView textView, String patternToMatch, final String link) {
		Linkify.TransformFilter filter = new Linkify.TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				return link;
			}
		};
		Linkify.addLinks(textView, Pattern.compile(patternToMatch), null, null, filter);
	}
	
	public static String getPackage() {
		return "";
	}
}
