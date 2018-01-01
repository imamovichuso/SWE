package at.ac.univie.swe.model;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class PlayerTests {

	@Test
	public void testFoundGoldFalse() {
		Board board = new Board();
		Player player1 = new Player(true, board);
		assertFalse(player1.isFoundGold());
	}

}
