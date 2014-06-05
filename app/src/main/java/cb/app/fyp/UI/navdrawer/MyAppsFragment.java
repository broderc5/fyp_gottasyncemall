package cb.app.fyp.UI.navdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cb.app.fyp.R;

/**
 * Created by Conor on 02/06/2014.
 */
public class MyAppsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_apps, container, false);
		return rootView;
	}
}
