package de.hda.particles.editor;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hda.particles.renderer.particles.AbstractBillboardParticleRenderer;

/**
 * 
 * Experiment a litte:
 * http://www.andersriggelsen.dk/glblendfunc.php
 * 
 * @author aschaeffer
 *
 */
public class BillboardParticleRendererEditor extends AbstractParticleRendererEditor<AbstractBillboardParticleRenderer> implements Editor {

	private final Logger logger = LoggerFactory.getLogger(BillboardParticleRendererEditor.class);

	@Override
	public void setup() {
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.MIN_SIZE, "Min Size"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.MAX_SIZE, "Max Size"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.POINT_SIZE, "Point Size"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.FADE_THRESHOLD_SIZE, "Fade Threshold Size"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.BLEND_FUNCTION, "Blend Function"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.RENDER_FUNCTION, "Render Function"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.TEXTURE_FORMAT, "Texture Format"));
		editorEntries.add(HUDEditorEntry.create(AbstractBillboardParticleRenderer.TEXTURE_FILENAME, "Texture Filename"));
	}

	@Override
	public Boolean accept(Class<? extends Object> clazz) {
		return clazz.equals(AbstractBillboardParticleRenderer.class) || clazz.getSuperclass().equals(AbstractBillboardParticleRenderer.class);
	}
	
	@Override
	public Class<? extends Object> getAcceptable() {
		return AbstractBillboardParticleRenderer.class;
	}

	@Override
	public List<HUDEditorEntry> getEditorEntries() {
		return editorEntries;
	}
	
	@Override
	public void decrease(String fieldName) {
		if (fieldName.equals(AbstractBillboardParticleRenderer.MIN_SIZE)) {
			if (subject.getMinSize() >= 1.0f) {
				subject.setMinSize(subject.getMinSize() - 1.0f);
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.MAX_SIZE)) {
			if (subject.getMaxSize() >= 1.0f) {
				subject.setMaxSize(subject.getMaxSize() - 1.0f);
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.POINT_SIZE)) {
			if (subject.getPointSize() >= 1.0f) {
				subject.setPointSize(subject.getPointSize() - 1.0f);
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.FADE_THRESHOLD_SIZE)) {
			if (subject.getFadeThresholdSize() >= 1.0f) {
				subject.setFadeThresholdSize(subject.getFadeThresholdSize() - 1.0f);
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.BLEND_FUNCTION)) {
			switch (subject.getBlendFunction()) {
				case GL11.GL_ZERO:
					break;
				case GL11.GL_ONE:
					subject.setBlendFunction(GL11.GL_ZERO);
					break;
				case GL11.GL_SRC_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE);
					break;
				case GL11.GL_SRC_ALPHA_SATURATE:
					subject.setBlendFunction(GL11.GL_SRC_ALPHA);
					break;
				case GL11.GL_SRC_COLOR:
					subject.setBlendFunction(GL11.GL_SRC_ALPHA_SATURATE);
					break;
				case GL11.GL_DST_ALPHA:
					subject.setBlendFunction(GL11.GL_SRC_COLOR);
					break;
				case GL11.GL_DST_COLOR:
					subject.setBlendFunction(GL11.GL_DST_ALPHA);
					break;
				case GL11.GL_CONSTANT_ALPHA:
					subject.setBlendFunction(GL11.GL_DST_COLOR);
					break;
				case GL11.GL_CONSTANT_COLOR:
					subject.setBlendFunction(GL11.GL_CONSTANT_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_CONSTANT_ALPHA:
					subject.setBlendFunction(GL11.GL_CONSTANT_COLOR);
					break;
				case GL11.GL_ONE_MINUS_CONSTANT_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_SRC_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_CONSTANT_COLOR);
					break;
				case GL11.GL_ONE_MINUS_SRC_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_DST_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_SRC_COLOR);
					break;
				case GL11.GL_ONE_MINUS_DST_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_DST_ALPHA);
					break;
				default:
					subject.setBlendFunction(GL11.GL_ONE);
					break;
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.RENDER_FUNCTION)) {
			switch (subject.getRenderFunction()) {
				case GL11.GL_POINTS:
					break;
				case GL11.GL_LINES:
					subject.setRenderFunction(GL11.GL_POINTS);
					break;
				case GL11.GL_LINE_STRIP:
					subject.setRenderFunction(GL11.GL_LINES);
					break;
				case GL11.GL_LINE_LOOP:
					subject.setRenderFunction(GL11.GL_LINE_STRIP);
					break;
				case GL11.GL_TRIANGLES:
					subject.setRenderFunction(GL11.GL_LINE_LOOP);
					break;
				case GL11.GL_TRIANGLE_STRIP:
					subject.setRenderFunction(GL11.GL_TRIANGLES);
					break;
				case GL11.GL_TRIANGLE_FAN:
					subject.setRenderFunction(GL11.GL_TRIANGLE_STRIP);
					break;
				case GL11.GL_QUADS:
					subject.setRenderFunction(GL11.GL_TRIANGLE_FAN);
					break;
				case GL11.GL_QUAD_STRIP:
					subject.setRenderFunction(GL11.GL_QUADS);
					break;
				case GL11.GL_POLYGON:
					subject.setRenderFunction(GL11.GL_QUAD_STRIP);
					break;
				default:
					subject.setRenderFunction(GL11.GL_POINTS);
					break;
			}
		}
	}

	@Override
	public void setMin(String fieldName) {
		if (fieldName.equals(AbstractBillboardParticleRenderer.MIN_SIZE)) {
			subject.setMinSize(0.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.MAX_SIZE)) {
			subject.setMaxSize(0.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.POINT_SIZE)) {
			subject.setPointSize(0.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.FADE_THRESHOLD_SIZE)) {
			subject.setFadeThresholdSize(0.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.BLEND_FUNCTION)) {
			subject.setBlendFunction(GL11.GL_ONE);
		}
	}

	@Override
	public void increase(String fieldName) {
		if (fieldName.equals(AbstractBillboardParticleRenderer.MIN_SIZE)) {
			subject.setMinSize(subject.getMinSize() + 1.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.MAX_SIZE)) {
			subject.setMaxSize(subject.getMaxSize() + 1.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.POINT_SIZE)) {
			subject.setPointSize(subject.getPointSize() + 1.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.FADE_THRESHOLD_SIZE)) {
			subject.setFadeThresholdSize(subject.getFadeThresholdSize() + 1.0f);
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.BLEND_FUNCTION)) {
			switch (subject.getBlendFunction()) {
				case GL11.GL_ZERO:
					subject.setBlendFunction(GL11.GL_ONE);
					break;
				case GL11.GL_ONE:
					subject.setBlendFunction(GL11.GL_SRC_ALPHA);
					break;
				case GL11.GL_SRC_ALPHA:
					subject.setBlendFunction(GL11.GL_SRC_ALPHA_SATURATE);
					break;
				case GL11.GL_SRC_ALPHA_SATURATE:
					subject.setBlendFunction(GL11.GL_SRC_COLOR);
					break;
				case GL11.GL_SRC_COLOR:
					subject.setBlendFunction(GL11.GL_DST_ALPHA);
					break;
				case GL11.GL_DST_ALPHA:
					subject.setBlendFunction(GL11.GL_DST_COLOR);
					break;
				case GL11.GL_DST_COLOR:
					subject.setBlendFunction(GL11.GL_CONSTANT_ALPHA);
					break;
				case GL11.GL_CONSTANT_ALPHA:
					subject.setBlendFunction(GL11.GL_CONSTANT_COLOR);
					break;
				case GL11.GL_CONSTANT_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_CONSTANT_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_CONSTANT_COLOR);
					break;
				case GL11.GL_ONE_MINUS_CONSTANT_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_SRC_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_SRC_COLOR);
					break;
				case GL11.GL_ONE_MINUS_SRC_COLOR:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_DST_ALPHA);
					break;
				case GL11.GL_ONE_MINUS_DST_ALPHA:
					subject.setBlendFunction(GL11.GL_ONE_MINUS_DST_COLOR);
					break;
				case GL11.GL_ONE_MINUS_DST_COLOR:
					break;
				default:
					subject.setBlendFunction(GL11.GL_ONE);
					break;
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.RENDER_FUNCTION)) {
			switch (subject.getRenderFunction()) {
				case GL11.GL_POINTS:
					subject.setRenderFunction(GL11.GL_LINES);
					break;
				case GL11.GL_LINES:
					subject.setRenderFunction(GL11.GL_LINE_STRIP);
					break;
				case GL11.GL_LINE_STRIP:
					subject.setRenderFunction(GL11.GL_LINE_LOOP);
					break;
				case GL11.GL_LINE_LOOP:
					subject.setRenderFunction(GL11.GL_TRIANGLES);
					break;
				case GL11.GL_TRIANGLES:
					subject.setRenderFunction(GL11.GL_TRIANGLE_STRIP);
					break;
				case GL11.GL_TRIANGLE_STRIP:
					subject.setRenderFunction(GL11.GL_TRIANGLE_FAN);
					break;
				case GL11.GL_TRIANGLE_FAN:
					subject.setRenderFunction(GL11.GL_QUADS);
					break;
				case GL11.GL_QUADS:
					subject.setRenderFunction(GL11.GL_QUAD_STRIP);
					break;
				case GL11.GL_QUAD_STRIP:
					subject.setRenderFunction(GL11.GL_POLYGON);
					break;
				case GL11.GL_POLYGON:
					break;
				default:
					subject.setRenderFunction(GL11.GL_POINTS);
					break;
			}
		}
	}

	@Override
	public void setMax(String fieldName) {
		if (fieldName.equals(AbstractBillboardParticleRenderer.BLEND_FUNCTION)) {
			subject.setBlendFunction(GL11.GL_ONE_MINUS_DST_COLOR);
		}
	}

	@Override
	public String getValue(String fieldName) {
		String superValue = super.getValue(fieldName);
		if (! "N/A".equals(superValue)) {
			return superValue;
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.MIN_SIZE)) {
			return  String.format("%.2f", subject.getMinSize());
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.MAX_SIZE)) {
			return  String.format("%.2f", subject.getMaxSize());
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.POINT_SIZE)) {
			return  String.format("%.2f", subject.getPointSize());
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.FADE_THRESHOLD_SIZE)) {
			return  String.format("%.2f", subject.getFadeThresholdSize());
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.BLEND_FUNCTION)) {
			switch (subject.getBlendFunction()) {
				case GL11.GL_ZERO:
					return "Zero";
				case GL11.GL_ONE:
					return "One";
				case GL11.GL_SRC_ALPHA:
					return "Src Alpha";
				case GL11.GL_SRC_ALPHA_SATURATE:
					return "Src Alpha Saturate";
				case GL11.GL_SRC_COLOR:
					return "Src Color";
				case GL11.GL_DST_ALPHA:
					return "Dst Alpha";
				case GL11.GL_DST_COLOR:
					return "Dst Color";
				case GL11.GL_CONSTANT_ALPHA:
					return "Constant Alpha";
				case GL11.GL_CONSTANT_COLOR:
					return "Constant Color";
				case GL11.GL_ONE_MINUS_CONSTANT_ALPHA:
					return "One Minus Constant Alpha";
				case GL11.GL_ONE_MINUS_CONSTANT_COLOR:
					return "One Minus Constant Color";
				case GL11.GL_ONE_MINUS_SRC_ALPHA:
					return "One Minus Src Alpha";
				case GL11.GL_ONE_MINUS_SRC_COLOR:
					return "One Minus Src Color";
				case GL11.GL_ONE_MINUS_DST_ALPHA:
					return "One Minus Dst Alpha";
				case GL11.GL_ONE_MINUS_DST_COLOR:
					return "One Minus Dst Color";
				default:
					return subject.getBlendFunction().toString();
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.RENDER_FUNCTION)) {
			switch (subject.getRenderFunction()) {
				case GL11.GL_POINTS:
					return "Points";
				case GL11.GL_LINES:
					return "Lines";
				case GL11.GL_LINE_STRIP:
					return "Line Strip";
				case GL11.GL_LINE_LOOP:
					return "Line Loop";
				case GL11.GL_TRIANGLES:
					return "Triangles";
				case GL11.GL_TRIANGLE_STRIP:
					return "Triangle Strip";
				case GL11.GL_TRIANGLE_FAN:
					return "Triangle Fan";
				case GL11.GL_QUADS:
					return "Quads";
				case GL11.GL_QUAD_STRIP:
					return "Quad Stip";
				case GL11.GL_POLYGON:
					return "Polygon";
				default:
					return subject.getRenderFunction().toString();
			}
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.TEXTURE_FORMAT)) {
			return subject.getTextureFormat();
		} else if (fieldName.equals(AbstractBillboardParticleRenderer.TEXTURE_FILENAME)) {
			return subject.getTextureFilename();
		} else {
			return "N/A";
		}
	}
	
	@Override
	public Object getObject(String fieldName) {
		if (fieldName.equals(AbstractBillboardParticleRenderer.TEXTURE_FORMAT)) {
			return subject.getTextureFormat();
		} else {
			return null;
		}
	}

}
