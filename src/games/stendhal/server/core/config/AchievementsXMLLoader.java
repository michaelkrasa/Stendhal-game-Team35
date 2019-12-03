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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.condition.*;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.condition.LevelGreaterThanCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasKilledNumberOfCreaturesCondition;
import games.stendhal.server.entity.npc.condition.PlayerVisitedZonesCondition;
import games.stendhal.server.entity.npc.condition.PlayerVisitedZonesInRegionCondition;
import games.stendhal.server.entity.npc.condition.QuestStateGreaterThanCondition;

public final class AchievementsXMLLoader extends DefaultHandler {

	/** the logger instance. */
	private static final Logger LOGGER = Logger.getLogger(AchievementsXMLLoader.class);

	private String identifier;

	private String title;
	
	private String text = "";

	private Category category;

	private String description;

	private int baseScore;

	/** is this achievement visible? */
	private boolean active;

	private ChatCondition condition;
	
	private String constructor;

	private List<Achievement> list;
	
	private List<String> arguments;

	public List<Achievement> load(final URI uri) throws SAXException {
		list = new LinkedList<Achievement>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = AchievementsXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}

		return list;
	}

	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs)
	{
		text = "";
		
		if (qName.equals("achievement"))
		{
			identifier = attrs.getValue("identifier");
			title = "";
			category = null;
			description = "";
			baseScore = 0;
			active = false;
			condition = null;
			constructor = "";
			arguments = new LinkedList<String>();
		}else if(qName.equals("condition"))
			constructor = attrs.getValue("constructor");
		else if(qName.equals("argument"))
			arguments.add(attrs.getValue("value"));
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) 
	{
		if (qName.equals("achievement"))
			list.add(new Achievement(identifier, title, category, description, baseScore, active, condition));
		else if (qName.equals("title"))
		{
			if(text != null)
				title = text.trim();
		} else if (qName.equals("category")) 
		{
			if(text != null)
				try
				{
					category = Category.valueOf(text.trim().toUpperCase());
				}
				catch(final IllegalArgumentException e)
				{
					LOGGER.error("ERROR: COULD NOT LOAD CATEGORY OF ACHIEVEMENT - " + title + "\n\t" + e);
				}
		} else if (qName.equals("description"))
		{
			if(text != null)
				description = text.trim();
		} else if (qName.equals("basescore"))
		{
			if(text != null)
				try
				{
					baseScore = Integer.parseInt(text.trim());
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE BASESCORE AS INTEGER - " + title + "\n\t" + e);
				}
		} else if (qName.equals("active"))
		{
			if(text != null)
				if(text.trim().toLowerCase() == "yes")
					active = true;
				else
					active = false;
		} else if (qName.equals("condition"))
		{
			text = "";
			
			for(int i = 0; i < arguments.size(); i++)
				text = text + ( i >= 1 ? "," : "" ) + arguments.get(i).trim();
			
			condition = get_condition(text.trim());
		}
	}
	
	private ChatCondition get_condition(String cond)
	{
		ChatCondition c = null;
		String str_array[] = null;
		
		switch(category)
		{
			case EXPERIENCE:			
				try
				{
					if(constructor.compareTo("LevelGreaterThanCondition") == 0)
						c = new LevelGreaterThanCondition(Integer.parseInt(cond));
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE CONDITION PARAMETER AS INTEGER - " + title + "\n\t" + e);
				}
					
				return c;
				
			case FIGHTING: 				
				try
				{
					if(constructor.compareTo("PlayerHasKilledNumberOfCreaturesCondition") == 0)
					{
						int number = 0;
						str_array = cond.split(",");
						String aux[] = new String[str_array.length - 1];
						
						if(str_array.length < 2)
							throw new IllegalArgumentException("The constructor PlayerHasKilledNumberOfCreaturesCondition needs at least 2 arguments!");
						
						if(str_array.length > 2)
						{							
							number = Integer.parseInt(str_array[0].trim());
							
							for(int i = 0; i < aux.length; i++)
								aux[i] = str_array[i + 1].trim();
							
							c = new PlayerHasKilledNumberOfCreaturesCondition(number, aux);
						}else
						{
							number = Integer.parseInt(str_array[1].trim());
							
							c = new PlayerHasKilledNumberOfCreaturesCondition(str_array[0].trim(), number);
						}
					}
					else if(constructor.compareTo("KilledRareCreatureCondition") == 0)
						c = new KilledRareCreatureCondition();
					else if(constructor.compareTo("KilledSoloAllCreaturesCondition") == 0)
						c = new KilledSoloAllCreaturesCondition();
					else if(constructor.compareTo("KilledSharedAllCreaturesCondition") == 0)
						c = new KilledSharedAllCreaturesCondition();
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE CONDITION PARAMETER AS INTEGER - " + title + "\n\t" + e);
				}
				catch(final IllegalArgumentException e)
				{
					LOGGER.error("ERROR: COULD LOAD CONSTRUCTOR - " + title + "\n\t" + e);
				}
				
				return c;
				
			case QUEST: 
				try
				{	
					if(constructor.compareTo("QuestStateGreaterThanCondition") == 0)
					{
						str_array = cond.split(",");
						
						if(str_array.length != 3)
							throw new IllegalArgumentException("The constructor QuestStateGreaterThanCondition needs 3 arguments!"); 
						
						c = new QuestStateGreaterThanCondition(str_array[0].trim(), Integer.parseInt(str_array[1].trim()), Integer.parseInt(str_array[2].trim()));
					}
					else if(constructor.compareTo("QuestsInRegionCompletedCondition") == 0)
						c = new QuestsInRegionCompletedCondition(cond);
					else if(constructor.compareTo("QuestCountCompletedCondition") == 0)
						c = new QuestCountCompletedCondition(Integer.parseInt(cond));
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE CONDITION PARAMETER AS INTEGER - " + title + "\n\t" + e);
				}
				catch(final IllegalArgumentException e)
				{
					LOGGER.error("ERROR: COULD NOT LOAD CONSTRUCTOR - " + title + "\n\t" + e);
				}
				
				return c;
				
			case OUTSIDE_ZONE:
				try
				{
					str_array = cond.split(",");
					
					if(constructor.compareTo("PlayerVisitedZonesInRegionCondition") == 0)
					{
						if(str_array.length != 3)
							throw new IllegalArgumentException("The constructor PlayerVisitedZonesInRegionCondition needs exactly 3 arguments!");
						
						c = new PlayerVisitedZonesInRegionCondition(str_array[0].trim(), ( str_array[1].trim().toLowerCase().compareTo("yes") == 0 ? Boolean.TRUE : Boolean.FALSE ), ( str_array[2].trim().toLowerCase().compareTo("yes") == 0 ? Boolean.TRUE : Boolean.FALSE ));
					}
					else if(constructor.compareTo("PlayerVisitedZonesCondition") == 0)
					{						
						for(int i = 0; i < str_array.length; i++)
							str_array[i] = str_array[i].trim();
						
						c = new PlayerVisitedZonesCondition(str_array);
					}
				}
				catch(final IllegalArgumentException e)
				{
					LOGGER.error("ERROR: COULD NOT LOAD CONSTRUCTOR - " + title + "\n\t" + e);
				}
				
				return c;
				
			case UNDERGROUND_ZONE: break;
			case INTERIOR_ZONE: break;
			case AGE: break;
			case ITEM: break;
			case OBTAIN: break;
			case FRIEND: break;
			case PRODUCTION: break;
			case QUEST_ADOS_ITEMS: break;
			case QUEST_SEMOS_MONSTER: break;
			case QUEST_KIRDNEH_ITEM: break;
			case QUEST_MITHRILBOURGH_ENEMY_ARMY: break;
		}
		
		return null; 
	}

	@Override
	public void characters(final char[] buf, final int offset, final int len) {
		text = text + (new String(buf, offset, len)).trim();
	}
}
