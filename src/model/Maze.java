package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Maze {

	public int[][] mazeMatrix;
	public IGraph<String> mazeGraph;
	
	public Maze() {
		mazeMatrix = new int[20][20];
		mazeGraph = new ListGraph<String>(true);
	}
	
	
	public void initializematrix() {
		mazeGraph = new ListGraph<String>(true);
		
		for(int i=0;i<mazeMatrix.length;i++) {
			for(int j=0;j<mazeMatrix[i].length;j++) {
				mazeMatrix[i][j] = 0;
			}
		}
		
		mazeMatrix[0][0] = 1;
		mazeMatrix[19][19] = 1;
		
	}
	
	public void createVertices() {
		mazeGraph = new ListGraph<String>(true);
		
		for(int i=0;i<mazeMatrix.length;i++) {
			for(int j=0;j<mazeMatrix[i].length;j++) {
				
				if(mazeMatrix[i][j]==1) {
					mazeGraph.addVertex(i+"-"+j);
				}
			}
		}
		createEdges();
	}
	
	public void createEdges() {
		
		Random rd = new Random();
		
		for(int i=0;i<mazeMatrix.length;i++) {
			for(int j=0;j<mazeMatrix[i].length;j++) {

				ArrayList<String> edges = getEdges(i,j);
				
				for(String edge : edges) {
					//System.out.println(edge);
					int weight = rd.nextInt(100)+1;
					mazeGraph.addEdge(i+"-"+j, edge, weight);
				}
			}
		}
	}
	
	public ArrayList<String> getShortestPath() {
		String initialVertex = (mazeMatrix.length-1)+"-"+(mazeMatrix.length-1);
		Map<String,String> shortestPath = mazeGraph.bfs(initialVertex);
		//System.out.println(shortestPath.toString());
		ArrayList<String> path = new ArrayList<String>();
		Map<Integer,Integer> indexesPath = new HashMap<Integer,Integer>();
		
		path.add("0-0");
		String prev = shortestPath.get("0-0");
		
		while(prev!=null) {

			path.add(prev);
			prev = shortestPath.get(prev);
		}
		
		return path;
	}
	
	public ArrayList<String> getSafestPathDijkstra() {
		String initialVertex = (mazeMatrix.length-1)+"-"+(mazeMatrix.length-1);
		Map<String,String> shortestPath = mazeGraph.dijkstra(initialVertex);
		ArrayList<String> path = new ArrayList<String>();
		Map<Integer,Integer> indexesPath = new HashMap<Integer,Integer>();
		
		path.add("0-0");
		String prev = shortestPath.get("0-0");
		
		while(prev!=null) {
			path.add(prev);
			prev = shortestPath.get(prev);
		}
		
		return path;
	}
	
	public ArrayList<String> getSafestPathKruskal(){
		ListGraph<String> graph = (ListGraph<String>) mazeGraph.kruskal();
		String initialVertex = (mazeMatrix.length-1)+"-"+(mazeMatrix.length-1);
		Map<String,String> shortestPath = graph.dijkstra(initialVertex);
		ArrayList<String> path = new ArrayList<String>();
		Map<Integer,Integer> indexesPath = new HashMap<Integer,Integer>();
		
		path.add("0-0");
		String prev = shortestPath.get("0-0");
		
		while(prev!=null) {
			path.add(prev);
			prev = shortestPath.get(prev);
		}
		
		return path;
	}
	
	public int[][] getMazeMatrix() {
		return mazeMatrix;
	}
	
    public ArrayList<String> getEdges(int i, int j){
        ArrayList<String> edges = new ArrayList<>();
        
        if(mazeMatrix[i][j]==0){
            return edges;
        }

        if((i-1)>=0 && mazeMatrix[i-1][j]!=0){
            edges.add((i-1)+"-"+(j));
        }

        if((j-1)>=0 && mazeMatrix[i][j-1]!=0){
            edges.add((i)+"-"+(j-1));
        }
        if((j+1)<mazeMatrix[i].length && mazeMatrix[i][j+1]!=0){
            edges.add((i)+"-"+(j));
        }

        if((i+1)<mazeMatrix.length && mazeMatrix[i+1][j]!=0){
            edges.add((i+1)+"-"+(j));
        }

        return edges;
    }
	
    
    public void changeCellStatus(int i, int j) {
    	if(mazeMatrix[i][j]==1) {
    		mazeMatrix[i][j] = 0;
    	}else {
    		mazeMatrix[i][j] = 1;
    	}
    }
    
    public void setMatrix(int[][] matrix) {
    	mazeMatrix = matrix;
    	
    }
}
