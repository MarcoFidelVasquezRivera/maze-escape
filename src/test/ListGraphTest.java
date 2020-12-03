package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import customExceptions.VertexDoesntExistException;
import model.IGraph;
import model.ListGraph;
import model.Pair;
import model.VertexADJ;

public class ListGraphTest {
	
	
	ListGraph<String> stringGraph;
	ListGraph<Integer> integerGraph;
	
	public void setup1() {
		stringGraph = new ListGraph<String>(true);
		integerGraph = new ListGraph<Integer>(true);
	}
	public void setup2() {
		stringGraph = new ListGraph<String>(true);
		stringGraph.addVertex("Atlanta");
		stringGraph.addVertex("Denver");
		stringGraph.addVertex("Chicago");
		stringGraph.addVertex("San Francisco");
		stringGraph.addVertex("New York");
		
		stringGraph.addEdge("Atlanta", "Chicago", 700);
		stringGraph.addEdge("Atlanta", "Denver", 1400);
		stringGraph.addEdge("Atlanta", "San Francisco", 2200);
		stringGraph.addEdge("Atlanta", "New York", 800);
		
		stringGraph.addEdge("San Francisco", "New York", 2000);
		stringGraph.addEdge("San Francisco", "Chicago", 1200);
		stringGraph.addEdge("San Francisco", "Denver", 900);
		
		stringGraph.addEdge("Denver", "Chicago", 1300);
		stringGraph.addEdge("Denver", "New York", 1600);
		
		stringGraph.addEdge("New York", "Chicago", 1000);
	}
	
	public void setup3() {
		integerGraph = new ListGraph<Integer>(false);
		integerGraph.addVertex(1);
		integerGraph.addVertex(2);
		integerGraph.addVertex(3);
		integerGraph.addVertex(4);
		integerGraph.addEdge(2, 1, 4);
		integerGraph.addEdge(1, 3, -2);
		integerGraph.addEdge(3, 4, 2);
		integerGraph.addEdge(4, 2, -1);
		integerGraph.addEdge(2, 3, 3);
		
	}
	public void setup4() {
		stringGraph = new ListGraph<String>(true);
		stringGraph.addVertex("a");
		stringGraph.addVertex("b");
		stringGraph.addVertex("c");
		stringGraph.addVertex("d");
		stringGraph.addVertex("e");
		stringGraph.addVertex("z");
		stringGraph.addEdge("a", "b", 4);
		stringGraph.addEdge("a", "c", 2);
		stringGraph.addEdge("b", "c", 1);
		stringGraph.addEdge("b", "d", 5);
		stringGraph.addEdge("c", "d", 8);
		stringGraph.addEdge("c", "e", 10);
		stringGraph.addEdge("d", "e", 2);
		stringGraph.addEdge("d", "z", 6);
		stringGraph.addEdge("e", "z", 3);
		
	}
	public void setup5() {
		stringGraph = new ListGraph<String>(true);
		stringGraph.addVertex("s");
		stringGraph.addVertex("r");
		stringGraph.addVertex("v");
		stringGraph.addVertex("w");
		stringGraph.addVertex("t");
		stringGraph.addVertex("u");
		stringGraph.addVertex("x");
		stringGraph.addVertex("y");
		stringGraph.addEdge("s", "w");
		stringGraph.addEdge("s", "r");
		stringGraph.addEdge("r", "v");
		stringGraph.addEdge("w", "t");
		stringGraph.addEdge("w", "x");
		stringGraph.addEdge("t", "x");
		stringGraph.addEdge("t", "u");
		stringGraph.addEdge("u", "x");
		stringGraph.addEdge("x", "y");
		stringGraph.addEdge("u", "y");
	
	}
	public void setup6() {
		integerGraph = new ListGraph<Integer>(true);
		integerGraph.addVertex(1);
		integerGraph.addVertex(2);
		integerGraph.addVertex(3);
		integerGraph.addVertex(4);
		integerGraph.addVertex(5);
	}
	
