package cb.app.fyp;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

public class DriveSingleton implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	//1 of 3
	private static GoogleApiClient googleApiClient;
	private Context context;

	//Extra stuff...
	private static int counter=0;

	//2 of 3
	private DriveSingleton()
	{
		counter++;
	}

	//3 of 3
	public  synchronized GoogleApiClient getInstance(Context context)
	{
		if (googleApiClient != null) return googleApiClient;
			// Create the API client and bind it to an instance variable.
			// We use this instance as the callback for connection and connection
			// failures.
			// Since no account name is passed, the user is prompted to choose.
		googleApiClient = new GoogleApiClient.Builder(context)
				.addApi(Drive.API)
				.addScope(Drive.SCOPE_FILE)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.build();

		return googleApiClient;
	}

	public static int getCounter()
	{
		return counter;
	}

	@Override
	public void onConnected(Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}
}