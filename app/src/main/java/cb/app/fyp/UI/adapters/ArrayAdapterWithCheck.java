package cb.app.fyp.UI.adapters;

/**
 * Created by Conor on 04/03/14.
 */

import android.app.Activity;
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
import cb.app.fyp.demo.adapters.CheckedModel;

public class ArrayAdapterWithCheck extends ArrayAdapter<CheckedModel> {

	private final List<CheckedModel> list;
	private final Activity context;

	public ArrayAdapterWithCheck(Activity context, List<CheckedModel> list) {
		super(context, R.layout.row_layout_with_check, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected ImageView image;
		protected TextView text;
		protected CheckBox checkbox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row_layout_with_check, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) view.findViewById(R.id.icon);
			viewHolder.text = (TextView) view.findViewById(R.id.label);

			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					CheckedModel element = (CheckedModel) viewHolder.checkbox.getTag();
					element.setSelected(buttonView.isChecked());
				}
			});
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			view = convertView;
		//	((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
//		holder.image.setImageResource(list.get(position).getIcon());
		holder.text.setText(list.get(position).getName());
		holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}
}