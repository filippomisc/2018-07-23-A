package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	
	private NewUfoSightingsDAO dao;
	private List<Integer> anni;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> graph;
	private List<Adiacenza> adiacenze;

	public Model() {
		dao= new NewUfoSightingsDAO();
		anni= new ArrayList<>(dao.loadAllYears());
		
		
	}

	public void creaGrafo(int anno, String shape) {

		this.graph=new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.graph, dao.loadAllStates());
		System.out.println("vertici creati: "+this.graph.vertexSet().size());
		System.out.println("vertici creati: "+this.graph.vertexSet());


		
		adiacenze=dao.loadAdiacenze(anno, shape);
		
		for (Adiacenza a : adiacenze) {
			if(this.graph.getEdge(a.getS1(), a.getS2())==null //servono per non creare due volte lo stesso
															  //arco tra due vertici (perche non direzionati)
					&& this.graph.getEdge(a.getS2(), a.getS1())==null) {
			Graphs.addEdge(this.graph, a.getS1(), a.getS2(), a.getPeso());
			}
			
		}
		
		System.out.println("archi creati: "+this.graph.edgeSet().size());

	}
	
	public boolean isValid(int anno) {

		boolean res= false;
		for (int year : anni) {
			if(anno==year) {
				res= true;
			}
			
		}
		return res;
	}

	public List<String> getForme(int anno) {
		return dao.loadForme(anno);
	}

	public String neighbohrString() {
	
		StringBuilder builder = new StringBuilder();
		for (String vertice : this.graph.vertexSet()) {
			
			builder.append(vertice + " peso totale: ");
			
			int pesoTot=0;
			
			Set<DefaultWeightedEdge> archi= this.graph.outgoingEdgesOf(vertice);
			
			for (DefaultWeightedEdge defaultWeightedEdge : archi) {
				pesoTot+= this.graph.getEdgeWeight(defaultWeightedEdge);
				
			}
			
			builder.append(pesoTot + "\n");
			
		}
		return builder.toString();
	}
	

}
