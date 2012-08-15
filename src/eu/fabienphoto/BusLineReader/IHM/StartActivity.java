package eu.fabienphoto.BusLineReader.IHM;

import eu.fabienphoto.BusLineReader.R;
import eu.fabienphoto.BusLineReader.Controller.DataQuery;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Statrt activity is the main class. For now it's a favory screen.
 * @author fabien
 *
 */
public class StartActivity extends BLRActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.main);
		 
		 
	     ListView favorylist = (ListView) findViewById(R.id.FavoryLv);
	     
	     DataQuery db =  DataQuery.getInstances("rennes");
	     
	     Favadapter favadapter = new Favadapter(getApplicationContext(), db.GetLines() );
	     favorylist.setAdapter(favadapter);
	     
	     setVisible(true);
	        
	}

}
