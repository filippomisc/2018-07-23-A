package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<String> loadAllStates() {
		String sql = "SELECT * FROM state";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
//				State state = new State(rs.getString("id"), rs.getString("Name").toUpperCase(), rs.getString("Capital"),
//						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
//						rs.getString("Neighbors"));
				String state= rs.getString("id").toUpperCase();
				result.add(state);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Integer> loadAllYears() {
		String sql = "SELECT DISTINCT YEAR(s.DATETIME) as year " + 
				"FROM sighting s";
		List<Integer> result = new ArrayList<Integer>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(rs.getInt("year"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<String> loadForme(int anno) {
		String sql = "SELECT distinct s.shape as shape " + 
				"FROM sighting s " + 
				"WHERE YEAR(s.DATETIME)=?";
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				result.add(rs.getString("shape"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Adiacenza> loadAdiacenze(int anno, String shape) {
		String sql = "select n.state1, n.state2, sum(s1.cnt1)+sum(s2.cnt2) as peso " + 
				"from neighbor  n, (select count(id) as cnt1, state as st1 " + 
				"from sighting " + 
				"where shape=? AND YEAR(DATETIME)=? " + 
				"group by state) as s1, " + 
				"(select count(id) as cnt2, state as st2 " + 
				"from sighting " + 
				"where shape=? AND YEAR(DATETIME)=? " + 
				"group by state) as s2 " + 
				"where n.state1=s1.st1 AND n.state2=s2.st2 " + 
				"and s1.cnt1!=0 and s2.cnt2!=0 " + 
				"group by n.state1, n.state2";
		List<Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, shape);
			st.setInt(2, anno);
			st.setString(3, shape);
			st.setInt(4, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Adiacenza adiacenza = new Adiacenza(rs.getString("n.state1").toUpperCase(), rs.getString("n.state2").toUpperCase(), rs.getInt("peso"));
				result.add(adiacenza);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
