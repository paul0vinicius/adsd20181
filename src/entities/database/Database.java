package entities.database;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;

public class Database extends Sim_entity {
	
	private Sim_port out;
	private Sim_port in;
	
	public Database(String name) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		add_port(out);
		add_port(in);
	}

}
