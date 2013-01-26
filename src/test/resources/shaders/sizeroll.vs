#version 130

// in vec3 position;
// in vec3 color;
// out vec4 vsColor;

varying out vec4 vsColor;

void main() {
	gl_Position = gl_Vertex;
	vsColor = gl_Color.rgba;
}
