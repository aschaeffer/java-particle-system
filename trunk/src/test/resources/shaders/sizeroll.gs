#version 120
#extension GL_ARB_geometry_shader4 : enable

uniform float time;

varying in vec4 vsColor;
varying out vec4 gsColor;

float rand(vec2 n, float f) {
	return f * 0.5 + f * 0.5 * fract(sin(dot(n.xy ,vec2(12.9898,78.233))) * 43758.5453 + time);
} 

void main() {
		gl_Position = gl_ModelViewProjectionMatrix * gl_PositionIn[0];
		gsColor = vsColor;
		EmitVertex();
		EndPrimitive();
		for (int i=1; i<37; i++) {
			// gl_Position = gl_PositionIn[0] + vec4(-15 + sin(360.0/i) * 30, 0, -15 + cos(360.0/i) * 30, 0);
			gl_Position = gl_PositionIn[0] + vec4(rand(gl_PositionIn[0].xz, 30), 0, rand(gl_PositionIn[0].zx, 30), 0);
    	    gl_Position = gl_ModelViewProjectionMatrix * gl_Position;
	        gsColor = vsColor;
			EmitVertex();
			EndPrimitive();
		}
		// gl_Position = gl_PositionIn[0] + vec4(rand(gl_PositionIn[0].xy, 30),rand(gl_PositionIn[0].xy, 30),rand(gl_PositionIn[0].xy, 30),0);
		// gl_Position = gl_PositionIn[0] + vec4(rand(gl_PositionIn[0].xy, 30),rand(gl_PositionIn[0].xy, 30),rand(gl_PositionIn[0].xy, 30),0);
        // gl_Position = gl_ModelViewProjectionMatrix * gl_Position;
        // gsColor = vsColor;
		// EmitVertex();
		// EndPrimitive();
}
