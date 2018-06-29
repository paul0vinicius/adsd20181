package entities.mobile;

import eduni.simjava.*;
import requests.RequestType;

public class Mobile extends Sim_entity {

	// Deve ser um valor probabilístico
	private final double NETWORK_DELAY = 50;
	private final double MOBILE_DELAY = 10;
	private Sim_port in;
	private Sim_port out;

	public Mobile(String name) {
		super(name);
		out = new Sim_port("Out");
		in = new Sim_port("In");
		add_port(out);
		add_port(in);
	}

	public void body() {
		verificaPedidos();
		for (int i = 0; i < 3; i++) {
			requisitaMuda();
		}

		// O Mobile vai receber uma resposta do banco de dados
		// e vai consumir esses dados povoando a lista.
		while(Sim_system.running()) {
			sim_trace(1, "Mobile has received a response from the database.");
			Sim_event e = new Sim_event();
			sim_get_next(e);
			sim_process(MOBILE_DELAY);
			sim_completed(e);
		}
	}

	// GET dos pedidos já feitos
	private void verificaPedidos() {
		// O aplicativo sempre lista os pedidos do usuário na tela inicial do app
		// fazendo um GET ao BD.
		sim_trace(1, "mobile has sent a GET request to the database for listing the orders");
		sim_schedule(out, 0, RequestType.GET.getValue());
		System.out.println("Verifica pedidos (GET Mobile)");
		sim_pause(NETWORK_DELAY);
	}

	// POST de um novo pedido
	private void requisitaMuda() {
		// O usuário pode requerer uma muda da SESUMA, isso envia uma requisição
		// POST ao Banco de Dados.
		sim_trace(1, "Mobile has sent a POST to the database for a new order.");
		sim_schedule(out, 0, RequestType.POST.getValue());
		System.out.println("Muda requisitada (POST Mobile)");
		sim_pause(NETWORK_DELAY);
	}

}
