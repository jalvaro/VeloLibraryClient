package com.jalvaro.velolibrary.client.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.jalvaro.velolibrary.client.R;
import com.jalvaro.velolibrary.client.activities.MainActivity;
import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;
import com.jalvaro.velolibrary.client.views.MyArrayListAdapter;
import com.jalvaro.velolibrary.client.views.MyItemArrayLayout;

public abstract class ArrayListFragment extends SherlockListFragment implements Updatable {
	protected MainActivity activity;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("FragmentList", "onCreate");
		activity = (MainActivity) getSherlockActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
		Log.i("FragmentList", "onCreateView");
		return v;
	}

	protected void setList() {
		if (activity != null && isAdded() && getListView() != null) {
			MyArrayListAdapter adapter = new MyArrayListAdapter(activity, getList(), activity.getOnCheckedChangedListener());
			getListView().setAdapter(adapter);
			registerForContextMenu(getListView());
		}
	}

	protected abstract StationVO[] getList();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("FragmentList", "onActivityCreated");
		setList();
		registerForContextMenu(getListView());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
		MyItemArrayLayout item = (MyItemArrayLayout) v.findViewById(R.id.item_fav_my_item_array);
		FleetVO fleetVO = ((VeloApp) activity.getApplication()).getFleetVO();
		activity.setSelectedStation(fleetVO.getStationById(item.getStationId()));
		activity.getSupportActionBar().setSelectedNavigationItem(0);
	}

	@Override
	public void onHandleUpdateMessage() {
		setList();
	}

	@Override
	public void onHandleUpdateError() {
		// TODO Auto-generated method stub
	}

	/*protected void addFavouriteStation(int id) {
		VeloApp app = (VeloApp) activity.getApplication();
		FleetVO fleetVO = app.getFleetVO();
		FleetVO favFleetVO = app.getFavouriteFleetVO();
		StationVO stationVO = fleetVO.getStationById(id);
		if (favFleetVO.getStationById(id) == null) {
			try {
				app.addFavouriteStationVO(stationVO);
				activity.onHandleUpdateMessage();
				Toast.makeText(activity, "Se ha a�adido en favs.", Toast.LENGTH_LONG).show();
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
	}*/

	/*protected void deleteFavouriteStation(int id) {
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
	}*/
}