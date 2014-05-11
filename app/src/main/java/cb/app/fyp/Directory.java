package cb.app.fyp;

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
	private final String STORAGE_LOCATION = "/Android/data/cb.app.fyp/compressed/";
	private final String LOCAL_STORAGE =  "/Download/src";
	private List<String> fileList;
	private String zipFileName;


	public Directory(String path) {
		fileList = new ArrayList<String>();
		zipFileName = path.substring(path.lastIndexOf('/')+1, path.length()) + ".zip";
	}

	private void checkDirExists(String rootDir, String path){
		String[] dirs = path.split("/");
		for(int i = 1; i < dirs.length; i++) {
			rootDir = rootDir + "/" +dirs[i];
			File file = new File(rootDir);
			if (!file.exists()) {
				if (file.mkdir()) {
					Log.i(TAG, "Successfully created " + rootDir);
				} else {
					Log.i(TAG, "Failed to create " + rootDir);
				}
			}
		}
	}

	public void zipFile(String outputPath){

		byte[] buffer = new byte[1024];

		try{
			checkDirExists(SDCARD, STORAGE_LOCATION);
			generateFileList(new File(SDCARD + File.separator + LOCAL_STORAGE));
			FileOutputStream fos = new FileOutputStream(outputPath);
			ZipOutputStream zos = new ZipOutputStream(fos);
			setZipFileName(LOCAL_STORAGE);

			Log.i(TAG, "Output to Zip : " + outputPath);

			for(String file : this.fileList){

				System.out.println("File Added : " + file);
				ZipEntry ze= new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in =
						new FileInputStream(SDCARD + File.separator + LOCAL_STORAGE + "/" + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();

			System.out.println("Done");
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

	public byte[] getZippedFile(String path){
		zipFile(path);
		File file = new File(SDCARD + STORAGE_LOCATION + getZipFileName());
		int size = (int) file.length();
		byte[] bytes = new byte[size];
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
			buf.read(bytes, 0, bytes.length);
			buf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String sourceFolder = SDCARD + "/Download/src";
		return file.substring(sourceFolder.length()+1, file.length());
	}


}