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
		
		Source source = new Source("Source", 50);

		Mobile mob = new Mobile("Mobile", 230.0, 90.0);
		Database db = new Database("Database", 240.0, 133.0);
		Web web = new Web("Web", 120.0, 100.0);
		
		Sim_system.link_ports("Source", "Out_mobile", "Mobile", "In");
		Sim_system.link_ports("Source", "Out_web", "Web", "In");
		
		Sim_system.link_ports("Mobile", "Out", "Database", "In");
		Sim_system.link_ports("Database", "Out", "Mobile", "In");
		Sim_system.link_ports("Web", "Out", "Database", "In");
		Sim_system.link_ports("Database", "Out", "Web", "In");

		Sim_system.set_trace_detail(false, true, false);
		
		Sim_system.run();
	}

}
