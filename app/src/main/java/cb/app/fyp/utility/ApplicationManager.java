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
	Applications apps;
	PackageManager packageManager;

	public ApplicationManager(Context context) {
		appList = new ArrayList<Application>();
		apps = new Applications();
		packageManager = context.getPackageManager();

	}

	public List<Application> getAppList() {
		List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		List<PackageInfo> userInstalledApps = apps.setUserInstalledApps(packages);

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
		apps.setApps(appList);
		return appList;
	}

	public void setApp(List<Application> appList) {
		this.appList = appList;
	}
}
