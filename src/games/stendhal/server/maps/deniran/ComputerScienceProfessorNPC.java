package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Map;

/**
 * Adds the new resident computer science proffesor to the Deniran School of Computer Science
 * 
 * @author mbax4dk2
 *
 */

public class ComputerScienceProfessorNPC implements ZoneConfigurator {
 
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
	    final SpeakerNPC npc = new SpeakerNPC("Lon Jatham") {
        
	    	@Override
	    	protected void createPath() {
	    		setPath(null);
        }

        @Override
		protected void createDialog() {
            // Hinting at the upcoming quest
            addGreeting("Welcome to the new school of computer science! A school, about #computers and their science!");
            addJob("My whole aim is to educate people about computer science, but right now I have no students signed up!");
            addHelp("If I could only find some students, I'd even answer any of their questions.");
            // More hints for the inquisitive player!
            addReply("computers","Yeah! Computers! I teach people about them.");
            addGoodbye("Just gonna waggle my eyebrows at you, and say goodbye for now.");
        }
    };

    // A placeholder sprite for now
    npc.setEntityClass("wisemannpc");
    npc.setDescription("You see Lon Jatham, a powerhouse of computery knowledge.");
    // Set the initial position to be the first node on the Path you defined above.
    npc.setPosition(51, 83);
    npc.initHP(100);

    zone.add(npc);   
}
	
}