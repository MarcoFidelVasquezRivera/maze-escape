package model;

import java.util.Map;

public interface IGraph <T extends Comparable<T>>{
	
	public void addVertex(T vertex);
	public void addEdge(T vertex,T edge);
	public void addEdge(T vertex,T edge,Integer weight);
	public void deleteVertex(T vertex);
	public Map<T,T> dfs(T initialVertex);
	public Map<T,T> bfs(T initialVertex);
	public Map<T,T> dijkstra(T initialVertex);
	public int[][] floydWarshall();
	public IGraph<T> prim();
	public IGraph<T> kruskal();
	
}
