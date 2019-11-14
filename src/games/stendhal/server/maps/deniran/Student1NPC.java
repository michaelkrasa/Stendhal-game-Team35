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
 * @author Declan Kehoe
 *
 */

public class Student1NPC implements ZoneConfigurator {
 
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
	    final SpeakerNPC npc = new SpeakerNPC("Declan") {
        
	    	@Override
	    	protected void createPath() {
	    		List<Node> nodes=new LinkedList<Node>();
	    		nodes.add(new Node(42, 67));
	    		nodes.add(new Node(65, 67));
	    		setPath(new FixedPath(nodes,true));
        }

        @Override
		protected void createDialog() {
            // Hinting at the upcoming quest
            addGreeting("I just wish I could learn about #computers and the ONES, and the ZEROES, but where could I learn something like that?!");
            addJob("I just don't feel ready for a job right now, maybe once I'm better educated?");
            addHelp("Honestly, right now I'd appreciate some help myself!");
            // More hints for the inqusitive player!
            addReply("computers","Now they sound cool, I wish I understood the difference between python, monty python, and a snake.");
            addGoodbye("See you l8r m8");
        }
    };

    // All the students will use this as a placeholder appearance (sorry about the lack of diversity...)
    npc.setEntityClass("boynpc");
    npc.setDescription("You see Declan. A student. DESPERATE for a path in life.");
    // Set the initial position to be the first node on the Path you defined above.
    npc.setPosition(42, 67);
    npc.initHP(100);

    zone.add(npc);   
}
	
}