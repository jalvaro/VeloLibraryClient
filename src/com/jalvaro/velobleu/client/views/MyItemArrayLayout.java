package com.jalvaro.velobleu.client.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jalvaro.velobleu.client.R;

public class MyItemArrayLayout extends LinearLayout {
	public static final int RESOURCE_ID = R.layout.item_array;
	
	private TextView titleView;
	private TextView descriptionView;
	private MyCheckBox starCheckBox;

	public MyItemArrayLayout(Context context) {
		super(context);
	}

	public MyItemArrayLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void initialize() {
		titleView = (TextView) findViewById(R.id.item_fav_title);
		descriptionView = (TextView) findViewById(R.id.item_fav_text);
		starCheckBox = (MyCheckBox) findViewById(R.id.item_fav_check);
	}

	public void fill(String title, String text, int stationId, boolean checked, OnCheckedChangeListener listener) {
		initialize();

		titleView.setText(title);
		descriptionView.setText(text);
		starCheckBox.fill(stationId, checked, listener);
	}
	
	public int getStationId() {
		return starCheckBox.getStationId();
	}
	
	public static int getStationId(CompoundButton starCheckBox) {
		return ((MyCheckBox) starCheckBox).getStationId();
	}
}
