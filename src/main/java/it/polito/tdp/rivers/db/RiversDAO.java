package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getRilevazioni(River river) { // in ordine di data crescente
		final String sql = "SELECT day, flow FROM flow WHERE flow.river = ? ORDER BY day ASC";
		
		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add( new Flow( res.getDate("day").toLocalDate(), res.getDouble("flow"), river) );
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
		
	}
	
	public void setFlowAvg(River river) { // in ordine di data crescente
		final String sql = "SELECT AVG(flow) as avg FROM flow WHERE flow.river = ?";
		
		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			res.next();
			double avg = res.getDouble("avg");
			river.setFlowAvg(avg*3600*24); // al GIORNO e non al secondo

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		
	}
}
