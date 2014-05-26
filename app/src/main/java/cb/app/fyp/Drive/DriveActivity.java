package cb.app.fyp.Drive;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

import cb.app.fyp.utility.Directory;
import cb.app.fyp.MainActivity;
import cb.app.fyp.utility.RootManager;

/**
 * Created by Conor on 20/03/14.
 */
public class DriveActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	private static final String TAG = "Drive_Activity";

	private static final int REQUEST_CODE_LAUNCH_MAIN = 1;
	private static final int REQUEST_CODE_CREATOR = 2;
	private static final int REQUEST_CODE_RESOLUTION = 3;

	private final String LOCAL_STORAGE =  "/data/data/";

	private static GoogleApiClient googleApiClient;
	//TODO Check data type
	private byte[] file;
	private Directory directory;

	@Override
	protected void onResume() {
		super.onResume();
		//startService(new Intent(this, DownloadFromDriveService.class));
		if (googleApiClient == null) {
			// Create the API client and bind it to an instance variable.
			// We use this instance as the callback for connection and connection
			// failures.
			// Since no account name is passed, the user is prompted to choose.
			googleApiClient = new GoogleApiClient.Builder(this)
					.addApi(Drive.API)
					.addScope(Drive.SCOPE_FILE)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.build();
		}
		// Connect the client. Once connected, the app's UI is launched.
		googleApiClient.connect();
		RootManager.requestRoot(this);
	}

	public static GoogleApiClient getGoogleApiClient(){
		return googleApiClient;
	}

	public void uploadFileToDrive() {
		// Start by creating a ne
		//w contents, and setting a callback.
		Log.i(TAG, "Creating new contents.");
		Drive.DriveApi.newContents(googleApiClient).setResultCallback(new ResultCallback<DriveApi.ContentsResult>() {

			@Override
			public void onResult(DriveApi.ContentsResult result) {

				if (!result.getStatus().isSuccess()) {
					Log.i(TAG, "Failed to create new contents.");
					return;
				}

				Log.i(TAG, "New contents created.");
				OutputStream outputStream = result.getContents().getOutputStream();

				try {
					outputStream.write(file);
				} catch (IOException e1) {
					Log.i(TAG, "Unable to write file contents.");
				}

				// Create the initial metadata - MIME type and title.
				// Note that the user will be able to change the title later.
				MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
						.setMimeType("application/zip")
						.setTitle(directory.getZipFileName())
						.build();

				// Create an intent for the file chooser, and start it.
				IntentSender intentSender = Drive.DriveApi
		 				.newCreateFileActivityBuilder()
						.setInitialMetadata(metadataChangeSet)
						.setInitialContents(result.getContents())
						.build(googleApiClient);
				try {
					startIntentSenderForResult(
							intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
				} catch (IntentSender.SendIntentException e) {
					Log.i(TAG, "Failed to launch file chooser.");
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CODE_LAUNCH_MAIN:
				// Called after a photo has been taken.
				if (resultCode == Activity.RESULT_OK) {
					// Store the image data as a bitmap for writing later.
					Bundle bundle = data.getExtras();
					if (bundle != null){
						Set<String> keys = bundle.keySet();
						Iterator<String> it = keys.iterator();
						while (it.hasNext()) {
							String key = it.next();
							file = (byte[]) data.getExtras().get(key);
							directory = new Directory(data.getExtras().getString(it.next()));
							Log.i(TAG, key + " extracted");
							uploadFileToDrive();
							Log.i(TAG, key + " uploaded");
						}
					}
				}
				Toast.makeText(this, "Finished Uploading", 0).show();
				break;
			case REQUEST_CODE_CREATOR:
				// Called after a file is saved to Drive.
				if (resultCode == RESULT_OK) {
					Log.i(TAG, "File successfully saved.");
					Toast.makeText(this, "Starting new process", 0).show();
					file = null;
					// Just start the camera again for another photo.
					startActivityForResult(new Intent(this, MainActivity.class),
							REQUEST_CODE_LAUNCH_MAIN);
				}
				break;
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "API client connected.");
		if (file == null) {
			// This activity has no UI of its own. Just starts the M.
			startActivityForResult(new Intent(this, MainActivity.class),
					REQUEST_CODE_LAUNCH_MAIN);
			return;
		}
		//uploadFileToDrive();
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.i(TAG, "GoogleApiClient connection suspended");
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// Called whenever the API client fails to connect.
		Log.i(TAG, "GoogleApiClient connection failed: " + result.toString());
		if (!result.hasResolution()) {
			// show the localized error dialog.
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}
		// The failure has a resolution. Resolve it.
		// Called typically when the app is not yet authorized, and an
		// authorization
		// dialog is displayed to the user.
		try {
			result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
		} catch (IntentSender.SendIntentException e) {
			Log.e(TAG, "Exception while starting resolution activity", e);
		}
	}
}
