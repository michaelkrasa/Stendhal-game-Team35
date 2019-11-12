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
 * Test Student Zhaoyu.
 *
 * @author Declan Kehoe
 */
public class Student2NPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "0_deniran_se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public Student2NPCTest() {
		setNpcNames("Zhaoyu");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new Student2NPC(), ZONE_NAME);
	}
	
	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Zhaoyu");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("I just wish I could learn about #computers but who knows what they even are?!", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("Are you offering ME a job? Otherwise, can't help, sorry.", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("Maybe if you found a computer science professor I could learn. But that just might be a big hint.", getReply(npc));
		
		assertTrue(en.step(player, "computers"));
		assertEquals("Now they sound cool, I wish I understood recursive programming.", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("See you!", getReply(npc));
	}

}
