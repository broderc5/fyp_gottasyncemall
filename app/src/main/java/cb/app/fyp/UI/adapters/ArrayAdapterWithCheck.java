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
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import cb.app.fyp.R;
import cb.app.fyp.UI.models.CheckedModel;

import static android.content.SharedPreferences.Editor;

public class ArrayAdapterWithCheck extends ArrayAdapter<CheckedModel> {

	private final List<CheckedModel> list;
	private final Activity context;
	private boolean [] checkBoxState;
	private ViewHolder viewHolder;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private RadioButton selectedRB;
	private int selectedPosition;

	//private LayoutInflater inflater;
	//Context context;


	public ArrayAdapterWithCheck(Activity context, List<CheckedModel> list) {
		super(context, R.layout.row_layout_with_check, list);
		this.context = context;
		//inflater = LayoutInflater.from(context);
		this.list = list;
		checkBoxState = new boolean[list.size()];
		selectedPosition = -1;
	//	setDefaultSeltion();
	}

	/*private void setDefaultSeltion() {
		CheckedModel checkedModel = list.get(selectedPosition);
		checkedModel.setSelected(true);
		list.set(selectedPosition, checkedModel);
		selectedRB = checkedModel.isSelected();
	}*/

	static class ViewHolder {
		protected ImageView image;
		protected TextView text;
		protected RadioButton radioButton;
		protected CheckBox checkbox;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);

		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.row_layout_with_check, null);
			viewHolder = new ViewHolder();
			editor = sharedPreferences.edit();

			setViewHolderResources(convertView);

			convertView.setTag(viewHolder);
			viewHolder.radioButton.setTag(list.get(position));
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		//if (currentlySelected == )

		viewHolder.image.setImageDrawable(list.get(position).getIcon());
		viewHolder.text.setText(list.get(position).getName());

		if (!sharedPreferences.contains("CheckValue" + position)) {
			editor.putBoolean("CheckValue" + position, false);
			editor.commit();
		}

		viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(position != selectedPosition && selectedRB != null){
					selectedRB.setChecked(false);
				}

				selectedPosition = position;
				selectedRB = (RadioButton) view;
			}
		});

		if(selectedPosition != position){
			viewHolder.radioButton.setChecked(false);
		}
		else {
			viewHolder.radioButton.setChecked(true);
			if(selectedRB != null && viewHolder.radioButton != selectedRB){
				selectedRB = viewHolder.radioButton;
			}
		}

		viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				editor.putBoolean("CheckValue" + position, isChecked);
				editor.commit();
				CheckedModel element = (CheckedModel) viewHolder.checkbox.getTag();
				element.setChecked(buttonView.isChecked());
				checkBoxState[position] = isChecked;
			}
		});

		viewHolder.radioButton.setChecked(list.get(position).isSelected());
		viewHolder.checkbox.setChecked(sharedPreferences.getBoolean("CheckValue" + position, false));

		//Toast.makeText(getContext(), item, 0).show();
		return convertView;
	}

	/**
	 * Connect each item in the view holder with its resource in this Adapters accompanying XML file
	 * @param view
	 */
	private void setViewHolderResources(View view) {

		viewHolder.image = (ImageView) view.findViewById(R.id.icon);
		viewHolder.text = (TextView) view.findViewById(R.id.label);
		viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio);
		viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
	}

	public List<CheckedModel> getList() {
		return list;
	}

	public RadioButton getSelectedRB() {
		return selectedRB;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public boolean[] getCheckBoxState() {
		return checkBoxState;
	}


	public void setCheckBoxState(boolean[] checkBoxState) {
		this.checkBoxState = checkBoxState;
		for(int i = 0; i<checkBoxState.length; i++){
			if (checkBoxState[i] != sharedPreferences.getBoolean("CheckValue" + i, false)) {
				editor.putBoolean("CheckValue" + i, checkBoxState[i]);
				editor.apply();
			}
		}
	}


}