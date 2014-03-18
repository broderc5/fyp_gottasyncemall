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

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cb.app.fyp.UI.adapters.AppSectionsPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	AppSectionsPagerAdapter appSectionsPagerAdapter;
	ViewPager viewPager;

	//TODO Remove
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	//private ActionBarDrawerToggle mDrawerToggle;

	/*private CharSequence mDrawerTitle;
	private CharSequence mTitle;*/
	private String[] drawerItemTitles;

	/**
	 * The {@link android.support.v4.view.ViewPager} that will display the three primary sections of the app, one at a
	 * time.
	 */


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*Create the adapter that will return a fragment for each of the three primary sections
		of the app.*/
		appSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(appSectionsPagerAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
			}
		});

		/*Create two tabs for the apps two functions with a specific icon for each*/
		actionBar.addTab(
				actionBar.newTab()
						.setIcon(R.drawable.ic_sync_to_cloud)
						.setTabListener(this));

		actionBar.addTab(
				actionBar.newTab()
						.setIcon(R.drawable.ic_send_to_device)
						.setTabListener(this));


		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		//}

		//TODO clean up!!
		//mTitle = mDrawerTitle = getTitle();
		drawerItemTitles = getResources().getStringArray(R.array.planets_array);
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

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = new PlanetFragment();
		Bundle args = new Bundle();
		args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		fragment.setArguments(args);

		android.app.FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.pager, fragment).commit();

		// update selected item and title, then close the drawer
		drawerListView.setItemChecked(position, true);
		setTitle(drawerItemTitles[position]);
		drawerLayout.closeDrawer(drawerListView);
	}
}
