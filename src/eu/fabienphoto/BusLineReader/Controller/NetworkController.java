package eu.fabienphoto.BusLineReader.Controller;

import java.util.ArrayList;
import java.util.List;

import eu.fabienphoto.BusLineReader.Model.Network;

public class NetworkController {

	
	//SIngleton
	private static NetworkController singleton ;
	/**
	 * Singleton access 
	 * @return the NetworkController 
	 */
	public static NetworkController getInstance()  {
		if( singleton == null ) 
			singleton = new NetworkController() ;
		
		return singleton;
	}
	
	
	public List<Network> getNetworks() {
		ArrayList<Network> listes = new ArrayList<Network>();
		
		//TODO : determiner la liste
		listes.add(new Network("rennes"));
		return listes;
	}
}
