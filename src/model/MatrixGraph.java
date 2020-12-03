package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class MatrixGraph<T extends Comparable<T>> implements IGraph<T>{

	private List<T> verticesNumbers;
	private boolean bidirectional;
	private Integer[][] weights;
	
	
	public MatrixGraph(int vertexQuantity, boolean bidirectional) {
		verticesNumbers = new ArrayList<T>();
		weights =  new Integer[vertexQuantity][vertexQuantity];
		this.bidirectional = bidirectional;
	}
	
	@Override
	public void addVertex(T vertex) {
		if(!verticesNumbers.contains(vertex) && verticesNumbers.size()<weights.length) {
			verticesNumbers.add(vertex);
		}
	}

	@Override
	public void addEdge(T vertex, T edge) {
		
		if(!verticesNumbers.contains(vertex) || !verticesNumbers.contains(edge)) {
			if(!verticesNumbers.contains(vertex) && !verticesNumbers.contains(edge) ) {
				addVertex(vertex);
				addVertex(edge);
			}else if(!verticesNumbers.contains(vertex) ) {
				addVertex(vertex);
			}else {
				addVertex(edge);
			}
		}
		int vertexNumber = verticesNumbers.indexOf(vertex);
		int edgeNumber = verticesNumbers.indexOf(edge);

		weights[vertexNumber][edgeNumber] = 1;

		if(bidirectional) {
			weights[edgeNumber][vertexNumber] = 1;
		}

		
	}

	@Override
	public void addEdge(T vertex, T edge, Integer weight) {
		if(verticesNumbers.contains(vertex) && verticesNumbers.contains(edge)) {
			int vertexNumber = verticesNumbers.indexOf(vertex);
			int edgeNumber = verticesNumbers.indexOf(edge);
			
			weights[vertexNumber][edgeNumber] = weight;
			
			if(bidirectional) {
				weights[edgeNumber][vertexNumber] = weight;
			}	
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVertex(T vertex) {
		if(verticesNumbers.contains(vertex)) {
			int index = verticesNumbers.indexOf(vertex);
			for(int i=0;i<verticesNumbers.size();i++) {
				weights[index][i]=null;
				weights[i][index]=null;
			}
			
			for(int i=0;i<verticesNumbers.size();i++) {
				for(int j=0;j<verticesNumbers.size();j++) {
					if(j>index) {
						int aux = weights[i][j];
						weights[i][j-1]=aux;
					}
				}
			}
			for(int i=0;i<verticesNumbers.size();i++) {
				for(int j=0;j<verticesNumbers.size();j++) {
					if(i>index) {
						Integer aux []=weights[i];
						weights[i-1]=aux;
					}
				}
			}
			
			verticesNumbers.remove(vertex);
		}
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
				
				int index = verticesNumbers.indexOf(current);
				Integer[] edges = weights[index];
				
				for(int i=0;i<edges.length;i++) {
					if(edges[i]!=null) {
						T aux = verticesNumbers.get(i);
						
						if(!visited.containsKey(aux)) {
							stack.add(aux);
							path.put(aux, current);
						}
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

				int index = verticesNumbers.indexOf(current);
				Integer[] edges = weights[index];
				
				
				for(int i=0;i<edges.length;i++) {
					if(edges[i]!=null) {
						T aux = verticesNumbers.get(i);
						
						if(!visited.containsKey(aux)) {
							queue.add(aux);
							path.put(aux, current);
						}
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
		
		for(int i=0;i<weights.length;i++) {
			T vertex = verticesNumbers.get(i);
			if(vertex!=initialVertex) {
				distances.put(vertex, Integer.MAX_VALUE);
				
				Pair<T> pair = new Pair<T>(vertex,distances.get(vertex));
				pairs.put(vertex, pair);
				pQueue.add(pair);
			}
		}

		
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			int currentIndex = verticesNumbers.indexOf(currentVertex);
			Integer[] currentWeights = weights[currentIndex];
			
			for(int i=0;i<currentWeights.length;i++) {
				if(currentWeights[i]!=null) {
					T neighbor = verticesNumbers.get(i);
					Integer aux = distances.get(currentVertex) + currentWeights[i];
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
		}
		
		return previous;
	}

	@Override
	public int[][] floydWarshall() {
		int length = weights.length;
		int[][] result = new int[length][length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				int value = weights[i][j];
				if(i==j) {
					result[i][j] = 0;
				}else {
					result[i][j] = value!=0?value:Integer.MAX_VALUE;
				}
			}
		}
		for (int k = 0; k <length; k++) {
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					if(result[i][j]> result[i][k]+ result[k][j] && (result[i][k]!=Integer.MAX_VALUE && result[k][j]!=Integer.MAX_VALUE)) {
						result[i][j] = result[i][k] + result[k][j];
					}
				}
			}
		}
		return result;
	}

	@Override
	public IGraph<T> prim() {
		int nVertices = verticesNumbers.size();
		int i, j, k, x, y;
		boolean[] visited = new boolean[nVertices];
		int[] predNode = new int[nVertices];
		visited[0] = true;
		int infinite = Integer.MAX_VALUE;
		int[][] costs = new int[nVertices][nVertices];
		for ( i=0; i < nVertices; i++){
			for ( j=0; j < nVertices; j++){
				costs[i][j] = weights[i][j];
        	 if ( costs[i][j] == 0 )
        		 costs[i][j] = infinite;
			}
		}
		predNode[0] = 0;
		for (k = 1; k < nVertices; k++){
			x = y = 0;
			for ( i = 0; i < nVertices; i++ ) {
				for ( j = 0; j < nVertices; j++ ){
					if ( visited[i] && !visited[j] && costs[i][j] < costs[x][y] ){
						x = i;
						y = j;
					}
				}
			}
			predNode[y] = x;
			visited[y] = true;
		}
		int[] a= predNode;
		MatrixGraph<T> result = new MatrixGraph<>(nVertices, bidirectional);
		for ( i = 0; i < nVertices; i++ ) {
			if(a[i]!=i) {
				result.addEdge(verticesNumbers.get(a[i]), verticesNumbers.get(i));
			}
		}
		return result;
	}

	@Override
	public IGraph<T> kruskal() {
		ListGraph<T> result = new ListGraph<T>(bidirectional);
		DisjointSets<T> sets = new DisjointSets<T>();
		
		for(T vertex : verticesNumbers) {
			sets.makeSet(vertex);
		}

		PriorityQueue<EdgesPair<T>> pQueue = new PriorityQueue<>();
		
		for(int i=0;i<weights.length;i++) {
			Integer[] edgesWeight = weights[i];
			
			for(int j=0;j<edgesWeight.length;j++) {
				
				if(edgesWeight[j]!=null) {
					EdgesPair<T> pair = new EdgesPair<T>(verticesNumbers.get(i), verticesNumbers.get(j), weights[i][j]);
					pQueue.add(pair);
				}
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
	
	public List<T> getVerticesNumbers(){
		return this.verticesNumbers;
	}
	
	public Integer[][] getAdyascenceMatrix(){
		return weights;
	}

}
