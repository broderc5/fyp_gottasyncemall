package cb.app.fyp.utility;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conor on 19/05/2014.
 */
public class DirectoryManager {
	private final String LOCAL_STORAGE =  "/data/data/";
	List<Directory> directories;

	public DirectoryManager() {
		directories = new ArrayList<Directory>();
	}

	public List<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<Application> applications) {
		for (Application app : applications){
			String path = LOCAL_STORAGE + app.getAppPackage();
			if (new File(path).isDirectory())
				directories.add(new Directory(path));
		}
	}
}

