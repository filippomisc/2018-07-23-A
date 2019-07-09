package it.polito.tdp.newufosightings.db;

public class TestDAO {

	public static void main(String[] args) {

		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();

		System.out.println(dao.loadAllStates().size());
		System.out.println(dao.loadAllSightings().size());
		System.out.println(dao.loadAllYears().size());
		System.out.println(dao.loadForme(1980).size());
		System.out.println(dao.loadAdiacenze(2013, "fireball"));



	}

}
