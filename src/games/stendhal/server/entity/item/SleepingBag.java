package games.stendhal.server.entity.item;
import java.util.Map;

import games.stendhal.server.entity.Entity;

//import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.SleepStatus;
import marauroa.common.game.RPObject;


public class SleepingBag extends Item {

	public SleepingBag(final SleepingBag item) {
		super(item);
		setPersistent(true);	
	}
	
	/**
	 * Creates a new seed
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SleepingBag(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		setPersistent(true);
	}
	
	@Override
	public boolean onUsed(final RPEntity user) {
		/* Check whether its a player that is trying to use the sleeping bag */
		if (user instanceof Player) {
			/* Check whether the sleeping bag is close to the Player */
			if (isContained()) {
				// We modify the base container if the object change.
				RPObject base = getContainer();

				while (base.isContained()) {
					base = base.getContainer();
				}
				/* Checking whether the item is within reach of the player */
				if (!user.nextTo((Entity) base)) {
					user.sendPrivateText("The sleeping bag is too far away");
					return false;
				}
			} else {
				if (!nextTo(user)) {
					user.sendPrivateText("The sleeping bag is too far away");
					return false;
				}
			}
			/* Place the sleeping bag on the ground */
			if(user.isEquippedItemInSlot("bag", "sleeping bag")) {
				this.setPosition(user.getX(), user.getY());
				getZone().add(this);
				user.drop(this);
			}			
			/* Inflict the sleeping status on the player */
			SleepStatus status = new SleepStatus();
			user.getStatusList().inflictStatus(status, user);		
			return true;
		} else {
			return false;
		}
	}
}
