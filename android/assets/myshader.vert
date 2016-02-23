
uniform mat4 u_projTrans;

attribute vec4 a_position;
attribute vec2 a_texCoord;

varying vec2 v_texCoord;

void mail() {
	gl_Position = u_projTrans * a_position;
	v_texCoord = a_texCoord;
}