package games.stendhal.server.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.rp.RPWorld;
import utilities.RPClass.PetTestHelper;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.BabyDragon;
import games.stendhal.server.entity.creature.Cat;
import games.stendhal.server.entity.creature.PurpleDragon;
import games.stendhal.server.entity.item.scroll.BlankPetScroll;
import games.stendhal.server.entity.item.scroll.SummonPetScroll;



public class SummonPetScrollsTest {
	
	@Test
	public void testSummonPurple() {
		
		RPWorld testWorld = MockStendlRPWorld.get();
		StendhalRPZone testZone = new StendhalRPZone("testZone");
		
		testWorld.addRPZone(testZone);
		Player testPlayer = utilities.PlayerTestHelper.createPlayer("testPlayer");
		utilities.PlayerTestHelper.registerPlayer(testPlayer, testZone);
		
		PetTestHelper.generateRPClasses();
		PurpleDragon testDragon = new PurpleDragon(testPlayer);
		testDragon.setName("testDragon");
		
		

		
		BlankPetScroll testBlankScroll = new BlankPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		
		
		testDragon.setWeight(99);
		int initialWeight = testDragon.getWeight();
		
		testDragon.setXP(80);
		int initialXP = testDragon.getXP();
		
		String initialTestDragonName = testDragon.getName();
		
		
		testDragon.setAtk(80);
		int initialAttack = testDragon.getAtk();
		
		testDragon.setDef(80);
		int initialDefence = testDragon.getDef();

		testBlankScroll.useScroll(testPlayer);
		
		SummonPetScroll testSummonScroll = new SummonPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		testSummonScroll.useScroll(testPlayer);
		
		int newWeight = testDragon.getWeight();
		int newXP = testDragon.getXP();
		String newTestDragonName = testDragon.getName();
		int newAttack = testDragon.getAtk();
		int newDefence = testDragon.getDef();
		
		//tests for Purple dragon
		assertEquals(initialWeight, newWeight);
		assertEquals(initialXP, newXP);
		assertEquals(initialTestDragonName, newTestDragonName);
		assertEquals(initialAttack, newAttack);
		assertEquals(initialDefence, newDefence);
		
		
	
	}
	
	@Test
	public void testSummonBaby() {
		
		RPWorld testWorld = MockStendlRPWorld.get();
		StendhalRPZone testZone = new StendhalRPZone("testZone");
		
		testWorld.addRPZone(testZone);
		Player testPlayer = utilities.PlayerTestHelper.createPlayer("testPlayer");
		utilities.PlayerTestHelper.registerPlayer(testPlayer, testZone);
		
		PetTestHelper.generateRPClasses();
		BabyDragon testDragon = new BabyDragon(testPlayer);
		testDragon.setName("testDragon");
		
		

		
		BlankPetScroll testBlankScroll = new BlankPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		
		
		testDragon.setWeight(99);
		int initialWeight = testDragon.getWeight();
		
		testDragon.setXP(80);
		int initialXP = testDragon.getXP();
		
		String initialTestDragonName = testDragon.getName();
		
		
		testDragon.setAtk(80);
		int initialAttack = testDragon.getAtk();
		
		testDragon.setDef(80);
		int initialDefence = testDragon.getDef();

		testBlankScroll.useScroll(testPlayer);
		
		SummonPetScroll testSummonScroll = new SummonPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		testSummonScroll.useScroll(testPlayer);
		
		int newWeight = testDragon.getWeight();
		int newXP = testDragon.getXP();
		String newTestDragonName = testDragon.getName();
		int newAttack = testDragon.getAtk();
		int newDefence = testDragon.getDef();
		
		//tests for baby dragon
		assertEquals(initialWeight, newWeight);
		assertEquals(initialXP, newXP);
		assertEquals(initialTestDragonName, newTestDragonName);
		assertEquals(initialAttack, newAttack);
		assertEquals(initialDefence, newDefence);
		
		
	
	}
	
	@Test
	public void testSummonCat() {
		
		RPWorld testWorld = MockStendlRPWorld.get();
		StendhalRPZone testZone = new StendhalRPZone("testZone");
		
		testWorld.addRPZone(testZone);
		Player testPlayer = utilities.PlayerTestHelper.createPlayer("testPlayer");
		utilities.PlayerTestHelper.registerPlayer(testPlayer, testZone);
		
		PetTestHelper.generateRPClasses();
		Cat testCat = new Cat(testPlayer);
		testCat.setName("testCat");
		
		

		
		BlankPetScroll testBlankScroll = new BlankPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		
		
		testCat.setWeight(99);
		int initialWeight = testCat.getWeight();
		
		testCat.setXP(80);
		int initialXP = testCat.getXP();
		
		String initialTestCatName = testCat.getName();
		
		
		testCat.setAtk(80);
		int initialAttack = testCat.getAtk();
		
		testCat.setDef(80);
		int initialDefence = testCat.getDef();

		testBlankScroll.useScroll(testPlayer);
		
		SummonPetScroll testSummonScroll = new SummonPetScroll("test string", "test string", "test string",
				new HashMap<String,String>());
		testSummonScroll.useScroll(testPlayer);
		
		int newWeight = testCat.getWeight();
		int newXP = testCat.getXP();
		String newTestCatName = testCat.getName();
		int newAttack = testCat.getAtk();
		int newDefence = testCat.getDef();
		
		//tests for cat
		assertEquals(initialWeight, newWeight);
		assertEquals(initialXP, newXP);
		assertEquals(initialTestCatName, newTestCatName);
		assertEquals(initialAttack, newAttack);
		assertEquals(initialDefence, newDefence);	
	
	}

}
