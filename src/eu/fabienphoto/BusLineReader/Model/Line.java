package eu.fabienphoto.BusLineReader.Model;

public class Line {

	private String code;

	private String nom;
	private String type;
	private int accessible;
	private boolean full; //if data are provided or not
	
	//constructeur par defaut
	public Line(String code) {
		this.code = code;
		full= false;
	}
	
	public Line(String code, String nom, String type, int accessible) {
		this.code = code;
		this.nom = nom;
		this.accessible = accessible;
		this.type = type;
		full= true;
	}
	

	public String getCode() {
		return code;
	}

	public String getNom() {
		if (!full)
			provide();
		return nom;
	}


	public String getType() {
		if (!full) provide();
		return type;
	}


	public int getAccessible() {
		if (!full) provide();
		return accessible;
	}
	
	private void provide() {
		//TODO fournir le db
	}

}
