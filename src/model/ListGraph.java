package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class ListGraph<T extends Comparable<T>> implements IGraph<T> {
	
	private List<T> verticesNumbers;
	private Map<T,VertexADJ<T>> vertices;
	private boolean bidirectional;

	
	public ListGraph(boolean bidirectional) {
		this.bidirectional = bidirectional;
		vertices = new HashMap<T,VertexADJ<T>>();
		verticesNumbers = new ArrayList<T>();
	}
	
	@Override
	public void addVertex(T vertex) {
		
		if(vertices.get(vertex)==null) {
			VertexADJ<T> newVertex = new VertexADJ<T>(vertex);
			verticesNumbers.add(vertex);
			vertices.put(vertex, newVertex);
		}else {
			//tirar una excepcion
		}
	}

	@Override
	public void addEdge(T vertex, T edge) {
		VertexADJ<T> currentVertex = vertices.get(vertex);
		VertexADJ<T> edgeVertex = vertices.get(edge);
		
		if(currentVertex==null || edgeVertex==null) {
			//tirar la excepcion
		}
		
		if(bidirectional) {
			currentVertex.addEdge(edge);
			edgeVertex.addEdge(vertex);	
		}else {
			currentVertex.addEdge(edge);
		}

	}

	@Override
	public void addEdge(T vertex, T edge, Integer weight) {
		addEdge(vertex,edge);
		
		VertexADJ<T> currentVertex = vertices.get(vertex);
		VertexADJ<T> edgeVertex = vertices.get(edge);
		
		if(bidirectional) {
			currentVertex.addWeight(edge, weight);
			edgeVertex.addWeight(vertex, weight);
		}else {
			currentVertex.addWeight(edge, weight);
		}
		
	}

	@Override
	public void deleteVertex(T vertex) {
		VertexADJ<T> currentVertex = vertices.get(vertex);
		
		if(currentVertex==null) {
			//lanzar excepcion
		}
		
		vertices.remove(vertex);
		
		if(bidirectional) {
			for(T edge : currentVertex.getEdges()) {
				
				VertexADJ<T> edgeVertex = vertices.get(edge);
				edgeVertex.deleteEdge(vertex);
			}
		}else {
			for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
				VertexADJ<T> edgeVertex = mapElement.getValue();
				edgeVertex.deleteEdge(vertex);
			}
		}
		verticesNumbers.remove(vertex);
	}

	@Override
	public Map<T, T> dfs(T initialVertex) {
		Map<T,Boolean> visited = new HashMap<T,Boolean>();
		Map<T,T> path = new HashMap<T,T>();
		Stack<T> stack = new Stack<T>();
		
		path.put(initialVertex, null);
		stack.add(initialVertex);
		
		while(!stack.isEmpty()) {
			T current = stack.pop();
			
			if(!visited.containsKey(current)) {
				visited.put(current, true);

				List<T> edges = vertices.get(current).getEdges();
				
				for(T aux : edges) {
					if(!visited.containsKey(aux)) {
						stack.add(aux);
						path.put(aux, current);
					}
				}
			}
		}
		
		return path;
	}

	@Override
	public Map<T, T> bfs(T initialVertex) {
		Map<T,Boolean> visited = new HashMap<T,Boolean>();
		Map<T,T> path = new HashMap<T,T>();
		Queue<T> queue = new LinkedList<T>();
		
		path.put(initialVertex, null);
		queue.add(initialVertex);
		
		while(!queue.isEmpty()) {
			T current = queue.poll();
			
			if(!visited.containsKey(current)) {
				visited.put(current, true);

				List<T> edges = vertices.get(current).getEdges();
				
				for(T aux : edges) {
					if(!visited.containsKey(aux)) {
						queue.add(aux);
						path.put(aux, current);
					}
				}
			}
		}
		
		return path;
	}

	@Override
	public Map<T, T> dijkstra(T initialVertex) {
		Map<T,Integer> distances = new HashMap<T,Integer>();
		Map<T,T> previous = new HashMap<T,T>();
		Map<T,Pair<T>> pairs = new HashMap<T,Pair<T>>();
		distances.put(initialVertex, 0);
		
		PriorityQueue<Pair<T>> pQueue = new PriorityQueue<Pair<T>>();
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			if(mapElement.getKey().compareTo(initialVertex)!=0) {
				
				T key = mapElement.getKey();
				distances.put(key,Integer.MAX_VALUE);
				
				Pair<T> pair = new Pair<T>(key,distances.get(key));
				pairs.put(key, pair);
				pQueue.add(pair);
				
			}
		}
		
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			ArrayList<T> currentNeighbors = vertices.get(currentVertex).getEdges();
			HashMap<T,Integer> currentWeights = (HashMap<T,Integer>) vertices.get(currentVertex).getWeights();
			
			for(int i=0;i<currentNeighbors.size();i++) {
				T neighbor = currentNeighbors.get(i);
				Integer aux = distances.get(currentVertex) + currentWeights.get(i);
				Integer currentDistance = distances.get(neighbor);
				
				if(aux < currentDistance) {
					Pair<T> currentPair = pairs.get(neighbor);
					
					distances.remove(neighbor);
					distances.put(neighbor, aux);
					
					previous.remove(neighbor);
					previous.put(neighbor,currentVertex);
					
					pQueue.remove(currentPair);
					currentPair.setWeight(aux);
					pQueue.add(currentPair);
				}
			}
		}
		
		return previous;
	}

	@Override
	public int[][] floydWarshall() {
		int [][] pairs = new int[vertices.size()][vertices.size()];
		for(int i=0;i<pairs.length;i++) {
			for(int j=0;j<pairs[0].length;j++) {
				if(i==j) {
					pairs[i][j]=0;
				}else {
					pairs[i][j]=Integer.MAX_VALUE;
				}		
			}
		}
		
		for(int i=0;i<pairs.length;i++) {
			for(int j=0;j<pairs[0].length;j++) {
				if(weight(i,j)!=null) {
					pairs[i][j]=weight(i,j);	
				}		
			}
		}
		
		for(int k=0;k<pairs.length;k++) {
			for(int i=0;i<pairs.length;i++) {
				for(int j=0;j<pairs.length;j++) {
					if(pairs[i][j]>(pairs[i][k]+pairs[k][j])) {
						pairs[i][j]=pairs[i][k]+pairs[k][j];
					}
				}
			}
		}

		return pairs;
	}
	
	public Integer minKey(int[]key,boolean[]visited) {
		int min=Integer.MAX_VALUE;
		int minIndex = -1;
		for(int i=0;i<vertices.size();i++) {
			if(visited[i]==false && key[i]<min) {
				min=key[i];
				minIndex=i;
			}
		}
		return minIndex;
	}
	@Override
	public IGraph<T> prim() {
		
		int [] key = new int[vertices.size()];
		Map<T,T> prev = new HashMap<>();
		boolean visited[] = new boolean[vertices.size()];
		
		for(int i=0;i<vertices.size();i++) {
			key[i]=Integer.MAX_VALUE;
		}
		key[0]=0;
		prev.put(verticesNumbers.get(0), null);
		for(int flag =0;flag<vertices.size()-1;flag++) {
			int u= minKey(key,visited);
			visited[u]=true;
			
			ArrayList <T> adj =vertices.get(verticesNumbers.get(u)).getEdges();
			for(int i=0;i<adj.size();i++) {
				if(weight(u,i)!=null && visited[i]==false && weight(u,i)<key[i]) {
					prev.put(verticesNumbers.get(i), verticesNumbers.get(u));
					key[i]=weight(u,i);
				}
			}
		}
		
		ListGraph<T> result = new ListGraph<T>(true);
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			result.addVertex(mapElement.getKey());
		}
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
				result.addEdge(prev.get(mapElement.getKey()), mapElement.getKey());
		}
		return result;
	}
	
	
	@Override
	public IGraph<T> kruskal() {
		ListGraph<T> result = new ListGraph<T>(bidirectional);
		DisjointSets<T> sets = new DisjointSets<T>();
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			sets.makeSet(mapElement.getKey());
		}
		
		PriorityQueue<EdgesPair<T>> pQueue = new PriorityQueue<>();
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			Map<T,Integer> weights = mapElement.getValue().getWeights();
			
			for(Map.Entry<T,Integer> edge : weights.entrySet()) {
				EdgesPair<T> pair = new EdgesPair<T>(mapElement.getKey(),edge.getKey(), edge.getValue());
				pQueue.add(pair);
			}
		}
		
		while(!pQueue.isEmpty()) {
			EdgesPair<T> pair = pQueue.poll();
			
			T u = pair.getElement();
			T v = pair.getElementTwo();
			
			if(sets.findSet(u)!=sets.findSet(v)) {
				sets.union(u, v);
				result.addVertex(u);
				result.addVertex(v);
				result.addEdge(u, v, pair.getWeight());
			}
		}

		return result;
	}
	
	public Integer weight(Integer vertex,Integer edge) {
		return vertices.get(verticesNumbers.get(vertex)).getWeight(verticesNumbers.get(edge));
	}
	
	public List<T> getVerticesNumbers(){
		return this.verticesNumbers;
	}
}
