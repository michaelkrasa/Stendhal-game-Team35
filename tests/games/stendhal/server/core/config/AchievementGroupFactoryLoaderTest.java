package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPObject;

/**
 * @author Michael Krasa
 *
 */
public class AchievementGroupFactoryLoaderTest {
	
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	/**
	 * Test method for loading XML achievement groups by opening "testachievements.xml" and then checking whether
	 * achievements in the list are being parsed correctly.
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testLoad() throws SAXException, IOException {
		AchievementGroupFactoryLoader loader = new AchievementGroupFactoryLoader("testachievements.xml");
		List<Achievement> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.FALSE));
		Achievement a = list.get(0);
		
		Player p = new Player(new RPObject());
		
		assertThat(a.getBaseScore(), is(1));
		assertThat(a.getCategory(), is(Category.EXPERIENCE));
		assertThat(a.getClass().getName(), is("games.stendhal.server.core.rp.achievement.Achievement"));
		assertThat(a.getDescription(), is("Reach level 10"));
		assertThat(a.getIdentifier(), is("xp.level.010"));
		assertThat(a.getTitle(), is("Greehorn"));
		assertThat(a.isActive(), is(Boolean.TRUE));
		assertThat(a.isFulfilled(p), is(Boolean.FALSE));
		
		a = list.get(1);
		assertThat(a.getBaseScore(), is(1));
		assertThat(a.getCategory(), is(Category.FIGHTING));
		assertThat(a.getClass().getName(), is("games.stendhal.server.core.rp.achievement.Achievement"));
		assertThat(a.getDescription(), is("Kill 25 deer"));
		assertThat(a.getIdentifier(), is("fight.general.deer"));
		assertThat(a.getTitle(), is("Deer Hunter"));
		assertThat(a.isActive(), is(Boolean.TRUE));
		assertThat(a.isFulfilled(p), is(Boolean.FALSE));
		}
}
