package games.stendhal.server.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
//import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Loads a group of achievements from an xml file.
 */

class AchievementGroupFactoryLoader extends DefaultHandler
{
	private static final Logger logger = Logger.getLogger(AchievementGroupFactoryLoader.class);

	protected URI uri;

	protected LinkedList<URI> groups;

	public AchievementGroupFactoryLoader(final URI uri) {
		this.uri = uri;
	}

	public List<URI> load() throws SAXException, IOException {
		final InputStream in = getClass().getResourceAsStream(uri.getPath());

		if (in == null) {
			throw new FileNotFoundException("Cannot find resource: "
					+ uri.getPath());
		}

		try {
			return load(in);
		} finally {
			in.close();
		}
	}

	protected List<URI> load(final InputStream in) throws SAXException, IOException {
		SAXParser saxParser;

		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			saxParser = factory.newSAXParser();
		} catch (final ParserConfigurationException ex) {
			throw new SAXException(ex);
		}

		// Parse the XML
		groups = new LinkedList<URI>();
		saxParser.parse(in, this);
		return groups;
	}
}
