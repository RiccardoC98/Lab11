package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	RiversDAO dao = new RiversDAO();
	
	public List<River> getAllRivers() {
		return dao.getAllRivers();
	}

	public Map<String, String> getUltimiValori(River river) {
		Map<String, String> valori = new HashMap<>();
		
		List<Flow> flows = dao.getRilevazioni(river); 
		valori.put("firstDate",flows.get(0).getDay().toString() ); // prima data disponibile
		valori.put("lastDate", flows.get( flows.size()-1 ).getDay().toString() ); // ultima data disponibile
		valori.put("nItems", String.valueOf( flows.size() ) ); // n.ro misurazioni
		Double media = 0.0;
		for (Flow f : flows) {
			media += f.getFlow();
		}
		valori.put("avg_flow", String.valueOf( media/flows.size() ) ); // la media
		
		return valori;
	}
}
