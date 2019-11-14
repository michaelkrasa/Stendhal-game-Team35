package games.stendhal.server.entity.status;

public class SleepStatus extends Status {
	
	public SleepStatus() {
		super("sleep");
	}
	
	/**
	 * @return
	 * 		StatusType
	 */
	@Override
	public StatusType getStatusType() {
		return StatusType.SLEEP;
	}

}
