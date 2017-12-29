package at.ac.univie.swe.graph;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Vertex> vertices;
	private int weight;

	public Path() {
		vertices = new ArrayList<>();
		weight = 10_000;
	}

	public Path(List<Vertex> vertices, int weight) {
		this.vertices = vertices;
		this.weight = weight;
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Path [weight=" + weight + ", vertices=" + vertices + "]";
	}

}
