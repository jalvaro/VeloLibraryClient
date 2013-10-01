package com.jalvaro.velobleu.client.fragments;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.exceptions.VeloException;
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle(((VeloApp) activity.getApplication()).getFleetVO().getStation(info.position).getDescription());
		String[] menuItems = { getString(R.string.text_delete_from_fav), getString(R.string.text_watch_at_map) };
		for (int i = 0; i < menuItems.length; i++) {
			menu.add(Menu.NONE, i, i, menuItems[i]);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			VeloApp app = (VeloApp) activity.getApplication();
			FleetVO favFleetVO = app.getFavouriteFleetVO();
			StationVO stationVO = favFleetVO.getStation(info.position);
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

		return false;
	}
}