package de.hda.particles.renderer.types;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Color;

import de.hda.particles.domain.Particle;
import de.hda.particles.features.ParticleColor;
import de.hda.particles.features.ParticleSize;

public class MetaBallsRenderType extends AbstractRenderType implements RenderType {

	public final static String NAME = "MetaBalls";

	public MetaBallsRenderType() {}

	@Override
	public void before() {
		glPushMatrix();
		glEnable(GL_BLEND);
		glColor4f(0.8f, 0.8f, 0.8f, 0.3f);
		glBegin(GL_QUADS);
	}
	
	@Override
	public void after() {
		glEnd();
		glPopMatrix();
	}

	@Override
	public void render(Particle particle) {
		
		
		
		// glVertex3f(particle.getX(), particle.getY(), particle.getZ());
		
		
//		int tFragmentShader = ShaderUtils.loadFragmentShaderFromFile(inGL,"/shaders/raymarchingshaders/metaballs.fs");
//        mLinkedShader = ShaderUtils.generateSimple_1xFS_ShaderProgramm(inGL,tFragmentShader);
//        mScreenDimensionUniform2fv = DirectBufferUtils.createDirectFloatBuffer(new float[] {(float)mBaseFrameBufferObjectRendererExecutor.getWidth(), (float)mBaseFrameBufferObjectRendererExecutor.getHeight()});
//        //create BufferedImage to be used as LUT ...
//        BufferedImage tLUT = TextureUtils.createARGBBufferedImage(5,1);
//        //"Bicycles" from kuler.adobe.com
//        tLUT.setRGB(0,0,0x0065356B);
//        tLUT.setRGB(1,0,0x00AB434F);
//        tLUT.setRGB(2,0,0x00C76347);
//        tLUT.setRGB(3,0,0x00FFA24C);
//        tLUT.setRGB(4,0,0x00519183);
//        mLUTTextureID = TextureUtils.generateTexture1DFromBufferedImage(inGL,tLUT,GL_CLAMP);

	}
	
	@Override
	public String getName() {
		return NAME;
	}

}
