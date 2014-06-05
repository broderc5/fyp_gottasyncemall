package cb.app.fyp.utility;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Conor on 14/05/2014.
 */
public class ApplicationManager {
	private static final String TAG = "Application_Manager" ;
	List<Application> appList;
	PackageManager packageManager;

	public ApplicationManager(Context context) {
		appList = new ArrayList<Application>();
		packageManager = context.getPackageManager();
	}

	public List<Application> getAppList() {
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		List<PackageInfo> userInstalledApps = setUserInstalledApps(packages);

		String appName;
		Drawable appIcon;
		String appPackage;

		ApplicationInfo applicationInfo;
		for (PackageInfo packageName : userInstalledApps) {
			try {
				applicationInfo = packageManager.getApplicationInfo(packageName.packageName, 0);
				appName = (String)packageManager.getApplicationLabel(applicationInfo);
				appIcon = packageManager.getApplicationIcon(applicationInfo);
				appPackage = packageName.packageName;
				appList.add(new Application(appName, appIcon, appPackage));
			} catch (final PackageManager.NameNotFoundException e) {
				Log.e(TAG, e.toString());
			}
		}

		Collections.sort(appList);
		return appList;
	}


	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}

	/**
	 * Takes a list of apps on a device and filters it down to only the user
	 * installed apps.
	 * @param packageList A List of packages
	 * @return A list of user installed apps
	 */
	public List<PackageInfo> setUserInstalledApps(List<PackageInfo> packageList) {
		List<PackageInfo> packages = new ArrayList<PackageInfo>();
		for(PackageInfo packageName : packageList) {
			if(!isSystemPackage(packageName)) {
				packages.add(packageName);
			}
		}
		return packages;
	}

	private boolean isSystemPackage(PackageInfo pkgInfo) {
		return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
	}
}
