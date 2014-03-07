
package cb.app.fyp.UI.adapters;


/**
 * Created by Conor on 04/03/14.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.demo.adapters.BasicModel;

public class ArrayAdapterNoCheck extends ArrayAdapter<BasicModel> {

	private final List<BasicModel> list;
	private final Activity context;

	public ArrayAdapterNoCheck(Activity context, List<BasicModel> list) {
		super(context, R.layout.row_layout_no_check, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected ImageView image;
		protected TextView text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.row_layout_no_check, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) view.findViewById(R.id.icon);
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			view.setTag(viewHolder);

		} else {
			view = convertView;

		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.image.setImageResource(list.get(position).getIcon());
		holder.text.setText(list.get(position).getName());
		return view;
	}
}