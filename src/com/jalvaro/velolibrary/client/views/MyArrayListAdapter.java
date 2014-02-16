package com.jalvaro.velolibrary.client.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jalvaro.velolibrary.client.models.StationVO;


public class MyArrayListAdapter extends BaseAdapter {
	private Context context;
	private List<MyItemArrayLayout> items;
	private StationVO[] stationsVO;
	private OnCheckedChangeListener onStarClickedListener;

	public static final String TAG = "DefaultAdapter";

	public MyArrayListAdapter(Context context, StationVO[] stationsVO, OnCheckedChangeListener onStarClickedListener) {
		super();

		Log.d(TAG, "Constructor inicio");
		this.context = context;
		this.stationsVO = stationsVO;
		this.items = new ArrayList<MyItemArrayLayout>();
		this.onStarClickedListener = onStarClickedListener;
	}

	// public void addItems(List<MyFavoriteLayout> items) {
	// this.items = items;
	// notifyDataSetChanged();
	// }

	@Override
	public int getCount() {
		return stationsVO.length;
	}

	@Override
	public MyItemArrayLayout getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		Log.d(TAG, "pos: " + position + ", size: " + items.size());
		if (items.size() > position) {
			v = items.get(position);
		}
		if (v == null) {
			StationVO stationVO = stationsVO[position];
			Log.d(TAG, "new " + position);
			LayoutInflater li = ((Activity) context).getLayoutInflater();
			v = li.inflate(MyItemArrayLayout.RESOURCE_ID, null);
			MyItemArrayLayout favLayout = (MyItemArrayLayout) v;
			String text = "Free: " + stationVO.getTotalFreeSlots() + " - Occupied: " + stationVO.getTotalOccupiedSlots();
			favLayout.fill(stationVO.toString(), text, stationVO.getId(), stationVO.isFavourite(), onStarClickedListener);

			items.add(favLayout);
		}

		return v;
	}
	
	public void setOnClickStar() {
		
	}
}
