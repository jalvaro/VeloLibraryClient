package com.jalvaro.velobleu.client.fragments;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.activities.MainActivity.Tabs;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.models.FleetVO;
import com.jalvaro.velobleu.client.models.StationVO;

public class StationListFragment extends ArrayListFragment {
	private final static String TAG = StationListFragment.class.getName();

	@Override
	protected StationVO[] getList() {
		FleetVO fleet = ((VeloApp) activity.getApplication()).getFleetVO();

		return fleet.getSubArrayOfAvailableStations();
	}

	@Override
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
	}
}