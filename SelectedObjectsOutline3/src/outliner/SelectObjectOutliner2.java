/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package outliner;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.MaterialDef;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;

/**
 * 使用方法：
 *      初始化：
 *          FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
 *          viewPort.addProcessor(fpp);
 *          SelectObjectOutliner outliner = new SelectObjectOutliner("resources/Outline.j3md", "resources/OutlinePre.j3md");
 *          outliner.initOutliner(4, new ColorRGBA(0, 255, 255, 255), fpp, renderManager, assetManager, cam);
 *      选择对象：
 *          outliner.select(Spatial model);
 *      取消选择：
 *          outliner.deselect(Spatial model);
 *      设置相关属性：(同时有对应的 get 方法)
 *          outliner.setOutlineWidth(int outlineWidth);
 *          outliner.setColor(ColorRGBA color);
 *          outliner.setOutlineMatPath(String outlineMatPath);
 *          outliner.setOutlinePreMatPath(String outlinePreMatPath);
 */
public class SelectObjectOutliner2 extends BaseAppState {

    private FilterPostProcessor fpp;
    private ViewPort viewport;
    private RenderManager renderManager;
    private AssetManager assetManager;
    private Camera cam;
    private float OutlineWidth = 2.0f;
    private ColorRGBA OutLineColor = new ColorRGBA(0, 1, 1, 1);
    private String outlineProMatPath;
    private String outlinePreMatPath;
    private FXAAFilter fxaaFilter;
    private ColorRGBA backGroundColor = new ColorRGBA(0, 0, 0, 0);

    public SelectObjectOutliner2(FilterPostProcessor fpp, String outlineProMatPath, String outlinePreMatPath) {
        this.outlineProMatPath = outlineProMatPath;
        this.outlinePreMatPath = outlinePreMatPath;
        this.fpp = fpp;
    }

