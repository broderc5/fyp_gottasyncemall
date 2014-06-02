package cb.app.fyp.Drive;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.DriveEvent;

import java.io.IOException;
import java.io.OutputStream;

import cb.app.fyp.utility.Directory;
import cb.app.fyp.MainActivity;
import cb.app.fyp.utility.RootManager;

/**
 * Created by Conor on 20/03/14.
 */
public class DriveActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	private static final String TAG = "Drive_Activity";

	private static final int REQUEST_CODE_GET_FILES = 1;
	private static final int REQUEST_CODE_LAUNCH_MAIN = 2;
	private static final int REQUEST_CODE_RESOLUTION = 3;

	private static final String PATH = "path";

	private static GoogleApiClient googleApiClient;
	private DriveId myAppsDirDriveId;
	private Directory directory;

	ProgressDialog progressBar;


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

	public void uploadFileToDrive(final Directory directory) {
		// Start by creating a new contents, and setting a callback.
		Log.i(TAG, "Creating new contents.");
		Drive.DriveApi.newContents(googleApiClient).setResultCallback(new ResultCallback<DriveApi.ContentsResult>() {

			@Override
			public void onResult(DriveApi.ContentsResult result) {

				if (!result.getStatus().isSuccess()) {
					showMessage("Error while trying to create new file contents");
					return;
				}

				Log.i(TAG, "New contents created.");
				//Upload the actual data
				OutputStream outputStream = result.getContents().getOutputStream();

				try {
					outputStream.write(directory.getZippedFile());

				} catch (IOException e) {
					Log.e(TAG, "Unable to write file contents.");
				} catch (IndexOutOfBoundsException e) {
					Log.e(TAG, e.toString());
				}

				DriveFolder folder = Drive.DriveApi.getFolder(getGoogleApiClient(), myAppsDirDriveId);

				// Create the metadata - MIME type and title.
				MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
						.setMimeType("application/zip")
						.setTitle(directory.getZipFileName())
						.build();

				Log.i(TAG, "Uploading " + directory.getZipFileName() + " ...");
				// create a file on root folder
				folder.createFile(getGoogleApiClient(), metadataChangeSet, result.getContents())
						.setResultCallback(fileCallback);

			}
		});
	}

	final ResultCallback<DriveApi.DriveIdResult> idCallback = new ResultCallback<DriveApi.DriveIdResult>() {
		@Override
		public void onResult(DriveApi.DriveIdResult result) {
			if (!result.getStatus().isSuccess()) {
				showMessage("Cannot find DriveId. Are you authorized to view this file?");
				return;
			}
			myAppsDirDriveId = result.getDriveId();

		}
	};


	final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
			ResultCallback<DriveFolder.DriveFileResult>() {
				@Override
				public void onResult(DriveFolder.DriveFileResult result) {
					if (!result.getStatus().isSuccess()) {
						showMessage("Error while trying to create the file");
						return;
					}
					showMessage("Created a file: " + directory.getZipFileName());
					launchMain();
				}
			};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Called after a Sync Button has been pressed.
		if (resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();

/*
			progressBar.setMessage("Downloading Music :) ");
			progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressBar.setIndeterminate(true);
			progressBar.show();


			final int totalProgressTime = 100;

			final Thread thread = new Thread(){
				@Override
				public void run(){

				}
			};*/
				directory = new Directory(bundle.getString(PATH));
				uploadFileToDrive(directory);

		}
	}

	private void launchMain(){
		//Re-/start the Main Activity so another app can be uploaded
		startActivityForResult(new Intent(this, MainActivity.class),
				REQUEST_CODE_GET_FILES);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "API client connected.");



		MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
				.setTitle("My Apps").build();
		Drive.DriveApi.getRootFolder(getGoogleApiClient()).createFolder(
				getGoogleApiClient(), changeSet).setResultCallback(callback);

		if (directory == null) {
			// This activity has no UI of its own. Just starts the Main.
			launchMain();
			return;
		}
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
		/*The failure has a resolution. Resolve it. Called typically when the app is not yet authorized, and an
		authorization dialog is displayed to the user.*/
		try {
			result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
		} catch (IntentSender.SendIntentException e) {
			Log.e(TAG, "Exception while starting resolution activity", e);
		}
	}



	private final ResultCallback<DriveFolder.DriveFolderResult> callback = new ResultCallback<DriveFolder.DriveFolderResult>() {
		@Override
		public void onResult(DriveFolder.DriveFolderResult result) {
			if (!result.getStatus().isSuccess()) {
				showMessage("Error while trying to create the folder");
				return;
			}
			myAppsDirDriveId = result.getDriveFolder().getDriveId();
			showMessage("Created a folder: " + myAppsDirDriveId);
		}
	};

	final private DriveEvent.Listener<ChangeEvent> changeListener = new DriveEvent.Listener<ChangeEvent>() {
		@Override
		public void onEvent(ChangeEvent event) {
			//DriveFile file = Drive.DriveApi.getFile(googleApiClient, )
			Log.i(TAG, String.format("File change event: %s", event));
		}
	};

	/**
	 * Shows a toast message.
	 */
	public void showMessage(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
}
