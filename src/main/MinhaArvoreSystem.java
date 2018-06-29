package main;

import eduni.simjava.Sim_system;
import entities.database.Database;
import entities.mobile.Mobile;
import entities.web.Web;

public class MinhaArvoreSystem {

	public MinhaArvoreSystem() {

	}

	public static void main(String[] args) {
		Sim_system.initialise();

		Mobile mob = new Mobile("Mobile");
		Database db = new Database("Database");
		Web web = new Web("Web");

		Sim_system.link_ports("Mobile", "Out", "Database", "In");
		Sim_system.link_ports("Database", "Out", "Mobile", "In");
		Sim_system.link_ports("Web", "Out", "Database", "In");
		Sim_system.link_ports("Database", "Out", "Web", "In");

		Sim_system.set_trace_detail(false, true, false);
		
		Sim_system.run();
	}

}
