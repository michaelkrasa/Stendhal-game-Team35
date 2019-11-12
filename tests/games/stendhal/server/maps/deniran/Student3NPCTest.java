package games.stendhal.server.maps.deniran;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;
/**
 * Test Student Alexandru.
 *
 * @author Declan Kehoe
 */
public class Student3NPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "0_deniran_se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public Student3NPCTest() {
		setNpcNames("Alexandru");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Student3NPC(), ZONE_NAME);
	}
	
	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Alexandru");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("I just wish I could learn about #computers but people don't even believe they're real?!", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("A job would be great, but I'm looking to learn first.", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("I bet this city is the perfect place for a computer science profesor.", getReply(npc));
		
		assertTrue(en.step(player, "computers"));
		assertEquals("Now they sound cool, I wish I could ask someone what types of variables there are. I'm fun like that.", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("Yeah, computers!", getReply(npc));
	}

}
