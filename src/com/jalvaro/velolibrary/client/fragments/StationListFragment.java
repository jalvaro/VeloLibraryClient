package com.jalvaro.velolibrary.client.fragments;

import com.jalvaro.velolibrary.client.application.VeloApp;
import com.jalvaro.velolibrary.client.models.FleetVO;
import com.jalvaro.velolibrary.client.models.StationVO;

public class StationListFragment extends ArrayListFragment {
	private final static String TAG = StationListFragment.class.getName();

	@Override
	protected StationVO[] getList() {
		FleetVO fleet = ((VeloApp) activity.getApplication()).getFleetVO();

		return fleet.getSubArrayOfAvailableStations();
	}

	/*@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		info.id = Tabs.STATIONS.getId();
		menu.setHeaderTitle(((VeloApp) activity.getApplication()).getFleetVO().getStation(info.position).getDescription());
		String[] menuItems = { getString(R.string.text_add_to_fav) };
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		if (info.id == Tabs.STATIONS.getId()) {
			int menuItemIndex = item.getItemId();
			if (menuItemIndex == 0) {
				activity.addFavouriteStation(info.position);
			}
			return true;
		}

		return false;
	}*/
}