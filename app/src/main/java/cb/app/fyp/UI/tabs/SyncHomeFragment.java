package cb.app.fyp.UI.tabs;

import android.app.Activity;
import android.app.ListFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cb.app.fyp.MainActivity;
import cb.app.fyp.R;
import cb.app.fyp.UI.adapters.ArrayAdapterWithCheck;
import cb.app.fyp.UI.models.CheckedModel;
import cb.app.fyp.utility.Application;
import cb.app.fyp.utility.ApplicationManager;
import cb.app.fyp.utility.Directory;
import cb.app.fyp.utility.DirectoryManager;
import cb.app.fyp.utility.RootManager;

public class SyncHomeFragment extends ListFragment {

	private final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	private final String STORAGE_LOCATION = "/Android/data/cb.app.fyp/compressed/";
	private final String LOCAL_STORAGE =  "/data/data/";

   	private Button button;
	private ArrayAdapterWithCheck adapter;
	private Directory directory;
	private ApplicationManager manager;
	private List<Application> applications;

	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_sync_home, container, false);
		manager = new ApplicationManager(getActivity());
		applications = manager.getAppList();
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
				List<String> paths = new ArrayList<String>();
				ApplicationManager applicationManager = new ApplicationManager(getActivity());

				List<Application> applications = applicationManager.getAppList();
				DirectoryManager directoryManager = new DirectoryManager();
				directoryManager.setDirectories(applications);
				String command;
				String path;
				for (int i = 0; i < checkBoxState.length; i++){
					if (checkBoxState[i]) {
						directory = directoryManager.getDirectories().get(i);
						//byte[] zippedFile = directory.getZippedFile(SDCARD + STORAGE_LOCATION + directory.getZipFileName());
						path = directory.getPath();
						command = "cp -r " + path + " /sdcard/Android/data/cb.app.fyp/tmp" + path.substring(path.lastIndexOf(File.separator));
						RootManager.execCommand(command);
						appData.add(directory.getZippedFile());
						paths.add(path);
						Toast.makeText(getActivity(), path, 0).show();
					}
				}
				Activity activity = getActivity();
				if(activity instanceof MainActivity) {
					((MainActivity) activity).setResult(paths, appData);
				}
			}
		});

		return rootView;
	}

	private List<CheckedModel> getModel() {
		List<CheckedModel> list = new ArrayList<CheckedModel>();
		ApplicationManager manager = new ApplicationManager(getActivity());
		List<Application> applications = manager.getAppList();

		for (Application app : applications){
			list.add(get(app.getAppName(), app.getAppIcon()));
		}

		return list;
	}

	private CheckedModel get(String s, Drawable icon) {
		return new CheckedModel(s, icon);
	}

}
