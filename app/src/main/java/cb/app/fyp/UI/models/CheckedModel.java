package cb.app.fyp.UI.models;

/**
 * Created by Conor on 05/03/14.
 */
public class CheckedModel extends BasicModel {

	private boolean selected;

	public CheckedModel(String name) {
		super(name);
		selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
