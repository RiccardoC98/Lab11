package it.polito.tdp.rivers.model;

import it.polito.tdp.rivers.db.RiversDAO;

public class TestSimulator {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		River river = dao.getAllRivers().get(0);
		dao.setFlowAvg(river);
		Simulator sim = new Simulator( river );
		sim.init(0.5);
		sim.run();
		
		System.out.println(" Giorni insoddisfatti " + sim.getGiorniInsoddisfatti() + 
				"\nMedia C: " + sim.getAvgC());
	}
}
