package cb.app.fyp.UI.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Conor on 04/03/14.
 */
public class BasicModel {

	private String name;
	private Drawable icon;

	public BasicModel(String name, Drawable icon){
		this.name = name;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

}
