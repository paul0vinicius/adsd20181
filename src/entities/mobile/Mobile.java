package entities.mobile;

import eduni.simjava.*;
import eduni.simjava.distributions.*;
import events.EventTypes;
import requests.RequestType;

import java.util.Random;

import javax.swing.event.HyperlinkEvent.EventType;

public class Mobile extends Sim_entity {
	
	Sim_stat stat;

	private Sim_normal_obj mobile_delay;
	private Sim_normal_obj network_delay;
	
	private Sim_port in;
	private Sim_port out;

	public Mobile(String name, double mean, double variance) {
		super(name);
		out = new Sim_port("Out");
		in = new Sim_port("In");
		add_port(out);
		add_port(in);
		
		mobile_delay = new Sim_normal_obj("Delay", mean, variance);
	    add_generator(mobile_delay);
	    
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
		verificaPedidos();

		// O Mobile vai receber uma resposta do banco de dados
		// e vai consumir esses dados povoando a lista.
		while(Sim_system.running()) {
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(mobile_delay.sample());
			sim_completed(e);
			
			if (e.get_tag() == EventTypes.FROM_DB.value) {
				sim_trace(1, "Mobile has received a response from the database.");
			}
			
			if (e.get_tag() == EventTypes.FROM_SOURCE.value) {
				int randomInt = new Random().nextInt(3);
				// 2/3 das vezes que o usuário abre o aplicativo ele requisitará uma muda.
				if (randomInt < 2) {
					requisitaMuda();
				}	
			}	
		}
	}

	// GET dos pedidos já feitos
	private void verificaPedidos() {
		// O aplicativo sempre lista os pedidos do usuário na tela inicial do app
		// fazendo um GET ao BD.
		sim_trace(1, "Mobile has sent a GET request to the database for listing the orders");
		sim_schedule(out, 0, RequestType.GET.getValue());
		sim_pause(network_delay.sample());
	}

	// POST de um novo pedido
	private void requisitaMuda() {
		// O usuário pode requerer uma muda da SESUMA, isso envia uma requisição
		// POST ao Banco de Dados.
		sim_trace(1, "Mobile has sent a POST request to the database for a new order.");
		sim_schedule(out, 0, RequestType.POST.getValue());
		sim_pause(network_delay.sample());
	}

}
