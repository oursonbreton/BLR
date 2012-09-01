package eu.fabienphoto.BusLineReader.IHM;

import eu.fabienphoto.BusLineReader.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

abstract class BLRActivity extends Activity {

	public static final String ACTION_LOGOUT = "eu.fabienphoto.BusLineReader.LOGOUT";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
	}

	
	/**
	 * Generic Menu, see on each activity. (pref, maps, favories, lines list, ...)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu); 
		return true;
	}
	
	/**
	 * Generic Menu, provide quick access for activity pref, maps, favorites, lines list 
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch( item.getItemId() ) {
		case R.id.Mfavorites:
		case R.id.Mpref:
			Intent prefintent = new Intent(getApplicationContext(), Preferences.class);
			startActivity(prefintent);
			
		case R.id.Mquit:
			logout();

			
		case R.id.MLines:
			Intent LinesIntent = new Intent(getApplicationContext(), LinesActivity.class);
			startActivity(LinesIntent);
		
		}
		
		return true;
	}
	
	
	//usefull ?
	protected void logout() {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ACTION_LOGOUT);
		sendBroadcast(broadcastIntent);
	}
}
