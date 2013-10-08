package com.jalvaro.velobleu.client.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
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
import com.jalvaro.velobleu.client.activities.MainActivity;
import com.jalvaro.velobleu.client.application.Constants;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class MapFragment extends SherlockFragment implements Updatable {

	private final static String TAG = MapFragment.class.getName();
	private GoogleMap map;
	private Circle currentCircle;
	private OnMyLocationChangeListener listener;
	private LinearLayout infoLayout;
	private TextView streetText;
	private TextView freeSlotsText;
	private TextView occupiedSlotsText;
	// private TextView disabledSlotsText;
	private CheckBox favCheckBox;
	private TextView lastUpdateMillisText;
	private Handler mHandler;
	private Runnable mRunnable;
	private int currentMarkerId = Constants.INIT_VALUE;
	private static View rootView;
	private MainActivity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
				parent.removeView(rootView);
		}
		try {
			rootView = inflater.inflate(R.layout.activity_map, container, false);
			init();
			onHandleUpdateMessage();
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
			Log.d(TAG, "Exception thrown: map is already there, just return view as it is");
			e.printStackTrace();
		}

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		addStations();
		showLastUpdate();
		if (currentMarkerId != Constants.INIT_VALUE) {
			showMarkerInfo(currentMarkerId);
		}
		startUpdatingStatusLayout();
		locate();
	}

	@Override
	public void onStop() {
		super.onStop();
		stopUpdatingStatusLayout();
	}

	@Override
	public void onHandleUpdateMessage() {
		addStations();
		showLastUpdate();
		if (currentMarkerId != Constants.INIT_VALUE) {
			showMarkerInfo(currentMarkerId);
		}
	}

	@Override
	public void onHandleUpdateError() {
		clearMarkerInfo();
	}

	private void init() {
		activity = (MainActivity) getSherlockActivity();
		map = ((SupportMapFragment) activity.getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		infoLayout = (LinearLayout) rootView.findViewById(R.id.infoLinear);
		infoLayout.setVisibility(View.INVISIBLE);
		streetText = (TextView) rootView.findViewById(R.id.streetText);
		freeSlotsText = (TextView) rootView.findViewById(R.id.freeSlotsText);
		occupiedSlotsText = (TextView) rootView.findViewById(R.id.occupiedSlotsText);
		favCheckBox = (CheckBox) rootView.findViewById(R.id.fav_check);
		// disabledSlotsText = (TextView)
		// rootView.findViewById(R.id.disabledSlotsText);
		lastUpdateMillisText = (TextView) rootView.findViewById(R.id.lastUpdateMillisText);

		listener = new OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location loc) {
				moveCamera(new LatLng(loc.getLatitude(), loc.getLongitude()), Constants.ZOOM);

				map.setOnMyLocationChangeListener(null);
			}
		};

		center();
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
		FleetVO fleetVO = ((VeloApp) activity.getApplication()).getFleetVO();
		if (fleetVO != null) {
			map.clear();
			for (StationVO stationVO : fleetVO.getSubArrayOfAvailableStations()) {
				map.addMarker(new MarkerOptions().position(new LatLng(stationVO.getLatitude(), stationVO.getLongitude()))
						.icon(BitmapDescriptorFactory.fromResource(stationVO.getDrawable())).title(Integer.toString(stationVO.getId())));
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
	}

	private void showLastUpdate() {
		long lastUpdateMillis = activity.getLastUpdate();
		if (lastUpdateMillis != Constants.INIT_VALUE) {
			Calendar cal = Calendar.getInstance();
			long diff = cal.getTimeInMillis() - lastUpdateMillis;

			if (diff < 30 * Constants.SECOND) {
				lastUpdateMillisText.setText(R.string.text_just_now);
			} else if (diff < Constants.MINUTE) {
				lastUpdateMillisText.setText(R.string.text_more_30_sec);
			} else if (diff < 2 * Constants.MINUTE) {
				lastUpdateMillisText.setText(R.string.text_1_minute_ago);
			} else if (diff < Constants.HOUR) {
				lastUpdateMillisText.setText(getString(R.string.text_x_minutes_ago, diff / Constants.MINUTE));
			} else {
				cal.setTimeInMillis(lastUpdateMillis);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
				lastUpdateMillisText.setText(dateFormat.format(cal.getTime()));
			}
		}
	}

	/**
	 * Inicia la rutina que s'activa cada STATUS_TEXT_MILLIS segons per a
	 * actualitzar el temps que fa que s'ha sincronitzat amb el servidor.
	 * Barra superior amb el temps en segons, minuts o hores...
	 */
	private void startUpdatingStatusLayout() {
		mHandler = new Handler();
		mRunnable = new Runnable() {

			@Override
			public void run() {
				showLastUpdate();
				mHandler.postDelayed(mRunnable, Constants.STATUS_TEXT_MILLIS);
			}
		};
		mHandler.postDelayed(mRunnable, Constants.STATUS_TEXT_MILLIS);
	}

	/**
	 * Finalitza la rutina que s'activa cada STATUS_TEXT_MILLIS segons per a
	 * actualitzar el temps que fa que s'ha sincronitzat amb el servidor.
	 * Barra superior amb el temps en segons, minuts o hores...
	 */
	private void stopUpdatingStatusLayout() {
		if (mHandler != null) {
			mHandler.removeCallbacks(mRunnable);
		}
	}

	private void clearMarkerInfo() {
		currentMarkerId = Constants.INIT_VALUE;
		infoLayout.setVisibility(View.INVISIBLE);
		favCheckBox.setOnCheckedChangeListener(null);
		if (currentCircle != null) {
			currentCircle.remove();
		}
	}

	private void showMarkerInfo(int id) {
		currentMarkerId = id;
		StationVO stationVO = ((VeloApp) activity.getApplication()).getFleetVO().getStationById(id);

		infoLayout.setVisibility(View.VISIBLE);
		updateInfoLayout(stationVO);
		if (currentCircle != null) {
			currentCircle.remove();
		}
		currentCircle = map.addCircle(new CircleOptions().center(new LatLng(stationVO.getLatitude(), stationVO.getLongitude())).radius(60)
				.strokeColor(0xff2CC5EF).strokeWidth(2f).fillColor(0xff2CC5EF));
	}

	private void updateInfoLayout(StationVO stationVO) {
		streetText.setText(stationVO.toString());
		freeSlotsText.setText(getString(R.string.text_free_slots, stationVO.getTotalFreeSlots()));
		occupiedSlotsText.setText(getString(R.string.text_occupied_slots, stationVO.getTotalOccupiedSlots()));
		favCheckBox.setId(stationVO.getId());
		favCheckBox.setOnCheckedChangeListener(null);
		favCheckBox.setChecked(stationVO.isFavourite());
		favCheckBox.setOnCheckedChangeListener(activity.getOnCheckedChangedListener());
		// disabledSlotsText.setText(getString(R.string.text_disabled_slots,
		// stationVO.getDisabledSlots()));
	}
}