    @Override
    protected void initialize(Application app) {

        this.renderManager = app.getRenderManager();
        this.assetManager = app.getAssetManager();

        this.viewport = app.getViewPort();
        this.backGroundColor = this.viewport.getBackgroundColor();
//        fpp = new FilterPostProcessor(assetManager);
//        FXAAFilter fxaa = new FXAAFilter();
//        fxaa.setSubPixelShift(1.0f / 8.0f);
//        fxaa.setReduceMul(1.0f / 28.0f);
////        fxaa.setReduceMul(2.0f );
//        fxaa.setVxOffset(0.0f);
//        fxaa.setSpanMax(40.0f);
//        fxaa.setEnabled(true);
//        fpp.addFilter(fxaa);
//
//        DLAAFilter dlaaFilter = new DLAAFilter();
//        dlaaFilter.setEnabled(true);
//        fpp.addFilter(dlaaFilter);

//        NFAAFilter nfaaFilter = new NFAAFilter();
//        nfaaFilter.setEnabled(true);
//        fpp.addFilter(nfaaFilter);


//        viewport.addProcessor(fpp);

//        fpp.setNumSamples(16);

        this.cam = app.getCamera();
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

//    public void initOutliner(
//            float OutlineWidth,
//            ColorRGBA OutLineColor,
//            FilterPostProcessor fpp,
//            RenderManager renderManager,
//            AssetManager assetManager,
//            Camera cam
//    ) {
//        this.fpp = fpp;
//        this.renderManager = renderManager;
//        this.assetManager = assetManager;
//        this.cam = cam;
//        this.OutlineWidth = OutlineWidth;
//        this.OutLineColor = OutLineColor;
//    }

    public float getOutlineWidth() {
        return OutlineWidth;
    }

    public void setOutlineWidth(float outlineWidth) {
        OutlineWidth = outlineWidth;
    }

    public ColorRGBA getOutLineColor() {
        return OutLineColor;
    }

    public void setOutLineColor(ColorRGBA outLineColor) {
        this.OutLineColor = outLineColor;
    }

    public String getOutlineProMatPath() {
        return outlineProMatPath;
    }

    public void setOutlineProMatPath(String outlineProMatPath) {
        this.outlineProMatPath = outlineProMatPath;
    }

    public String getOutlinePreMatPath() {
        return outlinePreMatPath;
    }

    public void setOutlinePreMatPath(String outlinePreMatPath) {
        this.outlinePreMatPath = outlinePreMatPath;
    }

    public void deselect(Spatial model) {
        hideOutlineFilterEffect(model);

        model.setUserData("OutlineSelected", false);
    }

    public void select(Spatial model) {
        showOutlineFilterEffect(model, OutlineWidth, OutLineColor, backGroundColor);

        System.out.println("选中的物体：" + model);

        model.updateGeometricState();
        model.setUserData("OutlineSelected", true);
    }

    public boolean isSelected(Spatial model) {
        return model.getUserData("OutlineSelected") != null && ((Boolean) model.getUserData("OutlineSelected"));
    }

    private void hideOutlineFilterEffect(Spatial model) {
        // 取消纯色描边
//        OutlineFilter outlineFilter = model.getUserData("OutlineFilter");
//        if (outlineFilter != null) {
//            outlineFilter.setEnabled(false);
//            outlineFilter.getOutlinePreFilter().setEnabled(false);
//        }

        // 取消渐变描边
        OutlineProFilter2 outlineFilter = model.getUserData("OutlineFilter");
//        FXAAFilter fxaaFilter = model.getUserData("FXAAFilter");
//        if (outlineFilter != null && fxaaFilter != null) {
//        System.out.println("取消渐变描边");
        if (outlineFilter != null) {

            outlineFilter.setEnabled(false);
            outlineFilter.getOutlinePreFilter().setEnabled(false);
//            fxaaFilter.setEnabled(false);
        }
    }

    private void showOutlineFilterEffect(Spatial model, float width, ColorRGBA outlineColor, ColorRGBA backGroundColor) {
        Filter outlineFilter = model.getUserData("OutlineFilter");
//        Filter fxaaFilter = model.getUserData("FXAAFilter");
        OutlinePreFilter2 outlinePreFilter2;
//        if (outlineFilter == null && fxaaFilter == null) {
        if (outlineFilter == null ) {
            ViewPort outlineViewport = renderManager.createPreView("outlineViewport", cam.clone());
            FilterPostProcessor outlineFpp = new FilterPostProcessor(assetManager);
            outlineFpp.setNumSamples(16);

            outlinePreFilter2 = new OutlinePreFilter2(outlinePreMatPath);
            outlineFpp.addFilter(outlinePreFilter2);
            outlineViewport.attachScene(model);
            outlineViewport.addProcessor(outlineFpp);
            outlineViewport.setClearFlags(true, true, false);
//            outlineViewport.setBackgroundColor(new ColorRGBA(0f, 0.64f, 0.54f, 1f));

//            viewport.clearScenes();
//            viewport.attachScene(model);
//            viewport.addProcessor(outlineFpp);
//            viewport.setClearFlags(true, true, false);
//            viewport.setBackgroundColor(new ColorRGBA(0, 0, 0, 0));

//            for (SceneProcessor processor : outlineViewport.getProcessors()) {
//                if (processor instanceof FilterPostProcessor) {
//                    System.out.println(((FilterPostProcessor)processor).getNumSamples());
//                }
//            }

            // 纯色描边
//            outlineFilter = new OutlineFilter(outlinePreFilter, outlineMatPath);
//            ((OutlineFilter) outlineFilter).setOutlineColor(outlineColor);
//            ((OutlineFilter) outlineFilter).setBackgroundColor(backGroundColor);
//            ((OutlineFilter) outlineFilter).setOutlineWidth(width);
//            model.setUserData("OutlineFilter", outlineFilter);

            // 渐变描边
            outlineFilter = new OutlineProFilter2(outlinePreFilter2, outlineProMatPath);
            ((OutlineProFilter2) outlineFilter).setOutlineColor(outlineColor);
            ((OutlineProFilter2) outlineFilter).setBackGroundColor(backGroundColor);
            ((OutlineProFilter2) outlineFilter).setOutlineWidth(width);
            model.setUserData("OutlineFilter", outlineFilter);

            fpp.addFilter(outlineFilter);

//            for (SceneProcessor processor : viewport.getProcessors()) {
//                if (processor instanceof FilterPostProcessor) {
//                    System.out.println(((FilterPostProcessor)processor).getNumSamples());
//                }
//            }

//            fxaaFilter = new FXAAFilter();
//            ((FXAAFilter)fxaaFilter).setSubPixelShift(1.0f / 8.0f);
//            ((FXAAFilter)fxaaFilter).setReduceMul(1.0f / 28.0f);
//            ((FXAAFilter)fxaaFilter).setVxOffset(0.0f);
//            ((FXAAFilter)fxaaFilter).setSpanMax(20.0f);
//            ((FXAAFilter)fxaaFilter).setEnabled(true);
//            outlineFpp.addFilter(fxaaFilter);
        } else {
            outlineFilter.setEnabled(true);

            // 纯色描边
//            ((OutlineFilter) outlineFilter).getOutlinePreFilter().setEnabled(true);

            // 渐变描边
            ((OutlineProFilter2) outlineFilter).getOutlinePreFilter().setEnabled(true);
        }
    }


}
