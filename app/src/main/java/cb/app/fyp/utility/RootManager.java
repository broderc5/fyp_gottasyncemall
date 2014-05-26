package cb.app.fyp.utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.acl.Permission;

/**
 * Created by Conor on 19/05/2014.
 */
public class RootManager {

	private static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	private static final String STORAGE_LOCATION = "/Android/data/cb.app.fyp/tmp/";
	private static final String TAG = "ROOT_MANAGER";

	private Context context;
	private Process p;
	private DataInputStream dataInputStream;

	public RootManager(Context context) {
		this.context = context;
		//dataInputStream = new DataInputStream()
	}

	public static void requestRoot(Context context){
		Process p;
		try {
			// Preform su to get root privledges
			p = Runtime.getRuntime().exec("su");

			// Attempt to write a file to a root-only
			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			os.writeBytes("echo \"Do I have root?\" >/system/sd/temporary.txt\n");

			// Close the terminal
			os.writeBytes("exit\n");
			os.flush();
			try {
				p.waitFor();
				if (p.exitValue() != 255) {
					// TODO Code to run on success
					toastMessage(context, "root");
				}
				else {
					// TODO Code to run on unsuccessful
					toastMessage(context, "not root");
				}
			} catch (InterruptedException e) {
				// TODO Code to run in interrupted exception
				toastMessage(context, "not root");
			}
		} catch (IOException e) {
			// TODO Code to run in input/output exception
			toastMessage(context, "not root");
		}
	}

	private static void toastMessage(Context context, String message){
		Toast.makeText(context, message, 0);
	}

	/**
	 * Run a command in a privileged shell
	 * @param command The command to be executed
	 */
	public static void execCommand(String command){
		Process process;
		try {
			process = Runtime.getRuntime().exec("su");

			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			try
			{
				process.waitFor();
				if (process.exitValue() != 255) {
					Log.i(TAG, "Command Successful");
				}
				else {
					Log.i(TAG, "Command failure");
				}
			} catch (InterruptedException e) {
				Log.e(TAG, e.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataInputStream getDataInputStream() {
		return dataInputStream;
	}
}
