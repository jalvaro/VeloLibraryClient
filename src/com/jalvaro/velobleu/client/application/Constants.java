package com.jalvaro.velobleu.client.application;

public final class Constants {
	// Session Preferences
	public static final String PREFS_SESSION_NAME = "name";
	public static final String PREFS_SESSION_SURNAMES = "surnames";
	public static final String PREFS_SESSION_PASSWORD = "password";
	public static final String PREFS_SESSION_USERNAME = "userName";
	public static final String PREFS_SESSION_EMAIL = "email";
	public static final String PREFS_SESSION_COOKIE = "cookie";
	
	// Extras
	public static final String EXTRA_DEVICE = "EXTRA_DEVICE";
	
	// Broadcast
	public static final String BROADCAST_LOGOUT = "BROADCAST_LOGOUT";
	
	// Map
	public static final int ZOOM = 15;
	public static final double LAT_NICE_CENTER = 43.699729;
	public static final double LON_NICE_CENTER = 7.266405;
	public static final int INIT_VALUE = -100;
	public static final int SECOND = 1000;
	public static final int MINUTE = 60*SECOND;
	public static final int HOUR = 60*MINUTE;
	public static final int STATUS_TEXT_MILLIS = 10*SECOND;
	
	// Resources Id
//	public static final int RES_ID_NULL = -1;
	
	// Json timezone and time representation
	public static final String JSON_TIME_FORMATTER = "MM/dd/yy HH:mm:ss";
	public static final String ANDROID_TIME_FORMATTER = "dd/MM/yyyy HH:mm";
}
