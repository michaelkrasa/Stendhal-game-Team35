package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.behavior.MessagingUseBehavior;
import games.stendhal.server.entity.item.behavior.UseBehavior;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;


public class SleepingBagTest {
	
	private Player player = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		//set up world etc
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		
	}
	
	@Before
	public void setUp() {
		//put player in a zone
		final StendhalRPZone zone = new StendhalRPZone("0_semos_plains_n");
		MockStendlRPWorld.get().addRPZone(zone);
		player = PlayerTestHelper.createPlayer("player");
	}

	@Test
	public void testPlayerHasBag() {
		
		//give the player a sleeping bag and equip it
		final Item sleepingBag = ItemTestHelper.createItem("Sleeping Bag");
		sleepingBag.setEquipableSlots(Arrays.asList("bag"));
		player.equipToInventoryOnly(sleepingBag);
		
		//checks player has a sleeping bag
		assertTrue(player.isEquipped("Sleeping Bag"));
		
		//checks sleeping bag description is correct
		assertEquals(sleepingBag.describe(), "You see a sleeping bag.");
	}
	
	@Test
	public void testPlayerUsesBag() {
		
		//give player a sleeping bag
		final Item sleepingBag = ItemTestHelper.createItem("Sleeping Bag");
		sleepingBag.setEquipableSlots(Arrays.asList("bag"));
		player.equipToInventoryOnly(sleepingBag);
		
		//check that the player isnt currently being used
		
		assertFalse(sleepingBag.onUsed(player));
		
		//instantiates bag behaviour
		Map<String,String> params = new HashMap<String,String>();
		params.put("private", "!me sleeps in bed");
		params.put("public", "The bed feels soft and warm");
		UseBehavior b = new MessagingUseBehavior(params);
		b.use(player,  sleepingBag);
		sleepingBag.setUseBehavior(b);
		
		//checks bag can be used
		assertTrue(sleepingBag.onUsed(player));
		
		//checks bag is still equipped after
		assertTrue(player.isEquipped("Sleeping Bag"));
	}
	
	
	//test the players health regenerates when they sleep
	@Test
	public void testHealthRegen() {
		//give player a sleeping bag
		final Item sleepingBag = ItemTestHelper.createItem("Sleeping Bag");
		sleepingBag.setEquipableSlots(Arrays.asList("bag"));
		player.equipToInventoryOnly(sleepingBag);
		
		//set the players health to be 40
		int playerHealth = 40;
		player.put("hp", playerHealth);
		
		//sleep --------- check this -------------
		sleepingBag.onUsed(player);
		
		//check health has grown
		assertNotEquals(player.get("hp"), playerHealth);
	}

}
