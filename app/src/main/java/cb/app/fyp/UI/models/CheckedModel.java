package cb.app.fyp.UI.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Conor on 05/03/14.
 */
public class CheckedModel extends BasicModel {

	private boolean selected;
	private boolean checked;

	public CheckedModel(String name, Drawable icon) {
		super(name, icon);
		checked = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
