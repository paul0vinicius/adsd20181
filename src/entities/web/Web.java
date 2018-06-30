package entities.web;

import eduni.simjava.*;
import eduni.simjava.distributions.*;
import events.EventTypes;
import requests.RequestType;

public class Web extends Sim_entity {
	
	Sim_stat stat;

	private Sim_normal_obj sys_delay;
	private Sim_normal_obj network_delay;

	private Sim_port out;
	private Sim_port in;

	public Web(String name, double mean, double variance) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		add_port(out);
		add_port(in);
		
		
		sys_delay = new Sim_normal_obj("Delay", mean, variance);
	    add_generator(sys_delay);
	    
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

	public void body(){
		veListaTodosPedidos();

		while(Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			// Resposta do BD no get e também ao aceitar o pedido.
			sim_process(sys_delay.sample());
			sim_completed(e);
			
			if (e.get_tag() == EventTypes.FROM_DB.value) {
				sim_trace(1, "Web client has received a response from the database.");
			}
			
			if (e.get_tag() == EventTypes.FROM_SOURCE.value) {
				aceitaPedidos();
			}	
		}
	}

	private void aceitaPedidos() {
		sim_trace(1, "Web client has send a PUT request to the database.");
		sim_schedule(out, 0, RequestType.PUT.getValue());
		sim_pause(network_delay.sample());
	}

	private void veListaTodosPedidos() {
		sim_trace(1, "Web client has send a GET request to the database.");
		sim_schedule(out, 0, RequestType.GET.getValue());
		sim_pause(network_delay.sample());
	}
}
