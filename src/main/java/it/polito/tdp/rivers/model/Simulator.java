package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {

	// Parametri
	private double Q; // capienza del bacino in m^3
	private double fout_min;
	
	// Stato del sistema
	private double C; // quantità d'acqua presente ogni giorno 
	
	// Output
	private int nGiorniNonSoddisfatti;
	private List<Double> listC;
	
	// Struttura degli eventi
	PriorityQueue<Event> queue;
	
	// Strutture ausiliarie
	List<Flow> rilevazioni;
	RiversDAO dao;
	private River river;
	
	public Simulator(River river) {
		super();
		this.river = river;
		this.dao = new RiversDAO();
	}



	public void init(double k) {
		this.queue = new PriorityQueue<>(); // svuoto la coda
		this.listC = new ArrayList<>(); // svuoto l'elenco di valori di C
		this.nGiorniNonSoddisfatti = 0;
		this.rilevazioni = dao.getRilevazioni(river); // elenco di rilevazioni
		
		//parametri
		this.Q = ( k* ( river.getFlowAvg() ) *30 ); // fattoreK* (f_med * 3600sec/h*24h) *30gg;
		this.C = Q/2;
		this.fout_min = 0.8 * ( river.getFlowAvg() );  // è già impostata in [m^3/giorno]
		listC.add(C); // all'inizio è a Q/2
		
		//prepararo gli eventi
		
		for (Flow f : rilevazioni) {
			
			// genero la F_IN del giorno 
			Event in = new Event(EventType.F_IN, f.getDay(), f.getFlow()*3600*24 ); // al GIORNO e non al secondo
			
			// genero la F_OUT del giorno con P[10*fout_min] = 5% -->la probabilità di irrigazione
			ThreadLocalRandom random = ThreadLocalRandom.current();
			int val = random.nextInt(1, 101);
			double f_out = fout_min;
			if ( val <= 5 ) {
				f_out = f_out*10.0;
			}
			
			Event out = new Event(EventType.F_OUT, f.getDay(), f_out);
			// aggiungo gli eventi alla queue
			
			queue.add(in);
			queue.add(out);
		}
		
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e1 = this.queue.poll(); // due eventi per giorno, sempre (IN) --> bastava un unico evento DAY con entrambi i valori
			Event e2 = this.queue.poll(); // (OUT)
			processEvents(e1, e2);
		}
	}
	
	
	private void processEvents(Event in, Event out) {
		Double fin = in.getFlow();
		Double fout = out.getFlow();
		System.out.println("FIN " + fin + "\tFOUT " + fout);
		C += fin; // l'acqua entra
		if ( C > Q ) { // se supero il livello max --> TRACIMAZIONE
			C = Q; 
		}
		
		C -= fout; // lacqua esce
		if ( C < 0 ) {
			nGiorniNonSoddisfatti += 1; // Eh non sono riuscuto ad avere abbastanza acqua
			C = 0; // ristabilisco che sia vuoto
		}
		
		listC.add(C); // salvo il livello d'acqua a fine giornata
	}
	
	public int getGiorniInsoddisfatti() {
		return nGiorniNonSoddisfatti;
	}
	
	public double getAvgC() {
		double avg = 0;
		for ( Double d : listC) {
			avg += d;
		}
		
		return avg;
	}
}
