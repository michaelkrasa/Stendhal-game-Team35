package games.stendhal.server.entity.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.HousePortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.quests.houses.HouseUtilities;
import utilities.PlayerTestHelper;

public class FurnitureTest{
	
	//Implementing global variables to store the 2 zones we need and the player's house portal
	private static StendhalRPZone zone = null;
	private static StendhalRPZone house_zone = null;
	private static HousePortal portal = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Loading all the classes that are needed
		PlayerTestHelper.generateNPCRPClasses();
		Portal.generateRPClass();
		HousePortal.generateRPClass();
		MockStendlRPWorld.get();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//Clearing the loaded stuff
		MockStendhalRPRuleProcessor.get().clearPlayers();
		HouseUtilities.clearCache();
	}
	
	@Before
	public void setUp() {
		//Create the city area and loading it into the world
		String zone_name = "0_kalavan_city";
		StendhalRPZone ados1 = new StendhalRPZone(zone_name);
		MockStendlRPWorld.get().addRPZone(ados1);
		zone = ados1;
		assertNotNull(zone);
		
		//Creating the house area and loading it into the world
		String house_zone_name = "int_kalavan_house_7";
		StendhalRPZone ados2 = new StendhalRPZone(house_zone_name);
		MockStendlRPWorld.get().addRPZone(ados2);
		house_zone = ados2;
		assertNotNull(house_zone);
		
		//Creating a portal to the house and adding it to the world
		portal = new HousePortal("test_house");
		portal.setDestination(house_zone_name, "test_house");
		portal.setIdentifier("keep rpzone happy");
		ados1.add(portal);
		assertNotNull(portal);
		
		//Clearing the House utilities cached data
		HouseUtilities.clearCache();
	}

	@Test
	public void testFurniturePlacementLogic() throws Exception {				
		//Create a player
		Player player = PlayerTestHelper.createPlayer("test");
		
		//Set the player's zone
		PlayerTestHelper.registerPlayer(player, zone);
		
		//Creating the map for the item attributes, together with
		//the name, clazz and subclass
		Map<String, String> attributes = new HashMap<String, String>();
		String name = "chair";
		String clazz = "furniture";
		String subclass = "chair";
		Furniture fr = new Furniture(name, clazz, subclass, attributes);
		
		//Test the degrade state of the furniture dropped outside a house
		player.equip("bag", fr);
		player.drop(fr);
		fr.onPutOnGround(player);
		assertEquals(fr.get_furniture_degrade_state(player), true);			
		
		//Set the house's owner as the player
		portal.setOwner(player.getName());
		
		//Remove the player from the previous zone
		PlayerTestHelper.unregisterPlayer(player, zone);
		
		//Set the player's zone
		PlayerTestHelper.registerPlayer(player, house_zone);
		
		//Test the degrade state of the furniture dropped outside a house
		player.equip("bag", fr);
		player.drop(fr);
		fr.onPutOnGround(player);
		assertEquals(fr.get_furniture_degrade_state(player), false);
	}
}
