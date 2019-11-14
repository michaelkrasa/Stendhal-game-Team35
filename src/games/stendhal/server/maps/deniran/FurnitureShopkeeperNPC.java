package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Map;

public class FurnitureShopkeeperNPC implements ZoneConfigurator
{
	// Configures the zone.
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	// Builds the NPC.
	private void buildNPC(StendhalRPZone zone)
	{
		// Initialise the NPC and its name.
		final SpeakerNPC npc = new SpeakerNPC("Ronan")
		{
			// Set the NPC to not move.
			@Override
			protected void createPath() {
				setPath(null);
			}
			
			// Create the NPC's dialogue.
			@Override
			public void createDialog()
			{
				// Lets NPC say hello to the player.
				addGreeting("Hey there! I will be opening a furniture shop in Deniran soon!");			
				// Lets NPC say bye to the player.
				addGoodbye("Bye! Come back later for all your furniture needs!");
			}

		};
		// Set NPC's class and outfit.
		npc.setEntityClass("man_001_npc");
		// Set NPC's description.
		npc.setDescription ("You see Ronan in deep thought about a future business opportunity...");
		// Set NPC's position
		npc.setPosition(32, 32);
		// Set NPC's health.
		npc.initHP(100);
		// Add NPC to the zone.
		zone.add(npc);
	}
}