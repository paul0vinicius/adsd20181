package entities.web;

import eduni.simjava.*;
import requests.RequestType;

public class Web extends Sim_entity {

	private final double NETWORK_DELAY = 50;
	private final double SYS_DELAY = 10;

	private Sim_port out;
	private Sim_port in;

	public Web(String name) {
		super(name);
		in = new Sim_port("In");
		out = new Sim_port("Out");
		add_port(out);
		add_port(in);
	}

	public void body(){
		veListaTodosPedidos();

		for (int i = 0; i < 10; i++) {
			aceitaPedido();
		}

		while(Sim_system.running()) {
			sim_trace(1, "Web client has received a response from the database.");
			Sim_event e = new Sim_event();
			sim_get_next(e);
			// Resposta do BD no get e também ao aceitar o pedido.
			sim_process(SYS_DELAY);
			sim_completed(e);
		}
	}

	private void aceitaPedido() {
		sim_trace(1, "Web client has send a PUT request to the database.");
		sim_schedule(out, 0, RequestType.PUT.getValue());
		System.out.println("Pedido aceito (PUT WEB)");
		sim_pause(NETWORK_DELAY);
	}

	private void veListaTodosPedidos() {
		sim_trace(1, "Web client has send a GET request to the database.");
		sim_schedule(out, 0, RequestType.GET.getValue());
		System.out.println("Verifica pedidos (GET Web)");
		sim_pause(NETWORK_DELAY);
	}
}
