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

import cb.app.fyp.Drive.DriveActivity;
import cb.app.fyp.UI.navdrawer.AboutFragment;
import cb.app.fyp.UI.navdrawer.HomeFragment;
import cb.app.fyp.UI.navdrawer.MyAppsFragment;
import cb.app.fyp.UI.navdrawer.SettingsFragment;
import cb.app.fyp.UI.tabs.NullFragment;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private static final String PATH = "path";
	private String[] drawerItemTitles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

	public void setResult(String paths){
		Intent intent = new Intent(this, DriveActivity.class);
		intent.putExtra(PATH, paths);

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
				fragment = new MyAppsFragment();
				break;
			case 2:
				//Placeholder
				fragment = new SettingsFragment();
				break;
			case 3:
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
			Log.e(TAG, "Error in creating fragment");
		}
	}
}
