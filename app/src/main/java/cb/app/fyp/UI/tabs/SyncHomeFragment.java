package cb.app.fyp.UI.tabs;

import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterWithCheck;
import cb.app.fyp.demo.adapters.CheckedModel;

public class SyncHomeFragment extends ListFragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */


	/** Called when the activity is first created. */

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_sync_home, container, false);
		ListView listView = (ListView) rootView.findViewById(android.R.id.list);

		ArrayAdapter<CheckedModel> adapter = new ArrayAdapterWithCheck(getActivity(), getModel());
		setListAdapter(adapter);
		return rootView;
	}

	private List<CheckedModel> getModel() {
		List<CheckedModel> list = new ArrayList<CheckedModel>();
		list.add(get("Linux"));
		list.add(get("Windows7"));
		list.add(get("Suse"));
		list.add(get("Eclipse"));
		list.add(get("Ubuntu"));
		list.add(get("Solaris"));
		list.add(get("Android"));
		list.add(get("iPhone"));

		return list;
	}

	private CheckedModel get(String s) {
		return new CheckedModel(s);
	}
}
