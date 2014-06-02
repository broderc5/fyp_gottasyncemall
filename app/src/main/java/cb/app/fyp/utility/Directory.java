package cb.app.fyp.utility;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Conor on 14/04/2014.
 *
 * All work done on device local storage
 */
public class Directory
{
	private final String TAG = "Directory";
	private final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	private final String COMPRESSED_STORAGE = "/Android/data/cb.app.fyp/compressed/";
	private final String TMP_STORAGE =  "/Android/data/cb.app.fyp/tmp/";

	private String path;
	private String zipFileName;
	private List<String> fileList;

	public Directory(String path) {
		if (isDirectory(path)) {
			this.path = path;
		}
		fileList = new ArrayList<String>();
		zipFileName = path.substring(path.lastIndexOf('/')+1, path.length()) + ".zip";
		checkDirExists(SDCARD, TMP_STORAGE + path.substring(path.lastIndexOf(File.separator)+1));

	}

	public boolean isDirectory(String path){
		return new File(path).isDirectory();
	}

	private void checkDirExists(String rootDir, String path){
		String[] dirs = path.split("/");
		for(int i = 1; i < dirs.length; i++) {
			rootDir = rootDir + "/" +dirs[i];
			File file = new File(rootDir);
			if (!file.exists()) {
				if (file.mkdir()) {
					return;
				} else {
					Log.i(TAG, "Failed to create " + rootDir);
				}
			}
		}
	}

	public void zipFile(){

		byte[] buffer = new byte[1024];

		try{
			checkDirExists(SDCARD, COMPRESSED_STORAGE);
			generateFileList(new File(SDCARD + File.separator + TMP_STORAGE + path.substring(path.lastIndexOf(File.separator)+1)));
			setZipFileName(path);
			FileOutputStream fos = new FileOutputStream(SDCARD + COMPRESSED_STORAGE + getZipFileName());
			ZipOutputStream zos = new ZipOutputStream(fos);

			Log.i(TAG, "Outputting to Zip : " + getZipFileName().substring(0, getZipFileName().lastIndexOf(".")));

			for(String file : this.fileList){

				ZipEntry ze= new ZipEntry(file);
				zos.putNextEntry(ze);
				String path2 = SDCARD + TMP_STORAGE +file;
				FileInputStream in = new FileInputStream(path2);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
//remember close it
			zos.close();

			Log.i(TAG, "Done");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String path) {
		this.zipFileName = path.substring(path.lastIndexOf('/')+1, path.length()) + ".zip";
	}

	public byte[] getZippedFile(){
		zipFile();
		File file = new File(SDCARD + COMPRESSED_STORAGE + getZipFileName());
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.toString());
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		} catch (OutOfMemoryError e) {
			Log.e(TAG, e.toString());
		}

		return bytes;
	}

	/**
	 * Traverse a directory and get all files,
	 * and add the file into fileList
	 * @param node file or directory
	 */
	public void generateFileList(File node){

//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}

	}

	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String file){
		String sourceFolder = SDCARD + TMP_STORAGE;
		return file.substring(sourceFolder.length(), file.length());
	}


	public String getPath() {
		return path;
	}
}