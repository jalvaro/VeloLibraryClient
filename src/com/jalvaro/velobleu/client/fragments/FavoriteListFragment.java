package com.jalvaro.velobleu.client.fragments;

import android.widget.ArrayAdapter;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class FavoriteListFragment extends ArrayListFragment {

	@Override
	protected void setList() {
		try {
			FleetVO favFleet = ((VeloApp) activity.getApplication()).getFavouriteFleetVO();
			FleetVO fleet = ((VeloApp) activity.getApplication()).getFleetVO();
			setListAdapter(new ArrayAdapter<StationVO>(getActivity(), android.R.layout.simple_list_item_1,
					fleet.getSubArrayOfAvailableStationsById(favFleet)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}