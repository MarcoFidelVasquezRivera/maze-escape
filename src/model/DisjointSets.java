package model;

import java.util.ArrayList;

public class DisjointSets<T> {

	private ArrayList<Set> sets;
	
	public DisjointSets() {
		sets = new ArrayList<Set>();
	}
	
	public void makeSet(T value) {
		Set newSet = new Set(value);
		sets.add(newSet);
	}
	
	public void union(T x,T y) {
		Set first = null;
		Set second = null;
		int index = -1;
		for (int i = 0; i < sets.size() && (first==null || second==null); i++) {
			if(first==null) {
				first = sets.get(i).find(x)!=null?sets.get(i):null;
			}
			if(second==null) {
				second = sets.get(i).find(y)!=null?sets.get(i):null;
				index = i;
			}
		}
		if(index!=-1 && first!=second) {
			first.union(second);
			sets.remove(index);
		}
			
	}
	
	public T findSet(T element) {
		T represent = null;
		
		for(Set set : sets) {
			represent = set.find(element);
			if(represent!=null) {
				return represent;
			}
		}
		
		return represent;
	}
	
	
	public class Set{
		
		private T represent;
		ArrayList<T> elements;
		
		public Set(T represent) {
			this.represent = represent;
			elements = new ArrayList<T>();
			elements.add(represent);
		}
		
		public void union(Set anotherSet) {
			elements.addAll(anotherSet.getElements());
		}
		
		public T find(T element) {
			if(elements.contains(element)) {
				return represent;
			}
			return null; 
		}
		
		public ArrayList<T> getElements() {
			return elements;
		}
		
	}
}
