package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Maze;

public class MainViewController {
	
	private Maze maze;
	private MatrixView canvas;
	
    @FXML
    private BorderPane mainPane;
	
    public MainViewController(Maze maze) {
		this.maze = maze;
    	canvas = new MatrixView(this.maze);
	}

    public void initialize() {
    	mainPane.setCenter(canvas);
    	maze.initializematrix();
    	canvas.draw();
    	canvas.setOnMouseClicked(this::handleClick);
    }
    
    public void handleClick(MouseEvent event) {
    	int x = (int) Math.round(event.getX());
    	int y = (int) Math.round(event.getY());
    	canvas.changeCellStatus(x, y);
    }
    
    @FXML
    void defaultMaze(ActionEvent event) {
    	int[][] defaultMaze = { {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				    			{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				    			{1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,1,1,0,1},
				    			{1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1},
				    			{0,0,1,0,0,0,0,1,1,1,1,1,0,0,1,1,1,1,0,1},
				    			{0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
				    			{0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
				    			{0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,0,1,1,1,1},
				    			{0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,1,0,0,1},
				    			{0,0,1,1,1,1,0,1,1,0,0,1,0,0,1,0,1,0,0,1},
				    			{0,0,1,0,0,0,0,1,0,0,0,1,0,0,1,1,1,0,0,1},
				    			{0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,1},
				    			{1,1,1,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,1},
				    			{1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,1},
				    			{1,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,1},
				    			{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				    			{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1},
				    			{1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,0,0,0,1},
				    			{0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,0,0,0,1},
				    			{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1}};
    	
    	maze.setMatrix(defaultMaze);
    	maze.createVertices();
    	canvas.updateTileMap();
    	canvas.draw();
    }

    @FXML
    void reset(ActionEvent event) {
    	maze.initializematrix();
    	canvas.updateTileMap();
    	canvas.draw();
    }

    @FXML
    void safestPathDjikstra(ActionEvent event) {
    	canvas.drawPath(2);
    }

    @FXML
    void safestPathKruskal(ActionEvent event) {
    	canvas.drawPath(1);
    }

    @FXML
    void shortestPath(ActionEvent event) {
    	canvas.drawPath(0);
    }
    

    @FXML
    void setAsMaze(ActionEvent event) {
    	maze.createVertices();
    }

}
