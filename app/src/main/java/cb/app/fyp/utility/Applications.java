package cb.app.fyp.utility;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conor on 14/05/2014.
 */
public class Applications {
	List<Application> apps;

	public Applications() {
	}

	public Applications(List<Application> apps) {
		this.apps = apps;
	}

	public List<Application> getApps() {
		return apps;
	}

	public void setApps(List<Application> apps) {
		this.apps = apps;
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
