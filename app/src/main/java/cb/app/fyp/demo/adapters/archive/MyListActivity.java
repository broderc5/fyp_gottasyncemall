package cb.app.fyp.demo.adapters.archive;
import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MyListActivity extends ListActivity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		/*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };*/
		ArrayList<String> values = new ArrayList<String>();
		values.add("Android");
		values.add("iPhone");
		values.add("WindowsMobile");
		values.add("Blackberry");

		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getBaseContext(), values);
		setListAdapter(adapter);
	}

}