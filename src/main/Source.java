package main;

import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_port;
import events.EventTypes;

public class Source extends Sim_entity {

	private Sim_port out_mobile;  // Porta para enviar eventos pro mobile
	private Sim_port out_web;  // Porta para enviar eventos pro web
   
	private double delay;

    Source(String name, double delay) {
      super(name);
      this.delay = delay;
      
      out_mobile = new Sim_port("Out_mobile");
      out_web = new Sim_port("Out_web");
      
      add_port(out_mobile);
      add_port(out_web);
    }

    public void body() {
      for (int i=0; i < 10000; i++) {
        sim_schedule(out_mobile, 0.0, EventTypes.FROM_SOURCE.value);
        
        // Deve sair (7 vezes) mais requisições via mobile do que web. O número 7 não é representativo.
        if (i % 7 == 0) {
        	sim_schedule(out_web, 0.0, EventTypes.FROM_SOURCE.value);
        }
        
        sim_pause(delay);
      }
    }

}
