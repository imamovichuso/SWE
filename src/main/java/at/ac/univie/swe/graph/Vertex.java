package at.ac.univie.swe.graph;

import java.util.HashSet;
import java.util.Set;

import at.ac.univie.swe.model.Field;

public class Vertex {

	private Field field;
	private boolean visited;
	// private Set<Vertex> neighbors;

	public Vertex(Field content) {
		this.field = content;
		// this.neighbors = new HashSet<>();
	}

	public Field getField() {
		return field;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/*
	 * public Set<Vertex> getNeighbors() { return neighbors; }
	 */

	@Override
	public String toString() {
		return "Vert[" + field + ",vis=" + visited + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}

}
