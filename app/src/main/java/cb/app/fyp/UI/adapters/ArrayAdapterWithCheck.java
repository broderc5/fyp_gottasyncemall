package cb.app.fyp.UI.adapters;

/**
 * Created by Conor on 04/03/14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.models.CheckedModel;

import static android.content.SharedPreferences.*;

public class ArrayAdapterWithCheck extends ArrayAdapter<CheckedModel> {

	private final List<CheckedModel> list;
	private final Activity context;
	private boolean [] checkBoxState;
	private ViewHolder viewHolder;
	Editor editor;

	//private LayoutInflater inflater;
	//Context context;


	public ArrayAdapterWithCheck(Activity context, List<CheckedModel> list) {
		super(context, R.layout.row_layout_with_check, list);
		this.context = context;
		//inflater = LayoutInflater.from(context);
		this.list = list;
		checkBoxState = new boolean[list.size()];
	}

	static class ViewHolder {
		protected ImageView image;
		protected TextView text;
		protected CheckBox checkbox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.row_layout_with_check, null);
			viewHolder = new ViewHolder();
			editor = sharedPreferences.edit();
			viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
			viewHolder.text = (TextView) convertView.findViewById(R.id.label);

			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check);
			convertView.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.image.setImageDrawable(list.get(position).getIcon());
		viewHolder.text.setText(list.get(position).getName());
		viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editor.putBoolean("CheckValue" + position, isChecked);
				editor.commit();
				CheckedModel element = (CheckedModel) viewHolder.checkbox.getTag();
				element.setSelected(buttonView.isChecked());
				checkBoxState[position] = isChecked;
			}
		});

		viewHolder.checkbox.setChecked(sharedPreferences.getBoolean("CheckValue" + position, false));

		//Toast.makeText(getContext(), item, 0).show();
		return convertView;
	}

	public List<CheckedModel> getList() {
		return list;
	}

	public boolean[] getCheckBoxState() {
		return checkBoxState;
	}


	public void setCheckBoxState(boolean[] checkBoxState) {
		this.checkBoxState = checkBoxState;
		/*viewHolder = getView()
		for(int i = 0; i<checkBoxState.length; i++){
			viewHolder.checkbox.setChecked(checkBoxState[i]);
		}*/
	}


}