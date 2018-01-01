package at.ac.univie.swe.graph;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import at.ac.univie.swe.model.Field;

public class VertexTests {

	@Test
	public void testVisitedFalse() {
		Field field = new Field(1, 1);
		Vertex vertex = new Vertex(field);
		assertFalse(vertex.isVisited());
	}

}