	public void setup7() {
		integerGraph = new ListGraph<Integer>(true);
		integerGraph.addVertex(1);
		integerGraph.addVertex(2);
		integerGraph.addVertex(3);
		integerGraph.addVertex(4);
		integerGraph.addVertex(5);
		integerGraph.addEdge(1, 3);
		integerGraph.addEdge(1, 2);
		integerGraph.addEdge(1, 4);
		integerGraph.addEdge(1, 5);
		integerGraph.addEdge(3, 2);
		integerGraph.addEdge(3, 5);
		integerGraph.addEdge(2, 5);
		integerGraph.addEdge(3, 4);
		integerGraph.addEdge(2, 4);
		integerGraph.addEdge(4, 5);
	}
	public void setup8() {
		stringGraph = new ListGraph<String>(true);
		stringGraph.addVertex("u");
		stringGraph.addVertex("v");
		stringGraph.addVertex("w");
		stringGraph.addVertex("x");
		stringGraph.addVertex("y");
		stringGraph.addVertex("z");
		stringGraph.addEdge("u", "v");
		stringGraph.addEdge("u", "x");
		stringGraph.addEdge("x", "v");
		stringGraph.addEdge("v", "y");
		stringGraph.addEdge("x", "y");
		stringGraph.addEdge("y", "w");
		stringGraph.addEdge("w", "z");
		
	}
	
	@Test
	public void directedGraph() {
		setup3();
		Map<Integer,VertexADJ<Integer>> list = integerGraph.getAdyascenceList();
		List<Integer> edges = new ArrayList<Integer>();
		edges = list.get(1).getEdges();
		System.out.println("vertex 1= "+edges.toString());
		edges = list.get(2).getEdges();
		System.out.println("vertex 2= "+edges.toString());
		edges = list.get(3).getEdges();
		System.out.println("vertex 3= "+edges.toString());
		edges = list.get(4).getEdges();
		System.out.println("vertex 4= "+edges.toString());
		
		
	}
	@Test
	public void addVertexTest() {
		List<Integer> vertices = new ArrayList<Integer>();
		setup1();
		integerGraph.addVertex(1);
		vertices=integerGraph.getVerticesNumbers();
		if(!vertices.contains(1)) {
			fail("The graph is not adding the vertices");
		}
		integerGraph.addVertex(2);
		vertices=integerGraph.getVerticesNumbers();
		if(!vertices.contains(2)) {
			fail("The graph is not adding the vertices");
		}
		integerGraph.addVertex(3);
		vertices=integerGraph.getVerticesNumbers();
		if(!vertices.contains(3)) {
			fail("The graph is not adding the vertices");
		}
		integerGraph.addVertex(4);
		vertices=integerGraph.getVerticesNumbers();
		if(!vertices.contains(4)) {
			fail("The graph is not adding the vertices");
		}
		integerGraph.addVertex(5);
		vertices=integerGraph.getVerticesNumbers();
		if(!vertices.contains(5)) {
			fail("The graph is not adding the vertices");
		}
	}
	
	@Test
	public void addEdgeTest() {
		setup6();
		List<Integer> edges = new ArrayList<Integer>();
		integerGraph.addEdge(1, 2);
		integerGraph.addEdge(1, 3);
		integerGraph.addEdge(1, 4);
		integerGraph.addEdge(1, 5);
		
		Map<Integer,VertexADJ<Integer>> list = integerGraph.getAdyascenceList();
		
		edges = list.get(1).getEdges();
		
		if(!edges.contains(2)) {
			fail("The graph isnt adding the edges properly");
		}
		if(!edges.contains(3)) {
			fail("The graph isnt adding the edges properly");
		}
		if(!edges.contains(4)) {
			fail("The graph isnt adding the edges properly");
		}
		if(!edges.contains(5)) {
			fail("The graph isnt adding the edges properly");
		}
		
	}

