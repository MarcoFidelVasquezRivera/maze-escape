package model;

public class EdgesPair<T extends Comparable<T>> implements Comparable<Integer> {
	private T element;
	private T elementTwo;
	private Integer weight;
	
	public EdgesPair(T element, T elementTwo, Integer weight) {
		this.element = element;
		this.weight = weight;
		this.elementTwo = elementTwo;
	}
		
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Integer o) {
		return o-weight;
	}
	public T getElementTwo(){
		return elementTwo;
	}
}
