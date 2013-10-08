/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jalvaro.velobleu.client.activities;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jalvaro.velobleu.client.R;
import com.jalvaro.velobleu.client.application.Constants;
import com.jalvaro.velobleu.client.application.VeloApp;
import com.jalvaro.velobleu.client.controllers.AddFavouriteStationController;
import com.jalvaro.velobleu.client.controllers.Controller;
import com.jalvaro.velobleu.client.controllers.Controller.VeloHandler;
import com.jalvaro.velobleu.client.controllers.DeleteFavouriteStationController;
import com.jalvaro.velobleu.client.controllers.UpdateController;
import com.jalvaro.velobleu.client.fragments.FavoriteListFragment;
import com.jalvaro.velobleu.client.fragments.MapFragment;
import com.jalvaro.velobleu.client.fragments.StationListFragment;
import com.jalvaro.velobleu.client.fragments.Updatable;
import com.jalvaro.velobleu.client.models.StationVO;
import com.jalvaro.velobleu.client.views.MyItemArrayLayout;

public class MainActivity extends SherlockFragmentActivity implements TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the
	 * three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	private AtomicBoolean mIsUpdating;
	private AtomicBoolean mIsAddingDeletingFavStation;
	private UpdateController mUpdateController;
	private AddFavouriteStationController mAddFavStationController;
	private DeleteFavouriteStationController mDeleteFavStationController;
	private VeloHandler mMapHandler;
	private VeloHandler mAddFavStationHandler;
	private VeloHandler mDeleteFavStationHandler;
	private long mLastUpdateMillis = Constants.INIT_VALUE;
	private OnCheckedChangeListener onCheckedChangeListener;
	private StationVO selectedStationVO;
	private final static String TAG = MainActivity.class.getName();

	private Updatable[] mFragments;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a
	 * time.
	 */
	private ViewPager mViewPager;

	public enum Tabs {
		MAP(0, R.string.title_tab_map), FAVORITES(1, R.string.title_tab_fav), STATIONS(2, R.string.title_tab_stations);

		private int id;
		private int resId;

		Tabs(int id, int resId) {
			this.id = id;
			this.resId = resId;
		}

		public int getId() {
			return id;
		}

		public int getResId() {
			return resId;
		}

		public static Tabs getTabById(int id) {
			for (Tabs t : Tabs.values()) {
				if (t.id == id) {
					return t;
				}
			}
			return null;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the
				// corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have
				// a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		update();
	}

	public long getLastUpdate() {
		return mLastUpdateMillis;
	}

	private void init() {
		mFragments = new Updatable[Tabs.values().length];
		mIsUpdating = new AtomicBoolean();
		mIsAddingDeletingFavStation = new AtomicBoolean();
		mUpdateController = new UpdateController((VeloApp) getApplication());
		mAddFavStationController = new AddFavouriteStationController((VeloApp) getApplication());
		mDeleteFavStationController = new DeleteFavouriteStationController((VeloApp) getApplication());

		mMapHandler = new VeloHandler((VeloApp) getApplication(), mIsUpdating) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mLastUpdateMillis = Calendar.getInstance().getTimeInMillis();
				onHandleUpdateMessage();
				Toast.makeText(MainActivity.this, R.string.toast_service_updated, Toast.LENGTH_LONG).show();
				setWorking(false);
			}

			@Override
			public void handleError(Message msg) {
				super.handleError(msg);
				onHandleUpdateError();
				Toast.makeText(MainActivity.this, R.string.toast_service_not_updated, Toast.LENGTH_LONG).show();
				setWorking(false);
			}
		};

		mAddFavStationHandler = new VeloHandler((VeloApp) getApplication(), mIsAddingDeletingFavStation) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onHandleUpdateMessage();
				Toast.makeText(MainActivity.this, "Se ha a–adido en favs.", Toast.LENGTH_LONG).show();
				setWorking(false);
			}

			@Override
			public void handleError(Message msg) {
				super.handleError(msg);
				onHandleUpdateError();
				Toast.makeText(MainActivity.this, "Ya esta en favs.", Toast.LENGTH_LONG).show();
				Toast.makeText(MainActivity.this, "No se ha podido guardar en favs.", Toast.LENGTH_LONG).show();
				setWorking(false);
			}
		};

		mDeleteFavStationHandler = new VeloHandler((VeloApp) getApplication(), mIsAddingDeletingFavStation) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onHandleUpdateMessage();
				Toast.makeText(MainActivity.this, "Se ha eliminado de favs.", Toast.LENGTH_LONG).show();
				setWorking(false);
			}

			@Override
			public void handleError(Message msg) {
				super.handleError(msg);
				onHandleUpdateError();
				Toast.makeText(MainActivity.this, "No se ha podido guardar en favs.", Toast.LENGTH_LONG).show();
				setWorking(false);
			}
		};
	}

	public void onHandleUpdateMessage() {
		for (Updatable u : mFragments) {
			if (u != null) {
				u.onHandleUpdateMessage();
			}
		}
	}

	public void onHandleUpdateError() {
		for (Updatable u : mFragments) {
			if (u != null) {
				u.onHandleUpdateError();
			}
		}
	}

	public void addFavouriteStation(int id) {
		if (mIsAddingDeletingFavStation.compareAndSet(false, true)) {
			mAddFavStationController.add(mAddFavStationHandler, id);
		}
	}

	public void deleteFavouriteStation(int id) {
		if (mIsAddingDeletingFavStation.compareAndSet(false, true)) {
			mDeleteFavStationController.delete(mDeleteFavStationHandler, id);
		}
	}

	void update() {
		if (mIsUpdating.compareAndSet(false, true)) {
			mUpdateController.update(mMapHandler);
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary
	 * sections of the app.
	 */
	public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			initAllSections();
		}

		@Override
		public Fragment getItem(int i) {
			initSection(i);
			return (Fragment) mFragments[i];
		}

		@Override
		public int getCount() {
			return Tabs.values().length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Tabs t = Tabs.getTabById(position);
			return (t != null) ? getString(t.getResId()) : "";
		}

		private void initAllSections() {
			initSection(Tabs.MAP.getId());
			initSection(Tabs.FAVORITES.getId());
			initSection(Tabs.STATIONS.getId());
		}

		private void initSection(int id) {
			Tabs t = Tabs.getTabById(id);
			if (mFragments[id] == null) {
				switch (t) {
				case MAP:
					mFragments[id] = new MapFragment();
					break;
				case FAVORITES:
					mFragments[id] = new FavoriteListFragment();
					break;
				case STATIONS:
					mFragments[id] = new StationListFragment();
					break;
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "item selected: " + item.getItemId());
		switch (item.getItemId()) {
		case R.id.menu_update:
			update();
			break;
		case R.id.menu_about:
			Controller.startAboutActivity(this);
			break;
		default:
			break;
		}
		return false;
	}

	public OnCheckedChangeListener getOnCheckedChangedListener() {
		if (onCheckedChangeListener == null) {
			onCheckedChangeListener = new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					int id = MyItemArrayLayout.getStationId(arg0);
					if (arg1) {
						addFavouriteStation(id);
					} else {
						deleteFavouriteStation(id);
					}
				}
			};
		}
		return onCheckedChangeListener;
	}
	
	public void setSelectedStation(StationVO selectedStationVO) {
		this.selectedStationVO = selectedStationVO;
	}
	
	public StationVO getSelectedStation() {
		return selectedStationVO;
	}
}
