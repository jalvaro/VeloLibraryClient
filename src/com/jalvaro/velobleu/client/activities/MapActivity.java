package com.jalvaro.velobleu.client.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.application.Constants;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.controllers.Controller;
import com.jalvaro.velobleu.client.controllers.Controller.VeloHandler;
import com.jalvaro.velobleu.client.controllers.MainController;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class MapActivity extends FragmentActivity {

	/*private final static String TAG = MapActivity.class.getName();
	private AtomicBoolean isUpdating;
	private MainController controller;
	private VeloHandler mapHandler;
	private FleetVO fleetVO;
	private GoogleMap map;
	private Circle currentCircle;
	private OnMyLocationChangeListener listener;
	private LinearLayout infoLayout;
	private TextView streetText;
	private TextView freeSlotsText;
	private TextView occupiedSlotsText;
	private TextView disabledSlotsText;
	private TextView lastUpdateMillisText;
	private Handler handler;
	private Runnable runnable;
	private int currentMarkerId = Constants.INIT_VALUE;
	private long lastUpdateMillis = Constants.INIT_VALUE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		infoLayout = (LinearLayout) findViewById(R.id.infoLinear);
		infoLayout.setVisibility(View.INVISIBLE);
		streetText = (TextView) findViewById(R.id.streetText);
		freeSlotsText = (TextView) findViewById(R.id.freeSlotsText);
		occupiedSlotsText = (TextView) findViewById(R.id.occupiedSlotsText);
		disabledSlotsText = (TextView) findViewById(R.id.disabledSlotsText);
		lastUpdateMillisText = (TextView) findViewById(R.id.lastUpdateMillisText);

		isUpdating = new AtomicBoolean();
		controller = new MainController((VeloApp) getApplication());
		fleetVO = new FleetVO();

		mapHandler = controller.new VeloHandler(isUpdating) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				addStations();
				lastUpdateMillis = Calendar.getInstance().getTimeInMillis();
				showLastUpdate();
				if (currentMarkerId != Constants.INIT_VALUE) {
					showMarkerInfo(currentMarkerId);
				}
				Toast.makeText(MapActivity.this, R.string.toast_service_updated, Toast.LENGTH_LONG).show();
				setWorking(false);
			}

			@Override
			public void handleError(Message msg) {
				super.handleError(msg);
				clearMarkerInfo();
				Toast.makeText(MapActivity.this, R.string.toast_service_not_updated, Toast.LENGTH_LONG).show();
				setWorking(false);
			}
		};

		listener = new OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location loc) {
				moveCamera(new LatLng(loc.getLatitude(), loc.getLongitude()), Constants.ZOOM);

				map.setOnMyLocationChangeListener(null);
			}
		};

		center();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "item selected: " + item.getItemId());
		switch (item.getItemId()) {
		case R.id.menu_update:
			update();
			break;
		case R.id.menu_about:
			Controller.startAboutActivity(this);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		update();
		showLastUpdate();
		startUpdatingStatusLayout();
		locate();
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopUpdatingStatusLayout();
	}

	void update() {
		if (isUpdating.compareAndSet(false, true)) {
			controller.update(fleetVO, mapHandler);
		}
	}

	private void center() {
		moveCamera(new LatLng(Constants.LAT_NICE_CENTER, Constants.LON_NICE_CENTER), Constants.ZOOM);
	}

	private void moveCamera(LatLng latlng, int zoomInt) {
		CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomInt);

		map.moveCamera(center);
		map.animateCamera(zoom);
	}

	private void locate() {
		map.setMyLocationEnabled(true);
		map.setOnMyLocationChangeListener(listener);
	}

	private void addStations() {
		map.clear();
		for (StationVO stationVO : fleetVO.getStations()) {
			if (stationVO.isAvailable()) {
				map.addMarker(new MarkerOptions().position(new LatLng(stationVO.getLatitude(), stationVO.getLongitude())).icon(BitmapDescriptorFactory.fromResource(stationVO.getDrawable()))
						.title(Integer.toString(stationVO.getId())));
			}
		}

		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				int id = Integer.valueOf(marker.getTitle());

				if (id == currentMarkerId && infoLayout.getVisibility() == View.VISIBLE) {
					clearMarkerInfo();
				} else {
					showMarkerInfo(id);
				}

				return true;
			}
		});
	}

	private void startUpdatingStatusLayout() {
		handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() {
				showLastUpdate();
				handler.postDelayed(runnable, Constants.STATUS_TEXT_MILLIS);
			}
		};
		handler.postDelayed(runnable, Constants.STATUS_TEXT_MILLIS);
	}

	private void showLastUpdate() {
		if (lastUpdateMillis != Constants.INIT_VALUE) {
			Calendar cal = Calendar.getInstance();
			long diff = cal.getTimeInMillis() - lastUpdateMillis;

			if (diff < 30*Constants.SECOND) {
				lastUpdateMillisText.setText(R.string.text_just_now);
			} else if (diff < Constants.MINUTE) {
				lastUpdateMillisText.setText(R.string.text_more_30_sec);
			} else if (diff < 2*Constants.MINUTE) {
				lastUpdateMillisText.setText(R.string.text_1_minute_ago);
			} else if (diff < Constants.HOUR){
				lastUpdateMillisText.setText(getString(R.string.text_x_minutes_ago, diff / Constants.MINUTE));
			} else {
				cal.setTimeInMillis(lastUpdateMillis);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				lastUpdateMillisText.setText(dateFormat.format(cal.getTime()));
			}
		}
	}

	private void stopUpdatingStatusLayout() {
		handler.removeCallbacks(runnable);
	}

	private void clearMarkerInfo() {
		currentMarkerId = Constants.INIT_VALUE;
		infoLayout.setVisibility(View.INVISIBLE);
		if (currentCircle != null) {
			currentCircle.remove();
		}
	}

	private void showMarkerInfo(int id) {
		currentMarkerId = id;
		StationVO stationVO = fleetVO.getStationById(id);

		infoLayout.setVisibility(View.VISIBLE);
		updateInfoLayout(stationVO);
		if (currentCircle != null) {
			currentCircle.remove();
		}
		currentCircle = map.addCircle(new CircleOptions().center(new LatLng(stationVO.getLatitude(), stationVO.getLongitude())).radius(60).strokeColor(0xff2CC5EF).strokeWidth(2f)
				.fillColor(0xff2CC5EF));
	}

	private void updateInfoLayout(StationVO stationVO) {
		streetText.setText(stationVO.getDescription());
		freeSlotsText.setText(getString(R.string.text_free_slots, stationVO.getTotalFreeSlots()));
		occupiedSlotsText.setText(getString(R.string.text_occupied_slots, stationVO.getTotalOccupiedSlots()));
		disabledSlotsText.setText(getString(R.string.text_disabled_slots, stationVO.getDisabledSlots()));
	}*/
}
