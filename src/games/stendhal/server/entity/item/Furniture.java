package games.stendhal.server.entity.item;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.portal.HousePortal;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.player.Player;

public class Furniture extends HouseKey
{		
	//Initializing global Map variable to store the degrade state of the latest dropped furniture
	private static Map<Player, Boolean> get_item_degrade_state = new HashMap<Player, Boolean>();
	
	//Initialize global variable to hold the house zones
	private static final String[] zoneNames = 
		{
			"0_kalavan_city",
			"0_kirdneh_city"
		};
	
	//Class constructor which will call the super class constructor to set up the items
	public Furniture(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes)
	{	
		super(name, clazz, subclass, attributes);
	}
	
	//Copy constructor so that the ItemTest won't complain when my items cannot be placed
	//inside the bank chests
	public Furniture(final Furniture furniture)
	{
		super(furniture);
	}
	
	//An override which is used only to remove the degrade function for furniture items only
	//so that they won't disappear (only if placed inside the owner's house)
	@Override
	public void onPutOnGround(final Player player)
	{		
		//Boolean variable which keeps track of whether to
		//degrade the dropped item or not
		boolean is_inside_house = false;

		//A list which holds house portals
		List<HousePortal> tempAllHousePortals = new LinkedList<HousePortal>();
		
		//A house portal which holds the player's house portal(of the house they own)
		HousePortal player_portal = null;
		
		//For each loop to go through each zone
		for (final String zoneName : zoneNames) 
		{
			//Getting the RPZone of the zoneName
			final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone(zoneName);
			
			if (zone != null)
				//For each loop which goes through all portals in the zone
				for (final Portal portal : zone.getPortals())
					//If the portal is a HousePortal
					if (portal instanceof HousePortal)
						//Add the house portal to the list
						tempAllHousePortals.add((HousePortal) portal);				
		}
		
		//For each house portal in the portal list
		for(final HousePortal portal : tempAllHousePortals)
		{
			//Terminate the loop when we find the player's house
			if(portal.getOwner().equals(player.getName()))
			{
				//At which point just remember the player's portal
				player_portal = portal;
				break;
			}
		}
		
		//If the player owns a house
		if(player_portal != null)
			//Check if the player is inside their house 
			if(player_portal.getDestinationZone().equals(player.getZone().getName()))
				is_inside_house = true;
		
		//Store the degrade state of the latest furniture dropped by player
		get_item_degrade_state.put(player, !is_inside_house);

		//If the owner is not inside their house,
		//the item should degrade else not
		super.onPutOnGround(!is_inside_house);
	}
	
	//Function which returns the degrade state of the latest dropped piece of furniture by a player
	public boolean get_furniture_degrade_state(Player player)
	{
		//Check if the dropped item is registered in the map
		//if so, return the value of the key(player)
		//else return true (as any item that is not in the map should degrade)
		return get_item_degrade_state.containsKey(player) ? get_item_degrade_state.get(player) : true;
	}
}
