package com.jalvaro.velobleu.client.fragments;

import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class FavoriteListFragment extends ArrayListFragment {

	@Override
	protected StationVO[] getList() {
		FleetVO favFleet = ((VeloApp) activity.getApplication()).getFavouriteFleetVO();
		FleetVO fleet = ((VeloApp) activity.getApplication()).getFleetVO();

		return fleet.getSubArrayOfAvailableStationsById(favFleet);
	}

	/*@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		info.id = Tabs.FAVORITES.getId();
		menu.setHeaderTitle(((VeloApp) activity.getApplication()).getFleetVO().getStation(info.position).getDescription());
		String[] menuItems = { getString(R.string.text_delete_from_fav), getString(R.string.text_watch_at_map) };
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		if (info.id == Tabs.FAVORITES.getId()) {
			int menuItemIndex = item.getItemId();
			if (menuItemIndex == 0) {
				activity.deleteFavouriteStation(info.position);
			}
			return true;
		}

		return false;
	}*/
}