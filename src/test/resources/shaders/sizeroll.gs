#version 400
 
layout (points) in;
layout (triangle_strip, max_vertices = 4) out; // convert to points, line_strip, or triangle_strip
 
// NB: in and out pass-through vertex->fragment variables must go here if used
 
void main () {
  for(int i = 0; i < gl_VerticesIn; i++) {
    // copy attributes
    gl_Position = gl_in[i].gl_Position;
    EmitVertex();
    // NB: pass-through of variables would go here
 
    gl_Position.y = gl_in[i].gl_Position.y + 5.0;
    EmitVertex();
    gl_Position.y = gl_in[i].gl_Position.y;
    gl_Position.x = gl_in[i].gl_Position.x - 2.0;
    EmitVertex();
    gl_Position.y = gl_in[i].gl_Position.y + 5.0;
    gl_Position.x = gl_in[i].gl_Position.x - 2.0;
    EmitVertex();
  }
}
//#version 400
//
//layout(points) in;
//layout(line_strip, max_vertices = 2) out;
//
//void main()
//{
//	for(int i = 0; i < gl_VerticesIn; i++) {
//		gl_Position = gl_in[i].gl_Position;
//		gl_Color = vec4(0.0, 0.0, 1.0, 0.0);
//		EmitVertex();
//		EndPrimitive();
//		gl_Position = vec4(0.0, 0.0, 0.0, 0.0);
//		EmitVertex();
//		EndPrimitive();
//		gl_Position.y = gl_in[i].gl_Position.y + 10.0;
//		EmitVertex();
//		EndPrimitive();
//		gl_Position.y = gl_in[i].gl_Position.y + 10.0;
//		gl_Position.z = gl_in[i].gl_Position.z + 10.0;
//		EmitVertex();
//		EndPrimitive();
//		gl_Position.z = gl_in[i].gl_Position.z + 10.0;
//		EmitVertex();
//		EndPrimitive();
//	}
//}

