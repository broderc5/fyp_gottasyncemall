package cb.app.fyp.UI.tabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterNoCheck;
import cb.app.fyp.demo.adapters.BasicModel;

/**
 * Created by Conor on 25/02/14.
 */
public class SendToFragment extends ListFragment {

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_sync_home, container, false);
		ListView listView = (ListView) rootView.findViewById(android.R.id.list);
		// create an array of Strings, that will be put to our ListActivit
		//View footer = getLayoutInflater(icicle).inflate(R.layout.activity_sync_home, null);
		//ListView listView = getListView();
		//listView.addFooterView(footer);
		LayoutInflater inflator = getActivity().getLayoutInflater();
		View view = inflator.inflate(R.layout.activity_send_to, null);
		ArrayAdapter<BasicModel> adapter = new ArrayAdapterNoCheck(getActivity(), getModel());
		setListAdapter(adapter);

		return rootView;
	}

	private List<BasicModel> getModel() {
		List<BasicModel> list = new ArrayList<BasicModel>();
		list.add(get("Linux"));
		list.add(get("Windows7"));
		list.add(get("Suse"));
		list.add(get("Eclipse"));
		list.add(get("Ubuntu"));
		list.add(get("Solaris"));
		list.add(get("Android"));
		list.add(get("iPhone"));
		// Initially select one of the items
		//list.get(4).setSelected(true);
		return list;
	}

	private BasicModel get(String s) {
		return new BasicModel(s);
	}

}
