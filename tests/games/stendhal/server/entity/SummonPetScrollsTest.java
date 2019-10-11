package games.stendhal.server.entity;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;
//import games.stendhal.client.World;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.creature.PurpleDragon;
import games.stendhal.server.entity.item.scroll.BlankPetScroll;
import games.stendhal.server.entity.item.scroll.SummonPetScroll;
import marauroa.common.game.RPObject;


public class SummonPetScrollsTest {
	
	@Test
	public void testSummon() {
		
		//World testWorld = new World();
		final Player testPlayer = new Player(new RPObject());
		PurpleDragon testDragon = new PurpleDragon(testPlayer);
		
		BlankPetScroll testBlankScroll = new BlankPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		
		
		testDragon.setWeight(12345);
		String initialDescribe = testDragon.describe();
		
		testBlankScroll.useScroll(testPlayer);
		
		SummonPetScroll testSummonScroll = new SummonPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		testSummonScroll.dropBlank(testPlayer);
		
		String newDescribe = testDragon.describe();

		
		assertEquals("checks that weights etc will be the same",initialDescribe, newDescribe);
	
	}

}
