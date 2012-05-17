package eu.fabienphoto.BusLineReader;

import eu.fabienphoto.BusLineReader.IHM.LineView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BusLineReaderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button bt = (Button) findViewById(R.id.buttoncarte);
        
        
		
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LineView.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				BusLineReaderActivity.this.startActivity(intent);
			}
			
		});
    	
    }
}