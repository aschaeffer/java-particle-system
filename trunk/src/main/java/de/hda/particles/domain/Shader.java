package de.hda.particles.domain;

public class Shader {

	private Integer shaderProgram = -1;
	private Integer vertexShaderId = -1;
	private Integer fragmentShaderId = -1;
	private Integer geometryShaderId = -1;

	public Shader(Integer shaderProgram, Integer vertexShaderId, Integer fragmentShaderId, Integer geometryShaderId) {
		this.shaderProgram = shaderProgram;
		this.vertexShaderId = vertexShaderId;
		this.fragmentShaderId = fragmentShaderId;
		this.setGeometryShaderId(geometryShaderId);
	}

	public Integer getShaderProgram() {
		return shaderProgram;
	}

	public void setShaderProgram(Integer shaderProgram) {
		this.shaderProgram = shaderProgram;
	}

	public Integer getVertexShaderId() {
		return vertexShaderId;
	}

	public void setVertexShaderId(Integer vertexShaderId) {
		this.vertexShaderId = vertexShaderId;
	}

	public Integer getFragmentShaderId() {
		return fragmentShaderId;
	}

	public void setFragmentShaderId(Integer fragmentShaderId) {
		this.fragmentShaderId = fragmentShaderId;
	}

	public Integer getGeometryShaderId() {
		return geometryShaderId;
	}

	public void setGeometryShaderId(Integer geometryShaderId) {
		this.geometryShaderId = geometryShaderId;
	}
}
