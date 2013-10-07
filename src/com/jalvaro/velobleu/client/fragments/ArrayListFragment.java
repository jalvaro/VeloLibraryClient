package com.jalvaro.velobleu.client.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.activities.MainActivity;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.exceptions.VeloException;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;
import com.jalvaro.velobleu.client.views.MyArrayListAdapter;

public abstract class ArrayListFragment extends SherlockListFragment implements Updatable {
	protected MainActivity activity;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activity = (MainActivity) getSherlockActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
		return v;
	}

	protected void setList() {
		if (activity != null) {
			MyArrayListAdapter adapter = new MyArrayListAdapter(activity, getList(), getOnStarClickedListener());
			if (getListView() != null) {
				getListView().setAdapter(adapter);
				registerForContextMenu(getListView());
			}
		}
	}

	protected abstract StationVO[] getList();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setList();
		registerForContextMenu(getListView());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
	}

	@Override
	public void onHandleUpdateMessage() {
		setList();
	}

	@Override
	public void onHandleUpdateError() {
		// TODO Auto-generated method stub
	}

	protected OnCheckedChangeListener getOnStarClickedListener() {
		return new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					addFavouriteStation(arg0.getId());
				} else {
					deleteFavouriteStation(arg0.getId());
				}
			}
		};
	}

	protected void addFavouriteStation(int id) {
		VeloApp app = (VeloApp) activity.getApplication();
		FleetVO fleetVO = app.getFleetVO();
		FleetVO favFleetVO = app.getFavouriteFleetVO();
		StationVO stationVO = fleetVO.getStationById(id);
		if (favFleetVO.getStationById(id) == null) {
			try {
				app.addFavouriteStationVO(stationVO);
				activity.onHandleUpdateMessage();
				Toast.makeText(activity, "Se ha a–adido en favs.", Toast.LENGTH_LONG).show();
			} catch (VeloException e) {
				// TODO Auto-generated catch block
				Toast.makeText(activity, "No se ha podido guardar en favs.", Toast.LENGTH_LONG).show();
				activity.onHandleUpdateError();
				e.printStackTrace();
			}
		} else {
			Toast.makeText(activity, "Ya esta en favs.", Toast.LENGTH_LONG).show();
			activity.onHandleUpdateError();
		}
	}

	protected void deleteFavouriteStation(int id) {
		VeloApp app = (VeloApp) activity.getApplication();
		FleetVO favFleetVO = app.getFavouriteFleetVO();
		StationVO stationVO = favFleetVO.getStationById(id);
		try {
			app.deleteFavouriteStationVO(stationVO);
			activity.onHandleUpdateMessage();
			Toast.makeText(activity, "Se ha eliminado de favs.", Toast.LENGTH_LONG).show();
		} catch (VeloException e) {
			// TODO Auto-generated catch block
			Toast.makeText(activity, "No se ha podido guardar en favs.", Toast.LENGTH_LONG).show();
			activity.onHandleUpdateError();
			e.printStackTrace();
		}
	}
}