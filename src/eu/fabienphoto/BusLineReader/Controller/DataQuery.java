package eu.fabienphoto.BusLineReader.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.fabienphoto.BusLineReader.Model.Line;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;


/***
 * This class provides objects coming from SQLite Bases.
 * 
 * Object comes from Models and aren't full at start.
 * (automaticaly provided)
 * @author fabien
 *
 */
public class DataQuery {

	private static String SDPATH =  Environment.getExternalStorageDirectory().getPath()+"/BusLineReader/";
	private SQLiteDatabase db;
	
	//Singletons (un par ville)
	private static  HashMap< String , DataQuery> singletons = new HashMap<String, DataQuery>();
	
	private DataQuery(String cityName) {
		//TODO: try catch
		db = SQLiteDatabase.openDatabase(SDPATH + cityName.toLowerCase() + ".db", null, SQLiteDatabase.OPEN_READONLY); //TODO, pas de nettoyage ?
		
	}
	
	/**
	 * Singleton access 
	 * @param cityName
	 * @return the DataQuery of the cityName
	 */
	public static DataQuery getInstances(String cityName)  {
		if( ! singletons.containsKey(cityName) ) {
			singletons.put(cityName, new DataQuery(cityName) );
		}
		return singletons.get(cityName);
	}
	
	
	
	public List<Line> GetLines() {
	
		Cursor c = db.query("ROUTES", new String[]{"code","nom","type","accessible"}, null,null, null, null, "code ASC");
		List<Line> result = new ArrayList<Line>();
		
		c.moveToFirst();
		do {
			Line l = new Line(c.getString(0),
					c.getString(1),
					c.getString(2),
					c.getInt(3) );
			result.add(l);
		} while(c.moveToNext() );
		
		return result;
	}
	
}
