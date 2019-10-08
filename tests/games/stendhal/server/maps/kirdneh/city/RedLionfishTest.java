package games.stendhal.server.maps.kirdneh.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class RedLionfishTest extends ZonePlayerAndNPCTestImpl{

	private static final String ZONE_NAME = "int_ados_felinas_house";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public RedLionfishTest() {
		setNpcNames("Fishmonger");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new KirdnehFishyMarketNPC(), ZONE_NAME);
	}

	@Test
	public void test() {
		final SpeakerNPC npc = getNPC("Fishmonger");
		final Engine en = npc.getEngine();

		// check for buying red lionfish
		assertTrue(en.step(player, "hi"));
		assertEquals("Ahoy, me hearty! Back from yer swashbucklin, ah see.", getReply(npc));

		// The red lionfish is accessible to sell but the player does not have any,
		// so the fishmonger will response that the player don't have any red lionfish.
		assertTrue(en.step(player, "sell red lionfish"));
		assertEquals("A red lionfish is worth 120. Do you want to sell it?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry! You don't have any red lionfish.", getReply(npc));
	}

}
