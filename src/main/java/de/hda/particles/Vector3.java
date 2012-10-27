package de.hda.particles;

public class Vector3 {

	public Float x = 0.0f;
	public Float y = 0.0f;
	public Float z = 0.0f;
	
	public Vector3() {
	}

	public Vector3(Float x, Float y, Float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getZ() {
		return z;
	}
	public void setZ(Float z) {
		this.z = z;
	}

}
