package games.stendhal.server.core.rp.achievement;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.server.core.config.AchievementsXMLLoader;
import games.stendhal.server.core.rp.achievement.factory.AbstractAchievementFactory;

public class AchievementTest {

	@Test
	public void testAchievementsExistJava() {
		List<AbstractAchievementFactory> listOfAchievementFactories = AbstractAchievementFactory.createFactories();
		
		Collection<Achievement> listOfAchievements = listOfAchievementFactories.get(0).createAchievements();
		
		for (int factoryIndex = 1; factoryIndex < listOfAchievementFactories.size(); factoryIndex ++) {
			Collection<Achievement> tempListOfAchievements = listOfAchievementFactories.get(factoryIndex).createAchievements();
			for (Achievement achievement :  tempListOfAchievements) {
				listOfAchievements.add(achievement);
			}
		}
		Iterator<Achievement> achievementIterator = listOfAchievements.iterator();
		assertEquals(achievementIterator.next().getTitle(), "Ados' Supporter");
		assertEquals(achievementIterator.next().getTitle(), "Ados' Provider");
		assertEquals(achievementIterator.next().getTitle(), "Ados' Supplier");
		assertEquals(achievementIterator.next().getTitle(), "Ados' Stockpiler");
		assertEquals(achievementIterator.next().getTitle(), "Ados' Hoarder");
		assertEquals(achievementIterator.next().getTitle(), "Greenhorn");
		assertEquals(achievementIterator.next().getTitle(), "Novice");
		assertEquals(achievementIterator.next().getTitle(), "Apprentice");
		assertEquals(achievementIterator.next().getTitle(), "Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Experienced Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Master Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Stendhal Master");
		assertEquals(achievementIterator.next().getTitle(), "Stendhal High Master");
		assertEquals(achievementIterator.next().getTitle(), "Rat Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Exterminator");
		assertEquals(achievementIterator.next().getTitle(), "Deer Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Boar Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Bear Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Fox Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Safari");
		assertEquals(achievementIterator.next().getTitle(), "Wood Cutter");
		assertEquals(achievementIterator.next().getTitle(), "Poacher");
		assertEquals(achievementIterator.next().getTitle(), "Legend");
		assertEquals(achievementIterator.next().getTitle(), "Team Player");
		assertEquals(achievementIterator.next().getTitle(), "Childrens' friend");
		assertEquals(achievementIterator.next().getTitle(), "Private Detective");
		assertEquals(achievementIterator.next().getTitle(), "Good Samaritan");
		assertEquals(achievementIterator.next().getTitle(), "Still Believing");
		assertEquals(achievementIterator.next().getTitle(), "Home maker");
		assertEquals(achievementIterator.next().getTitle(), "Elf visitor");
		assertEquals(achievementIterator.next().getTitle(), "Up town guy");
		assertEquals(achievementIterator.next().getTitle(), "Kobold City");
		assertEquals(achievementIterator.next().getTitle(), "Magic City");
		assertEquals(achievementIterator.next().getTitle(), "First pocket money");
		assertEquals(achievementIterator.next().getTitle(), "You don't need it anymore");
		assertEquals(achievementIterator.next().getTitle(), "Amazon's Menace");
		assertEquals(achievementIterator.next().getTitle(), "Feeling Blue");
		assertEquals(achievementIterator.next().getTitle(), "Nalwor's Bane");
		assertEquals(achievementIterator.next().getTitle(), "Shadow Dweller");
		assertEquals(achievementIterator.next().getTitle(), "Chaotic Looter");
		assertEquals(achievementIterator.next().getTitle(), "Golden Boy");
		assertEquals(achievementIterator.next().getTitle(), "Come to the dark side");
		assertEquals(achievementIterator.next().getTitle(), "Excellent Stuff");
		assertEquals(achievementIterator.next().getTitle(), "A Bit Xenophobic?");
		assertEquals(achievementIterator.next().getTitle(), "Dragon Slayer");
		assertEquals(achievementIterator.next().getTitle(), "A wish came true");
		assertEquals(achievementIterator.next().getTitle(), "Farmer");
		assertEquals(achievementIterator.next().getTitle(), "Fisherman");
		assertEquals(achievementIterator.next().getTitle(), "Ultimate Collector");
		assertEquals(achievementIterator.next().getTitle(), "Junior Explorer");
		assertEquals(achievementIterator.next().getTitle(), "Big City Explorer");
		assertEquals(achievementIterator.next().getTitle(), "Far South");
		assertEquals(achievementIterator.next().getTitle(), "Scout");
		assertEquals(achievementIterator.next().getTitle(), "Jungle Explorer");
		assertEquals(achievementIterator.next().getTitle(), "Tourist");
		assertEquals(achievementIterator.next().getTitle(), "Sky Tower");
		assertEquals(achievementIterator.next().getTitle(), "Safe Deposit");
		assertEquals(achievementIterator.next().getTitle(), "Gourmet");
		assertEquals(achievementIterator.next().getTitle(), "Thirsty Worker");
		assertEquals(achievementIterator.next().getTitle(), "Alchemist");
		assertEquals(achievementIterator.next().getTitle(), "Jenny's Assistant");
		assertEquals(achievementIterator.next().getTitle(), "Faiumoni's Casanova");
		assertEquals(achievementIterator.next().getTitle(), "Heretic");
		assertEquals(achievementIterator.next().getTitle(), "Pathfinder");
		assertEquals(achievementIterator.next().getTitle(), "Deathmatch Hero");
		assertEquals(achievementIterator.next().getTitle(), "Aide to Semos folk");
		assertEquals(achievementIterator.next().getTitle(), "Helper of Ados city dwellers");
		assertEquals(achievementIterator.next().getTitle(), "Quest Junkie");
		assertEquals(achievementIterator.next().getTitle(), "Semos' Protector");
		assertEquals(achievementIterator.next().getTitle(), "Semos' Guardian");
		assertEquals(achievementIterator.next().getTitle(), "Semos' Hero");
		assertEquals(achievementIterator.next().getTitle(), "Semos' Champion");
		assertEquals(achievementIterator.next().getTitle(), "Semos' Vanquisher");
		assertEquals(achievementIterator.next().getTitle(), "Canary");
		assertEquals(achievementIterator.next().getTitle(), "Fear not drows nor hell");
		assertEquals(achievementIterator.next().getTitle(), "Labyrinth Solver");
		assertEquals(achievementIterator.next().getTitle(), "Human Mole");
		assertEquals(achievementIterator.next().getTitle(), "Deep Dweller");
		assertEquals(achievementIterator.next().getTitle(), "Archaeologist");
		assertEquals(achievementIterator.next().getTitle(), "Dedicated Archaeologist");
		assertEquals(achievementIterator.next().getTitle(), "Senior Archaeologist");
		assertEquals(achievementIterator.next().getTitle(), "Master Archaeologist");
		assertEquals(achievementIterator.next().getTitle(), "Sergeant");
		assertEquals(achievementIterator.next().getTitle(), "Major");
		assertEquals(achievementIterator.next().getTitle(), "Major General");
		assertEquals(achievementIterator.next().getTitle(), "Field Marshal");
	}
	
