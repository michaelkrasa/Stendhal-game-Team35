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
	/** the unique identifier of the achievement */
	private String identifier;
	/**title of the achievement */
	private String title;
	/** a string which will take the text between the 
	 * beginning and the end of an element */
	private String text = "";
	/** category of the achievement */
	private Category category;
	/** description of the achievement? */
	private String description;
	/** the score a player receives for completing the
	 *  achievement? */
	private int baseScore;
	/** is this achievement active? */
	private boolean active;
	/** the condition to complete the achievement */
	private ChatCondition condition;
	/** a string to store the condition constructor
	 *  used to decide the condition of the achievement */
	private String constructor;
	/** the list of achievements */
	private List<Achievement> list;
	/** the arguments list of the condition */
	private List<String> arguments;

	//The load function which will create a new list of achievements
	//of a given xml file(uri)
	/**
	 * 		The parser will call the start and end element functions
	 * 		multiple times until it hits the end of the xml.
	 * 
	 * 		Along the way it will use the characters() function
	 * 		to detect the text between the beginning and ending of
	 * 		an element.
	 * */
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
	/**
	 * 		This function gets called when the beginning of an xml element
	 * 		has been detected
	 * 
	 * 		The characters() function which constructs the text
	 * 		gets called several times between the end of the startElement()
	 * 		and the beginning of the endElement().
	 * */
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs)
	{
		//empty the text for this line of the file
		text = "";
		
		//check if the beginning element is "achievement"
		if (qName.equals("achievement"))
		{
			//Set all achievement elements to empty (only get the 
			//unique identifier which is within this element)
			identifier = attrs.getValue("identifier");
			title = "";
			category = null;
			description = "";
			baseScore = 0;
			active = false;
			condition = null;
			constructor = "";
			arguments = new LinkedList<String>();
		//Get the constructor required to detect the condition
		}else if(qName.equals("condition"))
			constructor = attrs.getValue("constructor");
		//Get the condition constructor arguments
		else if(qName.equals("argument"))
			arguments.add(attrs.getValue("value"));
	}

	@Override
	/**
	 * 		This function gets called at the end of an element
	 * 		At this point the text is pretty much set
	 * */
	public void endElement(final String namespaceURI, final String sName, final String qName) 
	{
		//If the element end in "achievement" then add to the list the new achievement
		if (qName.equals("achievement"))
			list.add(new Achievement(identifier, title, category, description, baseScore, active, condition));
		else if (qName.equals("title"))
		{
			if(text != null) //check if the text was actually formed
				title = text.trim(); //trim in case there are additional spaces
		} else if (qName.equals("category")) 
		{
			if(text != null)
				try
				{
					category = Category.valueOf(text.trim().toUpperCase()); //attempt to get the enum value of the given string
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
					baseScore = Integer.parseInt(text.trim()); //attempt to parse the text given as an int
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE BASESCORE AS INTEGER - " + title + "\n\t" + e);
				}
		} else if (qName.equals("active"))
		{
			if(text != null)
				if(text.trim().toLowerCase().compareTo("yes") == 0) //check if the achievement will be active or not
					active = true;
				else
					active = false;
		} else if (qName.equals("condition"))
		{
			text = "";
			
			//Create a string of all the arguments, delimited by a ','
			//Reason for this is that there have been some modifications
			//in the xml layout and the old implementation only
			//works based on splitting a string by ','
			for(int i = 0; i < arguments.size(); i++)
				text = text + ( i >= 1 ? "," : "" ) + arguments.get(i).trim();
			
			condition = get_condition(text.trim());
		}
	}
	
	//Return the condition that is more suitable
	//based on the category and the constructor supplied 
	private ChatCondition get_condition(String cond)
	{
		ChatCondition c = null;
		String str_array[] = null;
		
		switch(category)
		{
			case EXPERIENCE:			
				try
				{
					if(constructor.compareTo("LevelGreaterThanCondition") == 0) //Checking the condition
						c = new LevelGreaterThanCondition(Integer.parseInt(cond)); //loading the condition
				}
				catch(final NumberFormatException e)
				{
					LOGGER.error("ERROR: COULD NOT PARSE THE CONDITION PARAMETER AS INTEGER - " + title + "\n\t" + e);
				}
					
				return c;
				
			case FIGHTING: 				
				try
				{
					//This constructor is overloaded (2 implementations)
					//So I need to get the arguments right
					if(constructor.compareTo("PlayerHasKilledNumberOfCreaturesCondition") == 0)
					{
						int number = 0;
						str_array = cond.split(",");
						String aux[] = new String[str_array.length - 1]; //this string will hold only the String... argument
						
						if(str_array.length < 2) //check for errors
							throw new IllegalArgumentException("The constructor "
									+ "PlayerHasKilledNumberOfCreaturesCondition needs at least 2 arguments!");
						
						if(str_array.length > 2)
						{							
							number = Integer.parseInt(str_array[0].trim());
							
							for(int i = 0; i < aux.length; i++)
								aux[i] = str_array[i + 1].trim(); //populate the String... argument to feed the constructor
							
							//This constructor needs an int and String... (aka String[])
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
						
						c = new QuestStateGreaterThanCondition(str_array[0].trim(), 
								Integer.parseInt(str_array[1].trim()), Integer.parseInt(str_array[2].trim()));
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
						
						c = new PlayerVisitedZonesInRegionCondition(str_array[0].trim(), ( str_array[1].trim().toLowerCase().compareTo("yes") == 0 ?
								Boolean.TRUE : Boolean.FALSE ), ( str_array[2].trim().toLowerCase().compareTo("yes") == 0 ? Boolean.TRUE : Boolean.FALSE ));
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
