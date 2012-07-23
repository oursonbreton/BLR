package eu.fabienphoto.BusLineReader.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipFile;

import eu.fabienphoto.BusLineReader.Util;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class read a folder contains a network at GTFS format.
 * For more informations about format view : https://developers.google.com/transit/gtfs/
 * Its returns a Bus network.
 * @author fabien
 *
 */
public class GTFSReader {
	
	private final static String agencypath = "agency.txt";	//One or more transit agencies that provide the data in this feed.
	private final static String stopsPath = "stops.txt";	//Individual locations where vehicles pick up or drop off passengers.
	private final static String routesPath = "routes.txt";	//Transit routes. A route is a group of trips that are displayed to riders as a single service.
	private final static String tripsPath = "trips.txt";	//Trips for each route. A trip is a sequence of two or more stops that occurs at specific time.
	private final static String stopTimesPath = "stop_times.txt";//	Times that a vehicle arrives at and departs from individual stops for each trip.
	private final static String calendarPath = "calendar.txt";//Dates for service IDs using a weekly schedule. Specify when service starts and ends, as well as days of the week where service is available.
	private String pathGTFS;
	private String dbPath;

	/*calendar_dates.txt	Optional	Exceptions for the service IDs defined in the calendar.txt file. If calendar_dates.txt includes ALL dates of service, this file may be specified instead of calendar.txt.
	fare_attributes.txt	Optional	Fare information for a transit organization's routes.
	fare_rules.txt	Optional	Rules for applying fare information for a transit organization's routes.
	shapes.txt	Optional	Rules for drawing lines on a map to represent a transit organization's routes.
	frequencies.txt	Optional	Headway (time between trips) for routes with variable frequency of service.
	transfers.txt	Optional	Rules for making connections at transfer points between routes.
	feed_info.txt	Optional	Additional information about the feed itself, including publisher, version, and expiration information.
	*/
	
	/**
	 * do the parsing at the building of classes
	 * 
	 * throw an error if not required file are present
	 * @param GTFSpath
	 */
	GTFSReader(String GTFSpath, String DbPath) {
		
		this.pathGTFS = GTFSpath;
		this.dbPath = DbPath;

		
	}
	
	
	
	/**
	 * the main fonction
	 * The GTFS file is supposed monoagency
	 */
	void convert2Db() {
		
		//create database
		File dbFile = new File(dbPath);
		dbFile.delete();
		SQLiteDatabase db;
		try{
			db = SQLiteDatabase.openOrCreateDatabase(dbPath,null);
		}catch(SQLException e) {
			Log.e(Util.Appname, "error during creation of db : "+e.getMessage());
			return;
		}

		//create structure
		db.beginTransaction();
		try {
			db.execSQL("CREATE TABLE ROUTES (_id INTEGER NOT NULL PRIMARY KEY, code TEXT, nom TEXT, type TEXT, accessible INTEGER)");
			db.execSQL("CREATE TABLE STOPS (_id INTEGER NOT NULL PRIMARY KEY, code TEXT, nom TEXT, commune TEXT, latitude DOUBLE, longitude DOUBLE, accessible INTEGER, banc INTEGER, eclairage INTEGER, couvert INTEGER)");
			db.execSQL("CREATE TABLE CALENDARS (_id INTEGER NOT NULL PRIMARY KEY, days INTEGER, start_date TEXT, stop_date TEXT)");
			db.execSQL("CREATE TABLE STOPTIMES (_id INTEGER NOT NULL PRIMARY KEY, idtrips INTEGER, idstop INTEGER, arrive INTEGER, start INTEGER, sequence INTEGER)");
			db.execSQL("CREATE TABLE TRIPS (_id INTEGER NOT NULL PRIMARY KEY, idROutes INTEGER, idcalendars INTEGER)");
	
			
			db.setTransactionSuccessful();
		}catch(SQLException e) {
			Log.e(Util.Appname, "error during creation table : "+e.getMessage());
			return;
		} finally {
			db.endTransaction();
		}
	
		
		//unzip GTFS
		try {
	      ZipFile zip = new ZipFile(pathGTFS);
	      

	      //TODO : readagencies(zip.getInputStream(zip.getEntry(agencypath)), db );
	      
	      readRoutes(zip.getInputStream(zip.getEntry(routesPath)), db );
	      
	      readStops(zip.getInputStream(zip.getEntry(stopsPath)), db );
	      
	      readcalendarPath(zip.getInputStream(zip.getEntry(calendarPath)), db );
	      
	      readstopTimesPath(zip.getInputStream(zip.getEntry(stopTimesPath)), db );
	      
	      readtripsPath(zip.getInputStream(zip.getEntry(tripsPath)), db );
	      
	      //TODO : read Optional files
	     
	      zip.close();
	      
		}catch(Exception e) {
			Log.e(Util.Appname, "error unzipping GTFS : "+e.getMessage());
			return;
		}
		
		
	}



