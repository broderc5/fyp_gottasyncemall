package cb.app.fyp.UI.models;

import android.support.v7.appcompat.R;

/**
 * Created by Conor on 04/03/14.
 */
public class BasicModel {

	private String name;
	private int icon;

	public BasicModel(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

}
