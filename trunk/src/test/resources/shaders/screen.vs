#version 140
 
uniform Transformation {
	mat4 projection_matrix;
	mat4 modelview_matrix;
};
 
in vec3 vertex;
 
void main() {
	gl_Position = projection_matrix * modelview_matrix * vec4(vertex, 1.0);
}

//varying vec4 v_color;
//
//void main() {
//	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
//	v_color = vec4(0.6, 0.3, 0.4, 1.0);
//}