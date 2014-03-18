package cb.app.fyp.UI.tabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterWithCheck;
import cb.app.fyp.UI.models.CheckedModel;

public class SyncHomeFragment extends ListFragment {

   	Button button;
	ArrayAdapterWithCheck adapter;

	@Override public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		adapter = new ArrayAdapterWithCheck(getActivity(), getModel());
		setListAdapter(adapter);
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.activity_sync_home, container, false);

		button = (Button) rootView.findViewById(R.id.confirm_button);



		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO for test only remove
				boolean [] items =  adapter.getCheckBoxState();
				for(Boolean b : items){
					String item = Boolean.toString(b);
					Toast.makeText(getActivity().getBaseContext(), item, 0).show();
					/*List<CheckedModel> checkedModels = adapter.getList();
					String item = checkedModels.get(1).getName();
					Toast.makeText(getActivity().getBaseContext(), item, 0).show();*/
				}
			}
		});

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
