package com.jalvaro.velobleu.client.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.jalvaro.velobleu.client.models.FleetVO;

public class CRUDUtils {
	private static final String TAG = CRUDUtils.class.getSimpleName();

	public static ResponseEntity<String> post(String url, String req) {
		return post(url, req, "");
	}

	public static ResponseEntity<String> post(String url, String req, String cookie) {
		Log.d(TAG, "post - url: " + url);
		Log.d(TAG, "simpleGet - cookie: " + cookie);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.add("cookie", cookie);

		HttpEntity<?> requestEntity = new HttpEntity<Object>(req, requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

		Log.d(TAG, "post - result: " + responseEntity.getBody());

		return responseEntity;
	}

	public static void simpleGet(String url) {

		Log.d(TAG, "simpleGet - url: " + url);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the String message converter
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

		// Make the HTTP GET request, marshaling the response to a String
		String result = restTemplate.getForObject("http://www.google.com:80", String.class, "SpringSource");

		Log.d(TAG, "simpleGet - result: " + result);
	}

	public static String get(String url) {
		Log.d(TAG, "simpleGet - url: " + url);

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		Log.d(TAG, "simpleGet - result: " + responseEntity.getBody());

		return responseEntity.getBody();
	}

	/*
	 * public static DeviceVO[] getArrayGson(String url, String cookie) {
	 * Log.d(TAG, "simpleGet - url: " + url);
	 * Log.d(TAG, "simpleGet - cookie: " + cookie);
	 * 
	 * // Set the Accept header
	 * HttpHeaders requestHeaders = new HttpHeaders();
	 * requestHeaders.setContentType(new MediaType("application", "json"));
	 * requestHeaders.add("cookie", cookie);
	 * HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
	 * 
	 * // Create a new RestTemplate instance
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * // Add the Gson message converter
	 * restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	 * 
	 * // Make the HTTP GET request
	 * ResponseEntity<DeviceVO[]> responseEntity = restTemplate.exchange(url,
	 * HttpMethod.GET, requestEntity, DeviceVO[].class);
	 * DeviceVO[] devices = responseEntity.getBody();
	 * 
	 * Log.d(TAG, "simpleGet - result: " + devices.length);
	 * 
	 * return devices;
	 * }
	 */
	public static FleetVO getGson(String url) {
		Log.d(TAG, "simpleGet - url: " + url);

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		// Make the HTTP GET request
		ResponseEntity<FleetVO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, FleetVO.class);
		FleetVO fleetVO = responseEntity.getBody();

		Log.d(TAG, "simpleGet - result: " + responseEntity.getBody());

		return fleetVO;
	}
	/*
	 * public static void putGson(String url, String cookie, DeviceVO deviceVO)
	 * {
	 * Log.d(TAG, "postGson - url: " + url);
	 * Log.d(TAG, "postGson - cookie: " + cookie);
	 * 
	 * // Set the Content-Type header
	 * HttpHeaders requestHeaders = new HttpHeaders();
	 * requestHeaders.setContentType(new MediaType("application", "json"));
	 * requestHeaders.add("cookie", cookie);
	 * HttpEntity<DeviceVO> requestEntity = new HttpEntity<DeviceVO>(deviceVO,
	 * requestHeaders);
	 * 
	 * // Create a new RestTemplate instance
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * // Add the Jackson and String message converters
	 * restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
	 * restTemplate.getMessageConverters().add(new
	 * StringHttpMessageConverter());
	 * 
	 * // Make the HTTP POST request, marshaling the request to JSON, and the
	 * // response to a String
	 * ResponseEntity<Object> responseEntity = restTemplate.exchange(url,
	 * HttpMethod.PUT, requestEntity, Object.class);
	 * 
	 * Log.d(TAG, "postGson - result: " + responseEntity.getBody());
	 * }
	 */
}
