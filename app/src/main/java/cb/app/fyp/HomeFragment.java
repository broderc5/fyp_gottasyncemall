package cb.app.fyp;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.AppSectionsPagerAdapter;

public class HomeFragment extends Fragment implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	AppSectionsPagerAdapter appSectionsPagerAdapter;
	ViewPager viewPager;
	View rootView;
	ActionBar actionBar;
	public HomeFragment(){
		//Empty constructor

	}
	/**
	 * The {@link ViewPager} that will display the three primary sections of the app, one at a
	 * time.
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = null;
		/*if(savedInstanceState == null){*/
		rootView = inflater.inflate(R.layout.activity_home, container, false);
		appSectionsPagerAdapter = new AppSectionsPagerAdapter(getChildFragmentManager());
		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.

		// Set up the action bar.
		actionBar = getActivity().getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is no hierarchical
		// parent.
		//actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);
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
		return rootView;
	}

	/*@Override
	public void onPause(){
		super.onPause();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().add(this, "HomeFragment").commit();
	}*/

	@Override
	public void onDestroy() {
		//actionBar = getActivity().getActionBar();
		actionBar.removeAllTabs();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroy();
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

}
