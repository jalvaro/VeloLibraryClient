package com.jalvaro.velolibrary.client.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jalvaro.velolibrary.client.activities.MainActivity.Tabs;

public class ViewPagerMapNoScroll extends ViewPager {
	
	public ViewPagerMapNoScroll(Context context) {
		super(context);
	}
	
	public ViewPagerMapNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v instanceof ViewPagerMapNoScroll) {
                        // the vertCollectionPagerAdapter has logic on getItem that determines if the next fragment should have swipe disabled
			int id = ((ViewPagerMapNoScroll) v).getCurrentItem();
			//AppSectionsPagerAdapter a = (AppSectionsPagerAdapter) ((ViewPagerMapNoScroll)v).getAdapter();
			Log.d("CRUDUtils", "" + id);
			if (id == Tabs.MAP.getId())
				return true;
			else
				return false;
		}
		
		return super.canScroll(v, checkV, dx, x, y);
	}
	
}