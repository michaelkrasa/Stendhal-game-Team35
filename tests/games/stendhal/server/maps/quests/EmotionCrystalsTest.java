package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class EmotionCrystalsTest {

	private final Player player = PlayerTestHelper.createPlayer("player");
	private SpeakerNPC npc;
	private Engine en;
	private AbstractQuest quest;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		QuestHelper.setUpBeforeClass();
		assertTrue(SingletonRepository.getNPCList().getNPCs().isEmpty());
	}
	
	@Before
	public void setUp()
	{
		npc = SingletonRepository.getNPCList().get("Julius");
		en = npc.getEngine();

		quest = new EmotionCrystals();
		quest.addToWorld();
	}

	@Test
	public void testGetHistory() 
	{
		en.step(player, "hi");
		en.step(player, "quest");
		en.step(player, "yes");
		
		final Item red_crystal = ItemTestHelper.createItem("red emotion crystal", 1);
		final Item blue_crystal = ItemTestHelper.createItem("blue emotion crystal", 1);
		final Item yellow_crystal = ItemTestHelper.createItem("yellow emotion crystal", 1);
		
		player.getSlot("bag").add(red_crystal);
		player.getSlot("bag").add(blue_crystal);
				
		final List<String> history = new ArrayList<String>();
		
		history.add("I have talked to Julius, the soldier that guards the entrance to Ados.");
		history.add("I promised to gather five crystals from all across Faiumoni.");
		history.add("I have found the following crystals: red emotion crystal and blue emotion crystal");
		assertEquals(history, quest.getHistory(player));
		
		player.drop(blue_crystal);
		player.getSlot("bag").add(yellow_crystal);
		
		history.add("I have talked to Julius, the soldier that guards the entrance to Ados.");
		history.add("I promised to gather five crystals from all across Faiumoni.");
		history.add("I have found the following crystals: red emotion crystal, blue emotion crystal, and yellow emotion crystal");
		assertEquals(history, quest.getHistory(player));
	}
}
