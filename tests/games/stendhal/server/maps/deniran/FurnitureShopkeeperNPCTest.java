package games.stendhal.server.maps.deniran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class FurnitureShopkeeperNPCTest extends ZonePlayerAndNPCTestImpl{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone("0_deniran");
	}

	public FurnitureShopkeeperNPCTest() {
		setNpcNames("Ronan");
		setZoneForPlayer("0_deniran");
		addZoneConfigurator(new FurnitureShopkeeperNPC(), "0_deniran");
	}

	@Test
	public void test() {
		final SpeakerNPC npc = getNPC("Ronan");
		final Engine en = npc.getEngine();
		
		// Check if furniture shopkeeper NPC returns correct message when player says hello.
		assertTrue(en.step(player, "hi"));
		assertEquals("Hey there! I will be opening a furniture shop in Deniran soon!", getReply(npc));
		// Check if furniture shopkeeper NPC returns correct message when player says bye.
		assertTrue(en.step(player, "bye"));
		assertEquals("Bye! Come back later for all your furniture needs!", getReply(npc));
	}

}
