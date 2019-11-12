package games.stendhal.server.maps.deniran.school;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
// import correct name here :       import games.stendhal.server.maps.kalavan.citygardens.IceCreamSellerNPC;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;
/**
 * Test buying ice cream.
 *
 * @author Zhaoyu Zhang
 */
public class SchoolNPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "0_deniran_se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}
	
	public SchoolNPCTest() {
		setNpcNames("Lon Jatham");
		setZoneForPlayer(ZONE_NAME);
		// name of creating NPC method updating needed
		addZoneConfigurator((ZoneConfigurator) new SchoolNPCTest(), ZONE_NAME);
	}
	
	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("Welcome to the new school of computer science! A school about #computers and their science!", getReply(npc));
		assertTrue(en.step(player, "job"));
		assertEquals("My whole aim is to educate people about computer science, but right now I have no students signed up!", getReply(npc));
		assertTrue(en.step(player, "help"));
		assertEquals("If I could only find some students, i'd even answer any of their questions.", getReply(npc));
		assertTrue(en.step(player, "computers"));
		assertEquals("Yeah! Computers! I teach people about them.", getReply(npc));
		assertTrue(en.step(player, "bye"));
		assertEquals("Just gonna wiggle my eyebrows at you and say goodbye for now.", getReply(npc));
	}

}
