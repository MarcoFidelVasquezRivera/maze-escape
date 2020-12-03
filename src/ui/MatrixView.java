package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Maze;
import model.VertexADJ;


public class MatrixView extends Canvas{

	public static final int SIZE = 600;
	private GraphicsContext graphics;
	private int[][] tileMap;
	private Maze maze;

	
	public MatrixView(Maze maze) {
		super(800,800);
		graphics = super.getGraphicsContext2D();
		this.maze = maze;
		tileMap = maze.getMazeMatrix();
		
		graphics.setFill(Color.GRAY);
		graphics.fillRect(0, 0, SIZE, SIZE);
	}
	
	public void updateTileMap() {
		tileMap = maze.getMazeMatrix();
	}
	
	public void draw() {
		
		int posX = 0;
		int posY = 0;
		
		int xSize = SIZE/(tileMap.length);
		int ySize = SIZE/(tileMap.length);

		for(int i=0;i<tileMap.length;i++) {
			posY = i*ySize;
			for(int j=0;j<tileMap[i].length;j++) {
				posX = j*xSize;
				
				int status = tileMap[i][j];
				
				if(status==1) {
					graphics.setFill(Color.WHITE);
					graphics.fillRect(posX, posY, xSize, ySize);
					graphics.setStroke(Color.GRAY);
					graphics.strokeRect(posX, posY, xSize, ySize);
				}else {
					graphics.setFill(Color.BLACK);
					graphics.fillRect(posX, posY, xSize, ySize);
					graphics.setStroke(Color.GRAY);
					graphics.strokeRect(posX, posY, xSize, ySize);
				}
			}
		}
	}
	
	public void drawPath(int option) {
		draw();
		ArrayList<String> path = new ArrayList<String>();
		
		int xSize = SIZE/(tileMap.length);
		int ySize = SIZE/(tileMap.length);
		
		Color color = Color.WHITE;
		try {
		
		switch(option) {
			
		case 0: path = maze.getShortestPath();
				color = Color.AQUA;
			break;
		
		case 1: path = maze.getSafestPathKruskal();
				color = Color.RED;
			break;
			
		case 2: path = maze.getSafestPathDijkstra();
				color = Color.RED;
			break;
		}	
		
		
		for(String element : path) {
			String[] indexes = element.split("-");
			
			int posY = Integer.parseInt(indexes[0])*ySize;
			int posX = Integer.parseInt(indexes[1])*xSize;
			
			graphics.setFill(color);
			graphics.fillRect(posX, posY, xSize, ySize);
			graphics.setStroke(Color.GRAY);
			graphics.strokeRect(posX, posY, xSize, ySize);
		}
		}catch(Exception e) {
			
		}

		
		
	}
	
	public void changeCellStatus(int x,int y) {
		int xSize = SIZE/(tileMap.length);
		int ySize = SIZE/(tileMap.length);

		int column = x/ySize;
		int row = y/xSize;

		if((column==0 && row==0) || (column==(tileMap.length-1) && row==(tileMap.length-1))) {

			
		}else if((column>-1 && row>-1)  && (column<=(tileMap.length-1) && row<=(tileMap.length-1))) {
			maze.changeCellStatus(row, column);
			this.updateTileMap();
			this.draw();
		}

	}
	
}