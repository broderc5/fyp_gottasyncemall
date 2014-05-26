package cb.app.fyp.utility;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;


/**
 * Created by Conor on 14/05/2014.
 */
public class Application implements Comparable<Application>{

	private String appName;
	private Drawable appIcon;
	private String appPackage;
	private boolean synced;

	public Application(String appName, Drawable appIcon, String appPackage) {
		this.appName = appName;
		this.appIcon = appIcon;
		this.appPackage = appPackage;
		this.synced = false;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getAppPackage() {
		return appPackage;
	}

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	public boolean isSynced() {
		return synced;
	}

	public void setSynced(boolean synced) {
		this.synced = synced;
	}


	@Override
	public int compareTo(Application application) {
		int name = this.appName.compareTo(application.appName);
		return name == 0 ? this.appName.compareTo(application.appName) : name;
	}
}
