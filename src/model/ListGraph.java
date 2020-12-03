package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

import customExceptions.VertexDoesntExistException;


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
		}else {
			if(!currentVertex.getEdges().contains(edge)) {
				if(bidirectional) {
					currentVertex.addEdge(edge);
					edgeVertex.addEdge(vertex);	
				}else {
					currentVertex.addEdge(edge);
				}
			}
		}
	}

	@Override
	public void addEdge(T vertex, T edge, Integer weight) {
		addEdge(vertex,edge);
		
		VertexADJ<T> currentVertex = vertices.get(vertex);
		VertexADJ<T> edgeVertex = vertices.get(edge);
		
		if(currentVertex.getEdges().contains(edge)) {
			if(bidirectional) {
				currentVertex.addWeight(edge, weight);
				edgeVertex.addWeight(vertex, weight);
			}else {
				currentVertex.addWeight(edge, weight);
			}
		}
	}

	@Override
	public void deleteVertex(T vertex) throws VertexDoesntExistException {
		VertexADJ<T> currentVertex = vertices.get(vertex);
		
		if(currentVertex==null) {
			throw new VertexDoesntExistException();
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
				Collections.sort(edges);
				
				for(T aux : edges) {
					if(!visited.containsKey(aux)) {
						stack.add(aux);
						if(path.containsKey(aux)) {
							path.remove(aux);
						}
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
				Collections.sort(edges);
				
				for(T aux : edges) {
					if(!visited.containsKey(aux) && !queue.contains(aux)) {
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
		
		previous.put(initialVertex, null);
		distances.put(initialVertex, 0);
		
		PriorityQueue<Pair<T>> pQueue = new PriorityQueue<Pair<T>>();
		Pair<T> fPair = new Pair<T>(initialVertex,0);
		pQueue.offer(fPair);
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			if(mapElement.getKey().compareTo(initialVertex)!=0) {
				
				T key = mapElement.getKey();
				distances.put(key,Integer.MAX_VALUE);
				
				Pair<T> pair = new Pair<T>(key,distances.get(key));
				pairs.put(key, pair);
				pQueue.offer(pair);
				
			}
		}
		
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			ArrayList<T> currentNeighbors = vertices.get(currentVertex).getEdges();
			HashMap<T,Integer> currentWeights = (HashMap<T,Integer>) vertices.get(currentVertex).getWeights();
			
			for(int i=0;i<currentNeighbors.size();i++) {
				T neighbor = currentNeighbors.get(i);
				
				Integer aux = distances.get(currentVertex) + currentWeights.get(neighbor);
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
					if(pairs[i][k]!=Integer.MAX_VALUE &&  pairs[k][j]!=Integer.MAX_VALUE && pairs[i][j]>pairs[i][k]+pairs[k][j]) {
						pairs[i][j]=pairs[i][k]+pairs[k][j];
					}
				}
			}
		}

		return pairs;
	}
	
	public Integer minKey(Map<T,Integer> key,Map<T,Boolean> visited) {
		int min=Integer.MAX_VALUE;
		int minIndex = -1;
		for(int i=0;i<verticesNumbers.size();i++) {	
			if(visited.get(verticesNumbers.get(i))==false && key.get(verticesNumbers.get(i))<min) {
				min=key.get(verticesNumbers.get(i));
				minIndex=i;
			}
		}
		return minIndex;
	}
	@Override
	public IGraph<T> prim() {
		Map<T,Integer> key = new HashMap<>();
		Map<T,T> prev = new HashMap<>();
		Map<T,Boolean> visited = new HashMap<>();
		
		for(int i=0;i<verticesNumbers.size();i++) {
			if(i==0) {
				key.put(verticesNumbers.get(i), 0);
				visited.put(verticesNumbers.get(i),false);
			}else {
				key.put(verticesNumbers.get(i), Integer.MAX_VALUE);
				visited.put(verticesNumbers.get(i),false);
			}
		}
		prev.put(verticesNumbers.get(0), null);
		for(int flag = 0;flag<(vertices.size()-1);flag++) {
			int u = minKey(key,visited);
			visited.put(verticesNumbers.get(u), true);
			
			
			
			for(int i=0;i<verticesNumbers.size();i++) {
				
				if(weight(u,i)!=null && visited.get(verticesNumbers.get(i))==false && weight(u,i)<key.get(verticesNumbers.get(i))) {
					prev.put(verticesNumbers.get(i), verticesNumbers.get(u));/////////////
					key.put(verticesNumbers.get(i), weight(u,i));
				}
			}
			
		}
		
		ListGraph<T> result = new ListGraph<T>(true);
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			System.out.println(mapElement.getValue().getVertexName());
			result.addVertex(mapElement.getValue().getVertexName());
		}

		for(Map.Entry<T,VertexADJ<T>> mapElement : vertices.entrySet()) {
			if(!(prev.get(mapElement.getKey())==null)) {
				result.addEdge(prev.get(mapElement.getKey()), mapElement.getKey(),key.get(mapElement.getKey()));
			}
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
			result.addVertex(mapElement.getKey());
			
			for(Map.Entry<T,Integer> edge : weights.entrySet()) {
				EdgesPair<T> pair = new EdgesPair<T>(mapElement.getKey(),edge.getKey(), edge.getValue());
				pQueue.offer(pair);
			}
		}
		
		while(!pQueue.isEmpty()) {
			EdgesPair<T> pair = pQueue.poll();
			
			T u = pair.getElement();
			T v = pair.getElementTwo();
			
			if(sets.findSet(u).compareTo(sets.findSet(v))!=0) {
				sets.union(u, v);
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
	
	public Map<T,VertexADJ<T>> getAdyascenceList(){
		return vertices;
	}
}
