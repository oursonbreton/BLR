package eu.fabienphoto.BusLineReader.IHM;

import java.util.ArrayList;

import eu.fabienphoto.BusLineReader.R;
import eu.fabienphoto.BusLineReader.Controller.DataQuery;
import eu.fabienphoto.BusLineReader.Controller.NetworkController;
import eu.fabienphoto.BusLineReader.IHM.LineElement.LinesAdapter;
import eu.fabienphoto.BusLineReader.Model.Network;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class LinesActivity  extends Activity {

	private ExpandableListView LinesList = null;
	private ArrayList<Network> reseaux ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lines);
		
		LinesList = (ExpandableListView) findViewById(R.id.LinesexpandableView);
		reseaux = new ArrayList<Network>();

		for(Network groupe : NetworkController.getInstance().getNetworks() ) {

			DataQuery dbville = DataQuery.getInstances(groupe.getName()) ;
			groupe.setLines(dbville.GetLines() );
			reseaux.add(groupe);
		}
		
		
		LinesList.setOnChildClickListener(new OnChildClickListener() {    
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {

	             Bundle donnes = new Bundle();
	             donnes.putString("line",  ((TextView) v.findViewById(R.id.LineitemCode)).getText().toString()  );
	             donnes.putString("network", reseaux.get(groupPosition).getName()  );

				Log.d("line activity","ligne n°: " + donnes.getString("line")+" reseau: " + donnes.getString("network") );
				
				Intent lineIntent = new Intent(getApplicationContext(), LineActivity.class);
				lineIntent.putExtras(donnes);
				startActivity(lineIntent);
   
	            return true;
	        }
	    });


		
		LinesAdapter adapter = new LinesAdapter(this, reseaux);
		LinesList.setAdapter(adapter);
		
		
	}
}
