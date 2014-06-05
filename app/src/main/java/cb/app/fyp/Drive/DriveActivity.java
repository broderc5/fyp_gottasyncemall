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
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.DriveEvent;

import java.io.IOException;
import java.io.OutputStream;

import cb.app.fyp.MainActivity;
import cb.app.fyp.utility.Directory;
import cb.app.fyp.utility.RootManager;

/**
 * Created by Conor on 20/03/14.
 */
public class DriveActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	private static final String TAG = "Drive_Activity";

	private static final int REQUEST_CODE_GET_FILES = 1;
	private static final int REQUEST_CODE_RESOLUTION = 2;

	private static final String PATH = "path";

	private static GoogleApiClient googleApiClient;
	private DriveId myAppsDirDriveId;
	private Directory directory;

	@Override
	protected void onResume() {
		super.onResume();
		if (googleApiClient == null) {
			/*Create the API client and bind it to an instance variable. We use this
			instance as the callback for connection and connection failures. Since no
			account name is passed, the user is prompted to choose.*/
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


	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i(TAG, "API client connected.");

		MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
				.setTitle("My Apps").build();
		Drive.DriveApi.getRootFolder(googleApiClient).createFolder(
				googleApiClient, changeSet).setResultCallback(callback);

		if (directory == null) {
			// This activity has no UI of its own. Just starts the Main.
			launchMain();
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


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Called after a Sync Button has been pressed.
		if (resultCode == Activity.RESULT_OK && data.hasExtra(PATH)) {
			Bundle bundle = data.getExtras();
			directory = new Directory(bundle.getString(PATH));
			uploadFileToDrive(directory);
		}
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

				DriveFolder folder = Drive.DriveApi.getFolder(googleApiClient, myAppsDirDriveId);

				// Create the metadata - MIME type and title.
				MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
						.setMimeType("application/zip")
						.setTitle(directory.getZipFileName())
						.build();

				Log.i(TAG, "Uploading " + directory.getZipFileName() + " ...");
				// create a file on root folder
				folder.createFile(googleApiClient, metadataChangeSet, result.getContents())
						.setResultCallback(fileCallback);

			}
		});
	}

	private void launchMain(){
		//Re-/start the Main Activity so another app can be uploaded
		startActivityForResult(new Intent(this, MainActivity.class),
				REQUEST_CODE_GET_FILES);
	}

	/**
	 * Shows a toast message.
	 */
	public void showMessage(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}

	private final ResultCallback<DriveFolder.DriveFolderResult> callback = new ResultCallback<DriveFolder.DriveFolderResult>() {
		@Override
		public void onResult(DriveFolder.DriveFolderResult result) {
			if (!result.getStatus().isSuccess()) {
				showMessage("Error while trying to create the folder");
				return;
			}
			myAppsDirDriveId = result.getDriveFolder().getDriveId();
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

	final private DriveEvent.Listener<ChangeEvent> changeListener = new DriveEvent.Listener<ChangeEvent>() {
		@Override
		public void onEvent(ChangeEvent event) {
			//DriveFile file = Drive.DriveApi.getFile(googleApiClient, )
			Log.i(TAG, String.format("File change event: %s", event));
		}
	};
}
