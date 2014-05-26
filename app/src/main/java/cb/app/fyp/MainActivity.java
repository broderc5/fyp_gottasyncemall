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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import cb.app.fyp.Drive.DriveActivity;
import cb.app.fyp.UI.navdrawer.AboutFragment;
import cb.app.fyp.UI.navdrawer.HomeFragment;
import cb.app.fyp.UI.navdrawer.PlanetFragment;
import cb.app.fyp.UI.tabs.NullFragment;

public class MainActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	//TODO Remove
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private final String DATA = "data";
	private static final String PATH = "path";
	private String[] drawerItemTitles;
	Fragment [] fragments = new Fragment[10];
	byte[] file;

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

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	public void setResult(List<String> paths, List<byte[]> bytes){
		Intent intent = new Intent(this, DriveActivity.class);
		for (int i = 0; i < bytes.size(); i++) {
			intent.putExtra(PATH+i, paths.get(i));
			intent.putExtra(DATA+i, bytes.get(i));
		}
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment = new NullFragment();
		switch (position){
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				//Placeholder
				fragment = new PlanetFragment();
				break;
			case 2:
				fragment = new AboutFragment();

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
