package cb.app.fyp.utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Conor on 19/05/2014.
 */
public class RootManager {

	private static final String TAG = "ROOT_MANAGER";

	private static boolean hasRoot;
	private Context context;

	public RootManager(Context context) {
		this.context = context;
	}

	public static boolean requestRoot(Context context){
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
					hasRoot = true;
					showMessage(context, "root");
				}
				else {
					hasRoot = false;
					showMessage(context, "not root");
				}
			} catch (InterruptedException e) {
				showMessage(context, "not root");
			}
		} catch (IOException e) {
			showMessage(context, "not root");
		}
		return hasRoot;
	}

	private static void showMessage(Context context, String message){
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
}
