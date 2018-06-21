package entities.database;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_event;
import eduni.simjava.Sim_port;
import eduni.simjava.Sim_system;
import requests.RequestType;

public class Database extends Sim_entity {
	
	private final double NETWORK_DELAY = 50;
	private final double DB_DELAY = 100;
	private Sim_port in;
	private Sim_port out;
	private Sim_port inW;
	private Sim_port outW;
	
	public Database(String name) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		//inW = new Sim_port("InWeb");
		//outW = new Sim_port("OutWeb");
		add_port(in);
		add_port(out);
		//add_port(inW);
		//add_port(outW);
	}
	
	public void body() {
		while(Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(DB_DELAY);
			sim_completed(e);
			// Retorna o dado para quem solicitou, seja o mobile ou desktop.
			sim_schedule(out, 0, 3);
			sim_pause(NETWORK_DELAY);
			System.out.println("Retorna dado de pedido(s) (Retorno do BD)");
		}
	}

}
