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

package cb.app.fyp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cb.app.fyp.UI.adapters.AppSectionsPagerAdapter;
import cb.app.fyp.UI.tabs.NullFragment;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	AppSectionsPagerAdapter appSectionsPagerAdapter;

	//TODO Remove
	private DrawerLayout drawerLayout;
	private ListView drawerListView;

	private String[] drawerItemTitles;
	Fragment [] fragments = new Fragment[10];

	/**
	 * The {@link android.support.v4.view.ViewPager} that will display the three primary sections of the app, one at a
	 * time.
	 */


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TODO clean up!!
		//mTitle = mDrawerTitle = getTitle();
		drawerItemTitles = getResources().getStringArray(R.array.nav_drawer_array);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerItemTitles));
		drawerListView.setOnItemClickListener(new DrawerItemClickListener());

		 /*ActionBarDrawerToggle ties together the the proper interactions
		 between the sliding drawer and the action bar app icon*/
		/*mDrawerToggle = new ActionBarDrawerToggle(
				this,                  *//* host Activity *//*
				drawerLayout,         *//* DrawerLayout object *//*
				R.drawable.ic_launcher,  *//* nav drawer image to replace 'Up' caret *//*
				R.string.drawer_open,  *//* "open drawer" description for accessibility *//*
				R.string.drawer_close  *//* "close drawer" description for accessibility *//*
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		drawerLayout.setDrawerListener(mDrawerToggle);*/

		/*if (savedInstanceState == null) {
			fragments[0] = selectNewItem(2);
		}*/

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.activity_main, container, false);
	}*/


	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
		/*@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(fragments[position] != null) {
				selectItem(fragments[position], position);
			}
			else{
				fragments[position] = selectNewItem(position);
			}
		}*/
	}

	/*private Fragment selectNewItem(int position) {
		Fragment fragment = new NullFragment();
		FragmentManager fragmentManager = getFragmentManager();

		switch (position){
			case 0:
				fragment = fragmentManager.findFragmentByTag("HomeFragment");
				break;
			case 1:
				fragment = new PlanetFragment();
				break;
			case 2:
				fragment = new HomeFragment();
				break;
		}
		// update the main content by replacing fragments
		if (fragment != null) {

			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			// update selected item and title, then close the drawer
			//drawerListView.setItemChecked(position, true);
			//setTitle(drawerItemTitles[position]);
			drawerLayout.closeDrawer(drawerListView);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		return fragment;
	}*/

	private void selectItem(int position) {
		Fragment fragment = new NullFragment();
		switch (position){
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				//Placeholder
				fragment = new PlanetFragment();

		}
		// update the main content by replacing fragments
		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			drawerListView.setItemChecked(position, true);
			setTitle(drawerItemTitles[position]);
			drawerLayout.closeDrawer(drawerListView);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
}
