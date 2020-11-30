package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VertexADJ<T extends Comparable<T>> extends Vertex<T> {

	private ArrayList<T> edges;
	private Map<T,Integer> weights;
	
	public VertexADJ(T name) {
		super(name);
		edges = new ArrayList<T>();
		weights = new HashMap<T,Integer>();
	}
	
	public ArrayList<T> getEdges(){
		return edges;
	}
	
	public void setEdges(ArrayList<T> edges) {
		this.edges = edges;
	}
	
	public Map<T,Integer> getWeights(){
		return weights;
	}
	
	public void addEdge(T edge) {
		edges.add(edge);
	}
	
	public void eddEdgeWithWeight(T edge,Integer weight) {
		edges.add(edge);
		weights.put(edge, weight);
	}

}
