package com.jalvaro.velolibrary.client.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.jalvaro.velolibrary.client.R;

public class MyCheckBox extends CheckBox {
	public static final int RESOURCE_ID = R.layout.item_array;

	private int stationId;

	public MyCheckBox(Context context) {
		super(context);
	}

	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void fill(int stationId, boolean checked, OnCheckedChangeListener listener) {
		setOnCheckedChangeListener(null);
		setChecked(checked);
		this.stationId = stationId;
		setOnCheckedChangeListener(listener);
	}

	public int getStationId() {
		return stationId;
	}
}
