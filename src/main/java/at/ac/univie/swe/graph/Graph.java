package at.ac.univie.swe.graph;

import java.util.HashSet;
import java.util.Set;

import at.ac.univie.swe.model.Field;

public class Graph {

	private Set<Vertex> vertices;
	private Set<Edge> edges;

	public Graph() {
		this.vertices = new HashSet<>();
		this.edges = new HashSet<>();
	}

	public Set<Vertex> getVertices() {
		return vertices;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public void addVertice(Field field) {
		Vertex vertex = new Vertex(field);
		for (Vertex v : getVertices()) {
			if (v.getField().equals(field)) {
				// must be SAME INSTANCE, so when setVisited is called it is valid for all edges
				vertex = v;
			}
		}
		getVertices().add(vertex);
	}

	public void addEdge(Field field1, Field field2) {
		Vertex node1 = null;
		Vertex node2 = null;
		for (Vertex v : getVertices()) {
			if (v.getField().equals(field1)) {
				node1 = v;
			}
			if (v.getField().equals(field2)) {
				node2 = v;
			}
		}
		// edges are ADDED if there is a CONNECTION, e.g grass->mountain etc.
		int weight = getWeight(field1, field2);
		if (weight > 0) {
			Edge edge = new Edge(node1, node2, weight);
			getEdges().add(edge);
		}
	}

	private static int getWeight(Field field, Field fieldUp) {
		int weight = -1;
		if (field.getType().isGrass()) {
			if (fieldUp.getType().isGrass()) {
				weight = 1;
			} else if (fieldUp.getType().isMountain()) {
				weight = 2;
			}
		} else if (field.getType().isMountain()) {
			if (fieldUp.getType().isGrass()) {
				weight = 2;
			} else if (fieldUp.getType().isMountain()) {
				weight = 4;
			}
		}
		return weight;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Graph [edges=");
		/*
		 * for (Vertex v : vertices) { stringBuilder.append("\n  " + v); }
		 */
		for (Edge e : edges) {
			stringBuilder.append("\n  " + e);
		}
		stringBuilder.append("\n]");
		return stringBuilder.toString();
	}

}
