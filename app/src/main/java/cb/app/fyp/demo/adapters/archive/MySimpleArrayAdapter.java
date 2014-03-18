package cb.app.fyp.demo.adapters.archive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cb.app.fyp.R;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
	private ViewHolder viewHolder;
	private boolean[] checkBoxState;

	public MySimpleArrayAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
		checkBoxState = new boolean[values.size()];
	}

	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.activity_sync_home, parent, false);
		TextView textView2 = (TextView) rowView.findViewById(R.id.label);
		CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.checkbox);
		textView2.setText(values[position]);
		checkbox.setText("");

		return rowView;
	}*/

	private class ViewHolder
	{
		TextView name;
		CheckBox checkBox;
	}



	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView==null)
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.activity_sync_home, parent, false);
			viewHolder=new ViewHolder();

			//cache the views
			viewHolder.name=(TextView) convertView.findViewById(R.id.label);
			viewHolder.checkBox=(CheckBox) convertView.findViewById(R.id.checkBox);

			//link the cached views to the convertview
			convertView.setTag( viewHolder);


		}
		else
			viewHolder=(ViewHolder) convertView.getTag();

		//set the data to be displayed
//		viewHolder.name.setText(values.get(position));

		//VITAL PART!!! Set the state of the
		//CheckBox using the boolean array
		viewHolder.checkBox.setChecked(checkBoxState[position]);


		//for managing the state of the boolean
		//array according to the state of the
		//CheckBox

		viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if(((CheckBox)v).isChecked())
					checkBoxState[position]=true;
				else
					checkBoxState[position]=false;

			}
		});

		//return the view to be displayed
		return convertView;
	}

}