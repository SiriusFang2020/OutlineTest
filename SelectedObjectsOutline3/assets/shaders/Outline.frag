uniform sampler2D m_Texture;
uniform sampler2D m_OutlineDepthTexture;
uniform sampler2D m_DepthTexture;
varying vec2 texCoord;

uniform vec2 m_Resolution;
uniform vec4 m_OutlineColor;
uniform float m_OutlineWidth;
//uniform vec4 m_BackGroundColor;

float plot(vec2 st, float pct){
	return  smoothstep( pct - 0.02, pct, st.y) - smoothstep( pct, pct + 0.02, st.y);
}

void main() {
	vec4 depth = texture2D(m_OutlineDepthTexture, texCoord);
	vec4 depth1 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth,m_OutlineWidth))/m_Resolution);
//	vec4 depth9 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth / 20.0, m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth2 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth,-m_OutlineWidth))/m_Resolution);
//	vec4 depth10 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth / 20.0, -m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth3 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth,m_OutlineWidth))/m_Resolution);
//	vec4 depth11 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth / 20.0, m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth4 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth,-m_OutlineWidth))/m_Resolution);
//	vec4 depth12 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth / 20.0, -m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth5 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(0.,m_OutlineWidth))/m_Resolution);
//	vec4 depth13 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(0.,m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth6 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(0.,-m_OutlineWidth))/m_Resolution);
//	vec4 depth14 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(0.,-m_OutlineWidth / 20.0))/m_Resolution);
	vec4 depth7 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth,0.))/m_Resolution);
//	vec4 depth15 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(m_OutlineWidth / 20.0, 0.))/m_Resolution);
	vec4 depth8 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth,0.))/m_Resolution);
//	vec4 depth16 = texture2D(m_OutlineDepthTexture, ((texCoord*m_Resolution)+vec2(-m_OutlineWidth / 20.0,0.))/m_Resolution);

	// 获取该着色点本来的颜色
	vec4 color = texture2D(m_Texture, texCoord);

	// 如果该着色点处于背景之中
	if(depth==vec4(0.0) && (depth1 != depth || depth2 != depth || depth3 != depth || depth4 != depth || depth5 != depth || depth6 != depth || depth7 != depth || depth8 != depth)){
		gl_FragColor = m_OutlineColor;
	}else{
		gl_FragColor = color;
	}

//	if(depth == m_BackGroundColor && (depth1 != depth || depth2 != depth || depth3 != depth || depth4 != depth || depth5 != depth || depth6 != depth || depth7 != depth || depth8 != depth || depth9 != depth || depth10 != depth || depth11 != depth  || depth12 != depth  || depth13 != depth  || depth14 != depth  || depth15 != depth  || depth16 != depth )){
//		gl_FragColor = m_OutlineColor;
//	}else{
//		gl_FragColor = color;
//	}
}