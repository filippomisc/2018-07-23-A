package it.polito.tdp.newufosightings.model;

public class Adiacenza {
	
	private String s1;
	private String s2;
	private int peso;
	
	public Adiacenza(String s1, String s2, int peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}

	public String getS1() {
		return s1;
	}

	public String getS2() {
		return s2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adiacenza [s1=");
		builder.append(s1);
		builder.append(", s2=");
		builder.append(s2);
		builder.append(", peso=");
		builder.append(peso);
		builder.append("]");
		return builder.toString();
	}

	

}
