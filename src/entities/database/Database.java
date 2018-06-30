package entities.database;

import eduni.simjava.*;
import eduni.simjava.distributions.*;
import events.EventTypes;
import requests.RequestType;

public class Database extends Sim_entity {
	
	Sim_stat stat;

	private Sim_normal_obj db_delay;
	private Sim_normal_obj network_delay;
	
	private Sim_port in;
	private Sim_port out;
	private Sim_port inW;
	private Sim_port outW;

	public Database(String name, double mean, double variance) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		//inW = new Sim_port("InWeb");
		//outW = new Sim_port("OutWeb");
		add_port(in);
		add_port(out);
		//add_port(inW);
		//add_port(outW);
		
		db_delay = new Sim_normal_obj("Delay", mean, variance);
	    add_generator(db_delay);
	    
	    network_delay = new Sim_normal_obj("Delay", mean, variance);
	    add_generator(network_delay);
	    
	    stat = new Sim_stat();
	    stat.add_measure(Sim_stat.ARRIVAL_RATE);
	    stat.add_measure(Sim_stat.QUEUE_LENGTH);
        stat.add_measure(Sim_stat.THROUGHPUT);
        stat.add_measure(Sim_stat.UTILISATION);
        stat.add_measure(Sim_stat.WAITING_TIME);
        stat.add_measure(Sim_stat.SERVICE_TIME);
        stat.add_measure(Sim_stat.RESIDENCE_TIME);
        set_stat(stat);
	}

	public void body() {
		while(Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(db_delay.sample());
			sim_completed(e);
			sim_trace(1, "The database has responded a request, returning data to the requester");
			sim_schedule(out, 0, EventTypes.FROM_DB.value);
			sim_pause(network_delay.sample());
		}
	}

}
