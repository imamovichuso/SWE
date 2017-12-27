package at.ac.univie.swe.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.ac.univie.swe.model.Board;
import at.ac.univie.swe.model.Field;
import at.ac.univie.swe.model.Row;

public class GraphUtils {

	public static Graph newGraph(Board board) {
		// building a graph of connected fields
		Graph graph = new Graph();
		List<Row> rows = board.getRows();
		// add ALL VERTICES
		for (Row row : rows) {
			for (Field field : row.getFields()) {
				if (!field.getType().isWater()) {
					graph.addVertice(field);
				}
			}
		}
		// add EDGES
		for (int i = 0; i < rows.size(); i++) {
			List<Field> fields = rows.get(i).getFields();
			for (int j = 0; j < fields.size(); j++) {
				Field field = fields.get(j);
				if (i > 0) {
					Field fieldUp = rows.get(i - 1).getFields().get(j);
					graph.addEdge(field, fieldUp);
				}
				if (i < rows.size() - 1) {
					Field fieldDown = rows.get(i + 1).getFields().get(j);
					graph.addEdge(field, fieldDown);
				}
				if (j > 0) {
					Field fieldLeft = fields.get(j - 1);
					graph.addEdge(field, fieldLeft);
				}
				if (j < fields.size() - 1) {
					Field fieldRight = fields.get(j + 1);
					graph.addEdge(field, fieldRight);
				}
			}
		}
		return graph;
	}

	public static void floodVisit(Vertex vertex, Graph graph) {
		vertex.setVisited(true);
		Set<Vertex> outgoingVertices = new HashSet<>();
		for (Edge e : graph.getEdges()) {
			if (e.getNode1().equals(vertex) && !e.getNode2().isVisited()) {
				outgoingVertices.add(e.getNode2());
			} else if (e.getNode2().equals(vertex) && !e.getNode1().isVisited()) {
				outgoingVertices.add(e.getNode1());
			}
		}
		for (Vertex v : outgoingVertices) {
			floodVisit(v, graph);
		}
	}

}
