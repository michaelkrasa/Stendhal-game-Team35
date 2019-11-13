package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;

public class SleepStatusHandler implements StatusHandler<SleepStatus>{

	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity attacker) {
		
		RPEntity entity = statusList.getEntity();
		
		/* Check that the entity exists */
		if (entity == null) {
			return;
		}
		int count = statusList.countStatusByType(status.getStatusType());
		
		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You're sleeping.");
				
		/* Checking whether player already has a sleepign status */
		if(count == 0) {	
			statusList.addInternal(status);
			//statusList.activateStatusAttribute("status_" + status.getName());
			TurnListener listener = new SleepStatusTurnListener(statusList);
			TurnNotifier.get().notifyInTurns(0, listener);
		}
	}

	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);
		
	}
}
