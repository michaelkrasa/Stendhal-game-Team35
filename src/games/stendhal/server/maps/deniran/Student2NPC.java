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

public class Student2NPC implements ZoneConfigurator {
 
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
	    final SpeakerNPC npc = new SpeakerNPC("Zhaoyu") {
        
	    	@Override
	    	protected void createPath() {
	    		List<Node> nodes=new LinkedList<Node>();
	    		nodes.add(new Node(65, 67));
	    		nodes.add(new Node(40, 67));
	    		setPath(new FixedPath(nodes,true));
        }

        @Override
		protected void createDialog() {
            // Hinting at the upcoming quest
            addGreeting("I just wish I could learn about #computers but who knows what they even are?!");
            addJob("Are you offering ME a job? Otherwise, can't help, sorry.");
            addHelp("Maybe if you found a computer science professor I could learn. But that just might be a big hint.");
            // More hints for the inquisitive player!
            addReply("computers","Now they sound cool, I wish I understood recursive programming.");
            addGoodbye("See you!");
        }
    };

    // All the students will use this as a placeholder appearance (sorry about the lack of diversity...)
    npc.setEntityClass("boynpc");
    npc.setDescription("You see Zhaoyu. A student. He got that computer science loving look.");
    // Set the initial position to be the first node on the Path you defined above.
    npc.setPosition(65, 67);
    npc.initHP(100);

    zone.add(npc);   
}
	
}