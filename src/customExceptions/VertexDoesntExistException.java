package customExceptions;

@SuppressWarnings("serial")
public class VertexDoesntExistException extends Exception {
	public VertexDoesntExistException() {
		super("The vertex doesnt exist in the graph");
	}
}
