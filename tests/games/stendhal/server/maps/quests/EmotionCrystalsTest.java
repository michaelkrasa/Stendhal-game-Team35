package games.stendhal.server.maps.quests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.ados.city.ManWithHatNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.SpeakerNPCTestHelper;
import utilities.ZonePlayerAndNPCTestImpl;
import utilities.RPClass.ItemTestHelper;

public class EmotionCrystalsTest extends ZonePlayerAndNPCTestImpl 
{
	private SpeakerNPC npc;
	private AbstractQuest quest;
	private static final String CITY_ZONE_NAME = "0_ados_wall_n";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		QuestHelper.setUpBeforeClass();
		assertTrue(SingletonRepository.getNPCList().getNPCs().isEmpty());
		
		setupZone(CITY_ZONE_NAME);
	}
	
	@Override
	@After
	public void tearDown() throws Exception 
	{
		SingletonRepository.getNPCList().clear();
	}
	
	@Override
	@Before
	public void setUp()
	{		
		final SpeakerNPC red_crystal_npc = SpeakerNPCTestHelper.createSpeakerNPC("Red Crystal");
		final SpeakerNPC blue_crystal_npc = SpeakerNPCTestHelper.createSpeakerNPC("Blue Crystal");
		final SpeakerNPC yellow_crystal_npc = SpeakerNPCTestHelper.createSpeakerNPC("Yellow Crystal");
		final SpeakerNPC purple_crystal_npc = SpeakerNPCTestHelper.createSpeakerNPC("Purple Crystal");
		final SpeakerNPC pink_crystal_npc = SpeakerNPCTestHelper.createSpeakerNPC("Pink Crystal");
		
		NPCList.get().add(red_crystal_npc);
		NPCList.get().add(blue_crystal_npc);
		NPCList.get().add(yellow_crystal_npc);
		NPCList.get().add(purple_crystal_npc);
		NPCList.get().add(pink_crystal_npc);
		
		npc = SpeakerNPCTestHelper.createSpeakerNPC("Julius");
		NPCList.get().add(npc);
		npc = SingletonRepository.getNPCList().get("Julius");
		quest = new EmotionCrystals();
		quest.addToWorld();
	}

	@Test
	public void testGetHistory() 
	{
		final Player player = PlayerTestHelper.createPlayer("player");
		setZoneForPlayer(CITY_ZONE_NAME);
		addZoneConfigurator(new ManWithHatNPC(), CITY_ZONE_NAME);
		
		player.setQuest("emotion_crystals", "start");
		
		final List<String> history = new ArrayList<String>();
		
		history.add("I have talked to Julius, the soldier that guards the entrance to Ados.");
		history.add("I promised to gather five crystals from all across Faiumoni.");
		assertEquals(history, quest.getHistory(player));

		final Item red_crystal = ItemTestHelper.createItem("red emotion crystal", 1);
		final Item blue_crystal = ItemTestHelper.createItem("blue emotion crystal", 1);
		final Item yellow_crystal = ItemTestHelper.createItem("yellow emotion crystal", 1);
		
		player.getSlot("bag").add(red_crystal);
		player.getSlot("bag").add(blue_crystal);
		
		history.add("I have found the following crystals: red emotion crystal and blue emotion crystal");
		assertEquals(history, quest.getHistory(player));
		
		history.remove(2);
		player.drop(blue_crystal);
		player.getSlot("bag").add(yellow_crystal);
		
		history.add("I have found the following crystals: red emotion crystal, blue emotion crystal, and yellow emotion crystal");
		assertEquals(history, quest.getHistory(player));
	}
}