	@Test
	public void testAchievementsExistXML() throws SAXException, URISyntaxException {
		URI uri = new URI("/data/conf/achievements.xml");
		AchievementsXMLLoader achievementsXMLLoader = new AchievementsXMLLoader();
		List<Achievement> listOfAchievements = achievementsXMLLoader.load(uri);
		Iterator<Achievement> achievementIterator = listOfAchievements.iterator();
		
		//less files as not all are implemented in xml
		assertEquals(achievementIterator.next().getTitle(), "Greenhorn");
		assertEquals(achievementIterator.next().getTitle(), "Novice");
		assertEquals(achievementIterator.next().getTitle(), "Apprentice");
		assertEquals(achievementIterator.next().getTitle(), "Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Experienced Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Master Adventurer");
		assertEquals(achievementIterator.next().getTitle(), "Stendhal Master");
		assertEquals(achievementIterator.next().getTitle(), "Stendhal High Master");
		assertEquals(achievementIterator.next().getTitle(), "Rat Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Exterminator");
		assertEquals(achievementIterator.next().getTitle(), "Deer Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Boar Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Bear Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Fox Hunter");
		assertEquals(achievementIterator.next().getTitle(), "Safari");
		assertEquals(achievementIterator.next().getTitle(), "Wood Cutter");
		assertEquals(achievementIterator.next().getTitle(), "Poacher");
		assertEquals(achievementIterator.next().getTitle(), "Legend");
		assertEquals(achievementIterator.next().getTitle(), "Team Player");
		assertEquals(achievementIterator.next().getTitle(), "Faiumoni's Casanova");
		assertEquals(achievementIterator.next().getTitle(), "Heretic");
		assertEquals(achievementIterator.next().getTitle(), "Pathfinder");
		assertEquals(achievementIterator.next().getTitle(), "Deathmatch Hero");
		assertEquals(achievementIterator.next().getTitle(), "Aide to Semos folk");
		assertEquals(achievementIterator.next().getTitle(), "Helper of Ados city dwellers");
		assertEquals(achievementIterator.next().getTitle(), "Quest Junkie");
	}
}
 