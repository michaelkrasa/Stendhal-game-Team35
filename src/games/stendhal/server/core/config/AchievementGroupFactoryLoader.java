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
	// An instance of logic reader to print any possible errors
	private static final Logger logger = Logger.getLogger(AchievementGroupFactoryLoader.class);

	// The uri of the xml files
	protected URI uri;

	// A group of uris
	protected LinkedList<URI> groups;

	/*
	 *  Load the uri into the achievement group loader, 
	 *  while catching any possible errors
	 */
	public AchievementGroupFactoryLoader(final String uri) {
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			logger.error(e, e);
		}
	}

	/*
	 *  As soon as we have the uri of the xml files, we insert them
	 *  into the loader and create a list of uris in order to load
	 *  separately for each existing achievement group
	 */
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
					/*
					 * catch all errors if there uri that cannot be load into the loader
					 */
					logger.error("Error loading creature group: " + tempUri, ex);
				}
			}
		} catch (SAXException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}

		/*
		 *  return the list of uris
		 */
		return list;
	}
}