	private void readcalendarPath(InputStream inputStream, SQLiteDatabase db) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		reader.readLine(); // ligne entete

		String line = null;
		int id=1;
		while ((line = reader.readLine()) != null) {
			// service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date
				line = line.replaceAll("\"", "");
				String[] data = line.split(",");

				int calendrier = "1".equals(data[1]) ? 1 : 0;//lundi
				calendrier += "1".equals(data[2]) ? 2 : 0;//mardi
				calendrier += "1".equals(data[3]) ? 4 : 0;//mercredi
				calendrier += "1".equals(data[4]) ? 8 : 0;//jeudi
				calendrier += "1".equals(data[5]) ? 16 : 0;//vendredi
				calendrier += "1".equals(data[6]) ? 32 : 0;//samedi
				calendrier += "1".equals(data[7]) ? 64 : 0;//dimanche

				ContentValues  calendar = new ContentValues();
				calendar.put("_id", id++);
				calendar.put("days", calendrier);
				calendar.put("start_date", data[8]); 
				calendar.put("stop_date", data[9]);
				
				db.insert("CALENDARS", null, calendar);
		}
		reader.close();
		isr.close();
		
	}

	private void readstopTimesPath(InputStream inputStream, SQLiteDatabase db) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		reader.readLine(); // ligne entete

		String line = null;
		int id =1;
		
		while ((line = reader.readLine()) != null) {
			// trip_id,stop_id,stop_sequence,arrival_time,departure_time,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
						line = line.replaceAll("\"", "");
						String[] data = line.split(",");
			
			ContentValues  arret = new ContentValues();
			arret.put("_id", id++); //TODO : no use, delete it ? KEY is idtrips + idstops
			arret.put("idtrips", data[0]);
			arret.put("idstop", data[1]);
			arret.put("arrive", data[3]);
			arret.put("start", data[4]);
			arret.put("sequence", Integer.valueOf(data[2]) );
			
			db.insert("STOPTIMES", null, arret);
			
		}
		reader.close();
		isr.close();
		
	}



	private void readtripsPath(InputStream inputStream, SQLiteDatabase db) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		reader.readLine(); // ligne entete

		String line = null;
		int id =1;
		
		while ((line = reader.readLine()) != null) {
			// trip_id,service_id,route_id,trip_headsign,direction_id,block_id
			line = line.replaceAll("\"", "");
			String[] data = line.split(",");


			ContentValues trajet = new ContentValues();
			trajet.put("_id", id++);
			trajet.put("idroute", data[2]);
			trajet.put("idcalendars", data[1]);

			
			db.insert("TRIPS",null, trajet);
			
		}
		reader.close();
		isr.close();
		
	}



	private void readStops(InputStream inputStream, SQLiteDatabase db) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		reader.readLine(); // ligne entete

		String line = null;
		int id =1;
		
		while ((line = reader.readLine()) != null) {
			// stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station
			line = line.replaceAll("\"", "");
			String[] data = line.split(",");

			ContentValues station = new ContentValues();
			station.put("_id", id++);
			station.put("code", data[1]);
			station.put("nom", data[2]);
			station.put("commune", data[3] );
			station.put("latitude", data[4]);
			station.put("longitude", data[5]);
			station.put("accessible",0);
			station.put("banc",0);
			station.put("eclairage",0);
			station.put("couvert",0);
			db.insert("STOPS", null, station);
			
		}
		reader.close();
		isr.close();
		
	}


	private void readRoutes(InputStream inputStream, SQLiteDatabase db) throws IOException {
		
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(isr);
		reader.readLine(); // ligne entete

		String line = null;
		int id =1;
		
		while ((line = reader.readLine()) != null) {
			// route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color
			line = line.replaceAll("\"", "");
			String[] data = line.split(",");
			
			ContentValues  ligne = new ContentValues();
			ligne.put("_id", id++);
			ligne.put("code", data[2]);
			ligne.put("nom",  data[3]);
			ligne.put("type", data[4]);
			ligne.put("accessible", 0);

			db.insert("ROUTES", null, ligne);
		}
		reader.close();
		isr.close();
	}

}
