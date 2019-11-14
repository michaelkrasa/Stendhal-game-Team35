package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Adds one of three new students to Deniran, ready for the school of computer science quest
 * 
 * @author mbax4dk2
 *
 */

public class Student3NPC implements ZoneConfigurator {
 
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
	    final SpeakerNPC npc = new SpeakerNPC("Alexandru") {
        
	    	@Override
	    	protected void createPath() {
	    		List<Node> nodes=new LinkedList<Node>();
	    		nodes.add(new Node(30, 54));
	    		nodes.add(new Node(30, 67));
	    		setPath(new FixedPath(nodes,true));
        }

        @Override
		protected void createDialog() {
            // Hinting at the upcoming quest
            addGreeting("I just wish I could learn about #computers but people don't even believe they're real?!");
            addJob("A job would be great, but I'm looking to learn first.");
            addHelp("I bet this city is the perfect place for a computer science profesor.");
            // More hints for the inquisitive player!
            addReply("computers","Now they sound cool, I wish I could ask someone what types of variables there are. I'm fun like that.");
            addGoodbye("Yeah, computers!");
        }
    };

    // All the students will use this as a placeholder appearance (sorry about the lack of diversity...)
    npc.setEntityClass("boynpc");
    npc.setDescription("You see Alexandru. Another student. There are just so many freaking students in this city.");
    // Set the initial position to be the first node on the Path you defined above.
    npc.setPosition(30, 54);
    npc.initHP(100);

    zone.add(npc);   
}
	
}