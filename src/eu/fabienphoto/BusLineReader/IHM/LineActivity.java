package eu.fabienphoto.BusLineReader.IHM;

import eu.fabienphoto.BusLineReader.R;
import eu.fabienphoto.BusLineReader.IHM.LineElement.LineAdapter;
import eu.fabienphoto.BusLineReader.IHM.LineElement.LinesAdapter;
import android.os.Bundle;
import android.widget.ListView;

public class LineActivity extends BLRActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.lineview);
		
		//TODO : recuprer de http://www.tutomobile.fr/intent-passer-dune-activity-a-une-autre-tutoriel-android-n%C2%B011/16/07/2010/

 
    	String line ="";
    	String network="";
        Bundle objetbunble  = this.getIntent().getExtras();
 
        if (objetbunble != null && objetbunble.containsKey("line") && objetbunble.containsKey("network")) {
        	line = this.getIntent().getStringExtra("line");
              network = this.getIntent().getStringExtra("network");
        }
        
      
        ListView linelist = (ListView) findViewById(R.id.LineListView);
        LineAdapter  ladapt = new LinesAdapter(getApplicationContext(), arretliste);
        linelist.setAdapter(ladapt);
      
	}

}
