package at.ac.univie.swe.model;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FieldTests {

	@Test(expected = IllegalArgumentException.class)
	public void testThrowWhenRowNegative() {
		new Field(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrowWhenColumnNegative() {
		new Field(1, -1);
	}

	@Test
	public void testTypeIsNull() {
		Field field = new Field(1, 1);
		assertNull(field.getType());
	}

}
