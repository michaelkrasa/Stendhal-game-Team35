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
 * Test Lon Jatham.
 *
 * @author Zhaoyu Zhang
 * @author Declan Kehoe
 */
public class ComputerScienceProfessorNPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static final String ZONE_NAME = "0_deniran_se";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public ComputerScienceProfessorNPCTest() {
		setNpcNames("Lon Jatham");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new ComputerScienceProfessorNPC(), ZONE_NAME);
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
		assertEquals("Welcome to the new school of computer science! A school, about #computers and their science!", getReply(npc));
		
		assertTrue(en.step(player, "job"));
		assertEquals("My whole aim is to educate people about computer science, but right now I have no students signed up!", getReply(npc));
		
		assertTrue(en.step(player, "help"));
		assertEquals("If I could only find some students, I'd even answer any of their questions.", getReply(npc));
		
		assertTrue(en.step(player, "computers"));
		assertEquals("Yeah! Computers! I teach people about them.", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("Just gonna waggle my eyebrows at you, and say goodbye for now.", getReply(npc));
	}

}
