package cb.app.fyp.UI.navdrawer;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cb.app.fyp.R;


/**
 * Created by Conor on 01/04/14.
 */
public class AboutFragment extends Fragment {

	View rootView;
	ActionBar actionBar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.fragment_about, container, false);

		actionBar = getActivity().getActionBar();

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		return rootView;
	}
}
