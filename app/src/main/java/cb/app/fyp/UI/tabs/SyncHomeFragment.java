package cb.app.fyp.UI.tabs;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.Directory;
import cb.app.fyp.Drive.DriveActivity;
import cb.app.fyp.MainActivity;
import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterWithCheck;
import cb.app.fyp.UI.models.CheckedModel;

public class SyncHomeFragment extends ListFragment {

	private final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	private final String STORAGE_LOCATION = "/Android/data/cb.app.fyp/compressed/";
	private final String LOCAL_STORAGE =  "/Download/src";

   	private Button button;
	private ArrayAdapterWithCheck adapter;
	private Directory directory;

	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_sync_home, container, false);
		adapter = new ArrayAdapterWithCheck(getActivity(), getModel());
		setListAdapter(adapter);
		button = (Button) rootView.findViewById(R.id.confirm_button);

		button.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View view) {
			//TODO for test only remove
			/*boolean [] items =  adapter.getCheckBoxState();
			for(Boolean b : items){
				String item = Boolean.toString(b);
				Toast.makeText(getActivity().getBaseContext(), item, 0).show();
				*/
				boolean[] checkBoxState = adapter.getCheckBoxState();
				List<byte[]> appData = new ArrayList<byte[]>();
				directory = new Directory(SDCARD + LOCAL_STORAGE);
				for (Boolean b : checkBoxState){
					if (b) {
						//byte[] zippedFile = directory.getZippedFile(SDCARD + STORAGE_LOCATION + directory.getZipFileName());
						appData.add(directory.getZippedFile(SDCARD + STORAGE_LOCATION + directory.getZipFileName()));
					}
				}
				Activity activity = getActivity();
				if(activity instanceof MainActivity) {
					((MainActivity) activity).setResult(appData);
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
