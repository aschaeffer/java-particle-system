package de.hda.particles.hud;

public class HUDCommand {

	private HUDCommandTypes type = HUDCommandTypes.NOOP;
	private Object payLoad;
	private Object payLoad2;
	private Object payLoad3;

	public HUDCommand(HUDCommandTypes type) {
		this.setType(type);
	}

	public HUDCommand(HUDCommandTypes type, Object payLoad) {
		this.setType(type);
		this.setPayLoad(payLoad);
	}

	public HUDCommand(HUDCommandTypes type, Object payLoad, Object payLoad2) {
		this.setType(type);
		this.setPayLoad(payLoad);
		this.setPayLoad2(payLoad2);
	}

	public HUDCommand(HUDCommandTypes type, Object payLoad, Object payLoad2, Object payLoad3) {
		this.setType(type);
		this.setPayLoad(payLoad);
		this.setPayLoad2(payLoad2);
		this.setPayLoad3(payLoad3);
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

	public Object getPayLoad2() {
		return payLoad2;
	}

	public void setPayLoad2(Object payLoad2) {
		this.payLoad2 = payLoad2;
	}

	public Object getPayLoad3() {
		return payLoad3;
	}

	public void setPayLoad3(Object payLoad3) {
		this.payLoad3 = payLoad3;
	}

}
