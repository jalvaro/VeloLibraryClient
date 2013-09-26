package com.jalvaro.velobleu.client.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.activities.MainActivity;

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

	protected abstract void setList();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("FragmentList", "Item clicked: " + id);
	}

	@Override
	public void onHandleMessage() {
		setList();
	}

	@Override
	public void onHandleError() {
		// TODO Auto-generated method stub
	}
}