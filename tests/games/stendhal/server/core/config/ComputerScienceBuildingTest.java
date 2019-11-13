package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.IRPZone;
import marauroa.server.game.db.DatabaseFactory;

/**
 * @author Zhaoyu Zhang
 *
 */

public class ComputerScienceBuildingTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockStendlRPWorld.get();
		new DatabaseFactory().initializeDatabase();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test method for {@link games.stendhal.server.core.config.ZonesXMLLoader#readZone(org.w3c.dom.Element)}.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void testReadZone() throws URISyntaxException, SAXException, IOException {
		ZonesXMLLoader loader = new ZonesXMLLoader(new URI("/data/conf/zones/deniran.xml"));
		loader.load();
		StendhalRPWorld world = SingletonRepository.getRPWorld();
		
		Collection<StendhalRPZone> regionDeniranInteriors = world.getAllZonesFromRegion("deniran", Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
		IRPZone rpZone = world.getRPZone("int_deniran_school_of_computer_science");
		assertThat(regionDeniranInteriors.contains(rpZone), is(Boolean.TRUE));
	}
	
}
