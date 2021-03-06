package cb.app.fyp.UI.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import cb.app.fyp.UI.tabs.NullFragment;
import cb.app.fyp.UI.tabs.SendToFragment;
import cb.app.fyp.UI.tabs.SyncHomeFragment;

/**
 * Created by Conor on 25/02/14.
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override public Fragment getItem(int i) {
			switch (i) {
				case 0:
					return new SyncHomeFragment();
				case 1:
					return new SendToFragment();

			}
			return new NullFragment();
		}

		@Override
		public int getCount() {
			return 2;
		}

}
