package eu.fabienphoto.BusLineReader.Model;

import java.util.ArrayList;
import java.util.List;

public class Network {

	private String Name;
	private ArrayList<Line> lines;
	
	//constructeur
	public Network(String name) {
		Name = name;
	}
	
	
	public ArrayList<Line> getLines() {
		return lines;
	}

	
	//TODO : conversion aléatoire
	public void setLines(List<Line> lines) {
		this.lines = (ArrayList<Line>) lines;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	
}
