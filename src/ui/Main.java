package ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Maze;

public class Main extends Application {

	private MainViewController mainController;
	private Maze maze;
	
	public Main() {
		maze = new Maze();
		mainController = new MainViewController(maze);
		
	}

	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/MainView.fxml"));
		fxmlLoader.setController(mainController);
		
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("maze-escape");
		primaryStage.setResizable(true);
		primaryStage.show();
	}
}
