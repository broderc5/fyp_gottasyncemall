package cb.app.fyp.demo.adapters;

/**
 * Created by Conor on 04/03/14.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import cb.app.fyp.UI.adapters.ArrayAdapterWithCheck;

public class MyList extends ListActivity {


	/** Called when the activity is first created. */

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// create an array of Strings, that will be put to our ListActivity
		ArrayAdapter<CheckedModel> adapter = new ArrayAdapterWithCheck(this, getModel());
		setListAdapter(adapter);
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
		// Initially select one of the items
		//list.get(4).setSelected(true);
		return list;
	}

	private CheckedModel get(String s) {
		return new CheckedModel(s);
	}


}
