package cb.app.fyp.UI.tabs;

import android.app.ListFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterNoCheck;
import cb.app.fyp.UI.models.BasicModel;
import cb.app.fyp.utility.Application;
import cb.app.fyp.utility.ApplicationManager;

/**
 * Created by Conor on 25/02/14.
 */
public class SendToFragment extends ListFragment {

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_sync_home, container, false);
		ListView listView = (ListView) rootView.findViewById(android.R.id.list);
		LayoutInflater inflator = getActivity().getLayoutInflater();
		View view = inflator.inflate(R.layout.fragment_send_to, null);
		ArrayAdapter<BasicModel> adapter = new ArrayAdapterNoCheck(getActivity(), getModel());
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				//TODO Replace with pop up request for NFC
				String item = "Long Press Working";
				Toast.makeText(getActivity().getBaseContext(), item, 0).show();
				return true;
			}
		});

		return rootView;
	}

	private List<BasicModel> getModel() {
		List<BasicModel> list = new ArrayList<BasicModel>();
		ApplicationManager manager = new ApplicationManager(getActivity());
		List<Application> applications = manager.getAppList();

		for (Application app : applications){
			list.add(get(app.getAppName(), app.getAppIcon()));
		}

		return list;
	}


	private BasicModel get(String s, Drawable icon) {
		return new BasicModel(s, icon);
	}

}
