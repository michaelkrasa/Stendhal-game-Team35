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
 * Test Student Declan. Cool name.
 *
 * @author Declan Kehoe
 */
public class Student1NPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "0_deniran_se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public Student1NPCTest() {
		setNpcNames("Declan");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Student1NPC(), ZONE_NAME);
	}
	
	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Declan");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("I just wish I could learn about #computers and the ONES, and the ZEROES, but where could I learn something like that?!", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("I just don't feel ready for a job right now, maybe once I'm better educated?", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("Honestly, right now I'd appreciate some help myself!", getReply(npc));
		
		assertTrue(en.step(player, "computers"));
		assertEquals("Now they sound cool, I wish I understood the difference between python, monty python, and a snake.", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("See you l8r m8", getReply(npc));
	}

}
