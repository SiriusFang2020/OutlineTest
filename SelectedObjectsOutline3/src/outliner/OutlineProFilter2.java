package outliner;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.FrameBuffer;

/**
 * 外描边
 */
public class OutlineProFilter2 extends Filter {

    private String matPath;
    private OutlinePreFilter2 outlinePreFilter2;
    private ColorRGBA outlineColor = new ColorRGBA(0, 1, 0, 1);
    private float outlineWidth = 1;
    private ColorRGBA backGroundColor = new ColorRGBA(0f, 0f, 0f, 0f);

    public OutlineProFilter2(OutlinePreFilter2 outlinePreFilter2, String matPath) {
        super("OutlineFilter");
        this.outlinePreFilter2 = outlinePreFilter2;
        this.matPath = matPath;
    }

    @Override
    protected void initFilter(AssetManager assetManager, RenderManager renderManager, ViewPort vp, int w, int h) {
        MaterialDef matDef = (MaterialDef) assetManager.loadAsset(matPath);
        material = new Material(matDef);
//        w = Math.round(w * 2.0f);
//        h = Math.round(h * 2.0f);
        material.setVector2("Resolution", new Vector2f(w, h));
        material.setColor("OutlineColor", outlineColor);
        material.setFloat("OutlineWidth", outlineWidth);
        material.setColor("BackGroundColor", backGroundColor);
//        material.getAdditionalRenderState().setPolyOffset(-100.0f, -10.0f);
    }

    @Override
    protected void preFrame(float tpf) {
        super.preFrame(tpf);
//        material.setTexture("OutlineDepthTexture", outlinePreFilter.getOutlineTexture());
//		System.out.println("OutlineFilter.preFrame()");
    }

    @Override
    protected void postFrame(RenderManager renderManager, ViewPort viewPort, FrameBuffer prevFilterBuffer, FrameBuffer sceneBuffer) {
        super.postFrame(renderManager, viewPort, prevFilterBuffer, sceneBuffer);
    }

    @Override
    protected Material getMaterial() {
        material.setTexture("OutlineDepthTexture", outlinePreFilter2.getOutlineTexture());
        return material;
    }

    public ColorRGBA getOutlineColor() {
        return outlineColor;

    }


    public void setOutlineColor(ColorRGBA outlineColor) {
        this.outlineColor = outlineColor;
        if (material != null) {
            material.setColor("OutlineColor", outlineColor);
        }
    }

    public float getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(float outlineWidth) {
        this.outlineWidth = outlineWidth;
        if (material != null) {
            material.setFloat("OutlineWidth", outlineWidth);
        }
    }

    public ColorRGBA getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(ColorRGBA backGroundColor) {
        this.backGroundColor = backGroundColor;
        if (material != null) {
            material.setColor("BackGroundColor", backGroundColor);
        }
    }

    public OutlinePreFilter2 getOutlinePreFilter() {
        return outlinePreFilter2;
    }


}
