package com.jalvaro.velolibrary.client.fragments;

import java.text.ParseException;
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
import com.jalvaro.velolibrary.client.R;
import com.jalvaro.velolibrary.client.activities.MainActivity;
import com.jalvaro.velolibrary.client.application.Constants;
import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;
import com.jalvaro.velolibrary.client.views.MyCheckBox;

public class MapFragment extends SherlockFragment implements Updatable {

	private final static String TAG = MapFragment.class.getName();
	private GoogleMap map;
	private Circle currentCircle;
	private OnMyLocationChangeListener listener;
	private LinearLayout infoLayout;
	private TextView streetText;
	private TextView freeSlotsText;
	private TextView occupiedSlotsText;
	private MyCheckBox favCheckBox;
	private TextView lastUpdateMillisText;
	private Handler mMinutesHandler;
	private Runnable mMinutesRunnable;
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
		} catch (Exception e) {
			Log.d(TAG, "Exception thrown: Creating map fragment...");
			e.printStackTrace();
		}

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		onHandleUpdateMessage();
		startUpdatingMinutesLayout();
		locateMeOnResume();
	}

	@Override
	public void onStop() {
		super.onStop();
		stopUpdatingMinutesLayout();
	}

	@Override
	public void onHandleUpdateMessage() {
		addStations();
		showLastMinutesUpdate();
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
		favCheckBox = (MyCheckBox) rootView.findViewById(R.id.fav_check);
		lastUpdateMillisText = (TextView) rootView.findViewById(R.id.lastUpdateMillisText);

		listener = new OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location loc) {
				moveCamera(new LatLng(loc.getLatitude(), loc.getLongitude()));

				map.setOnMyLocationChangeListener(null);
			}
		};

		centerInMap();
	}

	private void centerInMap() {
		double latMapCenter = Double.parseDouble(getResources().getString(R.string.lat_map_center));
		double lngMapCenter = Double.parseDouble(getResources().getString(R.string.lng_map_center));
		moveCamera(new LatLng(latMapCenter, lngMapCenter));
	}
	
	private void moveCamera(LatLng latlng) {
		int zoomMap = getResources().getInteger(R.integer.zoom_map);
		moveCamera(latlng, zoomMap);
	}

	private void moveCamera(LatLng latlng, int zoomInt) {
		CameraUpdate center = CameraUpdateFactory.newLatLng(latlng);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomInt);

		map.moveCamera(center);
		map.animateCamera(zoom);
	}

	public void decideWhereToCenterMap() {
		if (isAdded()) {
			StationVO selectedStationVO = activity.getSelectedStation();
			if (selectedStationVO != null) {
				showMarkerInfo(selectedStationVO.getId());
				activity.setSelectedStation(null);
				LatLng latLng = new LatLng(selectedStationVO.getLatitude(), selectedStationVO.getLongitude());
				moveCamera(latLng);
			} else if (currentMarkerId != Constants.INIT_VALUE) {
				showMarkerInfo(currentMarkerId);
				StationVO stationVO = ((VeloApp) activity.getApplication()).getFleetVO().getStationById(currentMarkerId);
				LatLng latLng = new LatLng(stationVO.getLatitude(), stationVO.getLongitude());
				moveCamera(latLng);
			} else {
				locateMe();
			}
		}
	}

	private void locateMeOnResume() {
		if (map != null) {
			if (!map.isMyLocationEnabled()) {
				locateMe();
			}
		}
	}

	private void locateMe() {
		map.setMyLocationEnabled(true);
		map.setOnMyLocationChangeListener(listener);
	}

	private void addStations() {
		if (activity != null) {
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
	}

	private void showLastMinutesUpdate() {
		if (activity != null) {
			FleetVO fleetVO = ((VeloApp) activity.getApplication()).getFleetVO();
			
			if (fleetVO != null) {
				if (fleetVO.getStations().length == 0) {
					activity.update();
				}
				SimpleDateFormat formatter = new SimpleDateFormat(Constants.JSON_TIME_FORMATTER, Locale.getDefault());
				try {
					Calendar cal = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(formatter.parse(fleetVO.getDate()));
					long diff = cal.getTimeInMillis() - cal2.getTimeInMillis();

					if (diff < 30 * Constants.SECOND) {
						lastUpdateMillisText.setText(R.string.text_just_now);
					} else if (diff < Constants.MINUTE) {
						lastUpdateMillisText.setText(R.string.text_more_30_sec);
					} else if (diff < 2 * Constants.MINUTE) {
						lastUpdateMillisText.setText(R.string.text_1_minute_ago);
					} else if (diff < Constants.HOUR) {
						lastUpdateMillisText.setText(getString(R.string.text_x_minutes_ago, diff / Constants.MINUTE));
					} else {
						SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.ANDROID_TIME_FORMATTER, Locale.getDefault());
						lastUpdateMillisText.setText(dateFormat.format(cal2.getTime()));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Inicia la rutina que s'activa cada STATUS_TEXT_MILLIS segons per a
	 * actualitzar el temps que fa que s'ha sincronitzat amb el servidor.
	 * Barra superior amb el temps en segons, minuts o hores...
	 */
	private void startUpdatingMinutesLayout() {
		mMinutesHandler = new Handler();
		mMinutesRunnable = new Runnable() {

			@Override
			public void run() {
				showLastMinutesUpdate();
				mMinutesHandler.postDelayed(mMinutesRunnable, Constants.STATUS_TEXT_MILLIS);
			}
		};
		mMinutesHandler.postDelayed(mMinutesRunnable, Constants.STATUS_TEXT_MILLIS);
	}

	/**
	 * Finalitza la rutina que s'activa cada STATUS_TEXT_MILLIS segons per a
	 * actualitzar el temps que fa que s'ha sincronitzat amb el servidor.
	 * Barra superior amb el temps en segons, minuts o hores...
	 */
	private void stopUpdatingMinutesLayout() {
		if (mMinutesHandler != null) {
			mMinutesHandler.removeCallbacks(mMinutesRunnable);
		}
	}

	private void clearMarkerInfo() {
		currentMarkerId = Constants.INIT_VALUE;
		infoLayout.setVisibility(View.INVISIBLE);
		map.setPadding(0, 0, 0, 0);
		favCheckBox.setOnCheckedChangeListener(null);
		if (currentCircle != null) {
			currentCircle.remove();
		}
	}

	private void showMarkerInfo(int id) {
		currentMarkerId = id;
		StationVO stationVO = ((VeloApp) activity.getApplication()).getFleetVO().getStationById(id);
		map.setPadding(0, infoLayout.getHeight(), 0, 0);
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
		favCheckBox.fill(stationVO.getId(), stationVO.isFavourite(), activity.getOnCheckedChangedListener());
	}
}
