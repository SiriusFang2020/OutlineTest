package outliner;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.post.Filter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Texture;

import static com.jme3.texture.Image.*;

/**
 * 在这个Filter中通过使用 getOutlineTexture 得到一个深度检测的纹理
 */
public class OutlinePreFilter2 extends Filter {

    private Pass normalPass;
    private RenderManager renderManager;
    private final String outlinePreMatPath;

    /**
     * Creates a OutlinePreFilter
     */
    public OutlinePreFilter2(String outlinePreMatPath) {
        super("OutlinePreFilter");
        this.outlinePreMatPath = outlinePreMatPath;
    }

    /**
     * 设置生成深度纹理对象
     */
    @Override
    protected boolean isRequiresDepthTexture() {
        return true;
    }

    @Override
    protected void postQueue(RenderQueue queue) {
        Renderer r = renderManager.getRenderer();
        r.setFrameBuffer(normalPass.getRenderFrameBuffer());
        renderManager.getRenderer().clearBuffers(true, true, false);
    }

    @Override
    protected void postFrame(RenderManager renderManager, ViewPort viewPort, FrameBuffer prevFilterBuffer, FrameBuffer sceneBuffer) {
        super.postFrame(renderManager, viewPort, prevFilterBuffer, sceneBuffer);

    }

    @Override
    protected Material getMaterial() {
        return material;
    }

    public Texture getOutlineTexture() {
        return normalPass.getRenderedTexture();
    }

    @Override
    protected void initFilter(AssetManager manager, RenderManager renderManager, ViewPort vp, int w, int h) {
        this.renderManager = renderManager;
        normalPass = new Pass();
        normalPass.init(renderManager.getRenderer(),
                w,
                h,
                Format.RGBA8,
                Format.Depth);
        material = new Material(manager, outlinePreMatPath);
    }

    @Override
    protected void cleanUpFilter(Renderer r) {
        normalPass.cleanup(r);
    }

}