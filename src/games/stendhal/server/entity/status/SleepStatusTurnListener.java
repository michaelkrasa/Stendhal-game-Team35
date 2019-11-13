package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

public class SleepStatusTurnListener implements TurnListener{
	StatusList statusList;
	private static final int SECONDS_TO_HEAL = 60;
	
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}	
	
	@Override
	public void onTurnReached(int turn) {
		RPEntity entity = statusList.getEntity();
		
		/* Check that the entity exists */
		if (entity == null) {
			return;
		}
		
		/* Sleeping status will be removed if the player moves! */
		int steps = entity.getStepsTaken();
				
		if(steps > 0) {
			if(entity instanceof Player) {
				entity.sendPrivateText(NotificationType.SCENE_SETTING, "You're awake.");
				statusList.removeAll(SleepStatus.class);
			}
			return;
		}
		
		/* Perform healing on player if they need it, if they don't healing stops */
		int heal = entity.getBaseHP() / SECONDS_TO_HEAL;
		
		/* When players are fully healed the sleeping status will be taken away  */
		if(entity.heal(heal, true) == 0) {
			entity.sendPrivateText(NotificationType.SCENE_SETTING, "You're completely healed.");
			entity.sendPrivateText(NotificationType.SCENE_SETTING, "You're awake.");
			statusList.removeAll(SleepStatus.class);
			return;
		}
		TurnNotifier.get().notifyInSeconds(1, this);				
	}
}