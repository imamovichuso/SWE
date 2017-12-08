/**
 * 
 */
package at.ac.univie.swe;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author imamovichuso
 *
 */
public class Unittest {
	static GameRepository gameRepository;

	/**
	 * @throws java.lang.Exception
	 */
	public void mytest() throws Exception {
		Game demogame = new Game("Demo game",5, 3, 5, 100);
			assertEquals("Demo game",demogame.getName());
		Player x = new Player("John", "Smith", "john.smith@example.com");
		assertEquals("John",x.getFirstName());
	}

	

	@Test
	public void test() throws Exception {
		mytest();
	}

}
