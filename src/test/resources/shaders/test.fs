precision mediump float;
uniform float time;
varying vec2 surfacePosition;

//
// Description : Array and textureless GLSL 2D/3D/4D 
//               noise functions with wrapping
//      Author : People
//  Maintainer : Anyone
//     Lastmod : 20130111 (davidwparker)
//     License : No Copyright No rights reserved.
//               Freely distributed
//
float snoise(vec3 uv, float res)
{
	const vec3 s = vec3(1e0, 1e2, 1e4);
	
	uv *= res;
	
	vec3 uv0 = floor(mod(uv, res))*s;
	vec3 uv1 = floor(mod(uv+vec3(1.), res))*s;
	
	vec3 f = fract(uv);
	f = f*f*(3.0-2.0*f);

	vec4 v = vec4(uv0.x+uv0.y+uv0.z, uv1.x+uv0.y+uv0.z,
		      uv0.x+uv1.y+uv0.z, uv1.x+uv1.y+uv0.z);

	vec4 r = fract(sin(v*1e-3)*1e5);
	float r0 = mix(mix(r.x, r.y, f.x), mix(r.z, r.w, f.x), f.y);
	
	r = fract(sin((v + uv1.z - uv0.z)*1e-3)*1e5);
	float r1 = mix(mix(r.x, r.y, f.x), mix(r.z, r.w, f.x), f.y);
	
	return mix(r0, r1, f.z)*2.-1.;
}

// Tau is 2Pi
const float Tau = 6.2832;

void main(void) 
{
	vec2 p = surfacePosition;
	
	float color = 		2.0 - (3.*length(2.*p));
	
	vec3 coord = vec3(atan(p.x,p.y)/Tau+0.5, length(p)*0.4, 0);
	
	for(int i = 1; i <= 7; i++)
	{
		float power = pow(2.0, float(i));
		vec3 timed = vec3(0.,-time*.02, time*.01);
		color += (1.5 / power) * snoise(coord + timed, power*12.);
	}

	gl_FragColor = vec4( color, pow(max(color,0.),2.)*0.4, pow(max(color,0.),3.)*0.15 , 1.0);
}