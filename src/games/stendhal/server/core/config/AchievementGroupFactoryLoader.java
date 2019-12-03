package games.stendhal.server.core.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rp.achievement.*;

/**
 * Loads a group of achievements from an xml file.
 */

public class AchievementGroupFactoryLoader extends DefaultHandler
{
	private static final Logger logger = Logger.getLogger(AchievementGroupFactoryLoader.class);

	protected URI uri;

	protected LinkedList<URI> groups;

	public AchievementGroupFactoryLoader(final String uri) {
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			logger.error(e, e);
		}
	}

	public List<Achievement> load() throws SAXException, IOException {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);
		final List<Achievement> list = new LinkedList<Achievement>();
		
		
		try {
			List<URI> groups = groupsLoader.load();

			// Load each group
			for (final URI tempUri : groups) {
				final AchievementsXMLLoader loader = new AchievementsXMLLoader();

				try {
					list.addAll(loader.load(tempUri));
				} catch (final SAXException ex) {
					logger.error("Error loading creature group: " + tempUri, ex);
				}
			}
		} catch (SAXException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}

		return list;
	}
}