	@Test
	public void addEdge2() {
		setup6();
		
		integerGraph.addEdge(1, 2, 20);
		integerGraph.addEdge(2, 3, 30);
		integerGraph.addEdge(3, 4, 24);
		integerGraph.addEdge(4, 5, 5);
		integerGraph.addEdge(5, 1, 10);
		integerGraph.addEdge(1, 3, 45);
		
		Map<Integer,VertexADJ<Integer>> list = integerGraph.getAdyascenceList();
		
		Integer weight = list.get(1).getWeight(2);
		assertTrue(weight==20,"the edges with weights are not saving the correct weights");
		
		weight = list.get(2).getWeight(3);
		assertTrue(weight==30,"the edges with weights are not saving the correct weights");
		
		weight = list.get(3).getWeight(4);
		assertTrue(weight==24,"the edges with weights are not saving the correct weights");
		
		weight = list.get(4).getWeight(5);
		assertTrue(weight==5,"the edges with weights are not saving the correct weights");
		
		weight = list.get(5).getWeight(1);
		assertTrue(weight==10,"the edges with weights are not saving the correct weights");
		
		weight = list.get(1).getWeight(3);
		assertTrue(weight==45,"the edges with weights are not saving the correct weights");
		
	}
	
	@Test
	public void deleteVertexTest() {
		setup7();
		List<Integer> edges = new ArrayList<Integer>();
		try {
			//Deleting vertex 5
			integerGraph.deleteVertex(5);
			Map<Integer,VertexADJ<Integer>> list = integerGraph.getAdyascenceList();
			edges = list.get(1).getEdges();
			if(edges.contains(5)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(2).getEdges();
			if(edges.contains(5)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(3).getEdges();
			if(edges.contains(5)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(4).getEdges();
			if(edges.contains(5)) {
				fail("The graph is not deleting properly");
			}
			edges = integerGraph.getVerticesNumbers();///////////////////////////////////////////////////////////
			if(edges.contains(5)) {
				fail("The vertex is not being deleted");
			}
			//Deleting vertex 4
			integerGraph.deleteVertex(4);
			list = integerGraph.getAdyascenceList();
			edges = list.get(1).getEdges();
			if(edges.contains(4)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(2).getEdges();
			if(edges.contains(4)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(3).getEdges();
			if(edges.contains(4)) {
				fail("The graph is not deleting properly");
			}
			edges = integerGraph.getVerticesNumbers();///////////////////////////////////////////////////////////
			if(edges.contains(4)) {
				fail("The vertex is not being deleted");
			}
			//Deleting vertex 3
			integerGraph.deleteVertex(3);
			list = integerGraph.getAdyascenceList();
			edges = list.get(1).getEdges();
			if(edges.contains(3)) {
				fail("The graph is not deleting properly");
			}
			edges = list.get(2).getEdges();
			if(edges.contains(3)) {
				fail("The graph is not deleting properly");
			}
			edges = integerGraph.getVerticesNumbers();///////////////////////////////////////////////////////////
			if(edges.contains(3)) {
				fail("The vertex is not being deleted");
			}
			//Deleting vertex 2
			integerGraph.deleteVertex(2);
			list = integerGraph.getAdyascenceList();
			edges = list.get(1).getEdges();
			if(edges.contains(2)) {
				fail("The graph is not deleting properly");
			}
			edges = integerGraph.getVerticesNumbers();///////////////////////////////////////////////////////////
			if(edges.contains(2)) {
				fail("The vertex is not being deleted");
			}
			//Deleting vertex 1
			integerGraph.deleteVertex(1);
			list = integerGraph.getAdyascenceList();
			edges = integerGraph.getVerticesNumbers();///////////////////////////////////////////////////////////
			if(!edges.isEmpty()) {
				fail("The vertex is not being deleted");
			}
			//Deleting inexistent vertex
			integerGraph.deleteVertex(10);
			fail("The graph is deleting a inexistent vertex");
		} catch (VertexDoesntExistException e) {
		
		}
		
		
	}
	
	@Test
	public void dfsTest() {
		setup8();
		
		Map<String,String> dfs = stringGraph.dfs("u");
		/*
		for(Map.Entry<String,String> element : dfs.entrySet()) {
			System.out.println(element.getKey()+"---"+element.getValue());
		}
		*/
		String prev = dfs.get("z");
		assertTrue(prev.equalsIgnoreCase("w"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dfs.get("w");
		assertTrue(prev.equalsIgnoreCase("y"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dfs.get("y");
		assertTrue(prev.equalsIgnoreCase("x"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dfs.get("x");
		assertTrue(prev.equalsIgnoreCase("u"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dfs.get("v");
		assertTrue(prev.equalsIgnoreCase("y"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dfs.get("u");
		assertTrue(prev==null,"The dfs method is not creating the correct path because it is nos putting the correcto previous");
	}
	
	@Test
	public void bfsTest() {
		setup5();
		Map<String,String> bfs = stringGraph.bfs("s");
		
		String prev = bfs.get("s");
		assertTrue(prev==null,"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("r");
		assertTrue(prev.equalsIgnoreCase("s"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("w");
		assertTrue(prev.equalsIgnoreCase("s"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("v");
		assertTrue(prev.equalsIgnoreCase("r"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("t");
		assertTrue(prev.equalsIgnoreCase("w"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("u");
		assertTrue(prev.equalsIgnoreCase("t"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("x");
		assertTrue(prev.equalsIgnoreCase("w"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = bfs.get("y");
		assertTrue(prev.equalsIgnoreCase("x"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
	}
	
	@Test
	public void dijkstraTest() {
		setup4();
		Map<String,String> dijkstra = stringGraph.dijkstra("a");
		String prev= dijkstra.get("a");
		assertTrue(prev==null,"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dijkstra.get("b");
		assertTrue(prev.equalsIgnoreCase("c"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dijkstra.get("c");
		assertTrue(prev.equalsIgnoreCase("a"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dijkstra.get("d");
		assertTrue(prev.equalsIgnoreCase("b"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dijkstra.get("e");
		assertTrue(prev.equalsIgnoreCase("d"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		prev = dijkstra.get("z");
		assertTrue(prev.equalsIgnoreCase("e"),"The dfs method is not creating the correct path because it is nos putting the correcto previous");
		
	}
	
	@Test
	public void floydWarshallTest() {
		setup3();
		int [][] floyd = integerGraph.floydWarshall();
		if(floyd[1][0]!=4) {
			fail("incorrect distance");
		}
		if(floyd[2][0]!=5) {
			fail("incorrect distance");
		}
		if(floyd[3][0]!=3) {
			fail("incorrect distance");
		}
		if(floyd[2][1]!=1) {
			fail("incorrect distance");
		}
		if(floyd[3][1]!=-1) {
			fail("incorrect distance");
		}
		if(floyd[3][2]!=1) {
			fail("incorrect distance");
		}
		if(floyd[0][1]!=-1) {
			fail("incorrect distance");
		}
		if(floyd[0][2]!=-2) {
			fail("incorrect distance");
		}
		if(floyd[0][3]!=0) {
			fail("incorrect distance");
		}
		if(floyd[1][2]!=2) {
			fail("incorrect distance");
		}
		if(floyd[1][3]!=4) {
			fail("incorrect distance");
		}
		if(floyd[2][3]!=2) {
			fail("incorrect distance");
		}
	}
	
	@Test
	public void kruskalTest() {
		setup2();
		ListGraph<String> graph = (ListGraph<String>) stringGraph.kruskal();
		List<String> edges = new ArrayList<String>();
		Map<String,VertexADJ<String>> list = graph.getAdyascenceList();
		
		Map<String,String> bfs = graph.bfs("New York");
		
		String prev = bfs.get("New York");
		assertTrue(prev==null,"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("San Francisco");
		assertTrue(prev.equalsIgnoreCase("Chicago"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Chicago");
		assertTrue(prev.equalsIgnoreCase("Atlanta"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Denver");
		assertTrue(prev.equalsIgnoreCase("San Francisco"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Atlanta");
		assertTrue(prev.equalsIgnoreCase("New York"),"The kruskal method is not creating correctly the minimum expansion tree");
	}
	
	@Test
	public void primTest() {
		setup2();
		ListGraph<String> graph =(ListGraph<String>) stringGraph.prim();

		Map<String,String> bfs = graph.bfs("New York");
		
		String prev = bfs.get("New York");
		assertTrue(prev==null,"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("San Francisco");
		assertTrue(prev.equalsIgnoreCase("Chicago"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Chicago");
		assertTrue(prev.equalsIgnoreCase("Atlanta"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Denver");
		assertTrue(prev.equalsIgnoreCase("San Francisco"),"The kruskal method is not creating correctly the minimum expansion tree");
		
		prev = bfs.get("Atlanta");
		assertTrue(prev.equalsIgnoreCase("New York"),"The kruskal method is not creating correctly the minimum expansion tree");
	}
}
