package de.hda.particles.hud;

public class HUDCommand {

	private HUDCommandTypes type = HUDCommandTypes.NOOP;
	private Object payLoad;

	public HUDCommand(HUDCommandTypes type, Object payLoad) {
		this.setType(type);
		this.setPayLoad(payLoad);
	}

	public HUDCommandTypes getType() {
		return type;
	}

	public void setType(HUDCommandTypes type) {
		this.type = type;
	}

	public Object getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(Object payLoad) {
		this.payLoad = payLoad;
	}

}
