package it.polito.tdp.newufosightings.model;

public class TestModel {

	public static void main(String[] args) {

		Model model= new Model();
		
		model.creaGrafo(2013, "fireball");
		System.out.println(model.neighbohrString());
	}

}
