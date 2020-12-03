package customExceptions;

@SuppressWarnings("serial")
public class GraphIsEmptyException extends Exception {
	
	public GraphIsEmptyException() {
		super("The graph is empty");
	}
}
