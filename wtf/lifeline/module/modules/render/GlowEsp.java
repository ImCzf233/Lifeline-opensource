package wtf.lifeline.module.modules.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import wtf.lifeline.Client;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.utils.render.ESPUtil;
import wtf.lifeline.utils.render.RenderUtil;
import wtf.lifeline.utils.render.ShaderUtil;
import wtf.lifeline.event.EventListener;
import wtf.lifeline.event.impl.render.Render2DEvent;
import wtf.lifeline.event.impl.render.Render3DEvent;
import wtf.lifeline.utils.animations.Animation;
import wtf.lifeline.utils.animations.impl.DecelerateAnimation;
import wtf.lifeline.utils.misc.MathUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import wtf.lifeline.settings.impl.ModeSetting;
import wtf.lifeline.settings.impl.BooleanSetting;
import wtf.lifeline.settings.impl.ColorSetting;

import wtf.lifeline.settings.impl.NumberSetting;
import wtf.lifeline.settings.impl.KeybindSetting;
import wtf.lifeline.settings.impl.MultipleBoolSetting;




import java.awt.*;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.renderer.OpenGlHelper.glUniform1;


public class GlowEsp extends Module {

    private final ModeSetting colorMode = new ModeSetting("Color Mode", "Sync", "Sync", "Custom");
    private final ColorSetting glowColor = new ColorSetting("Glow Color", Client.instance.getClientColor());
    private final NumberSetting radius = new NumberSetting("Radius", 4, 30, 2, 2);
    private final NumberSetting exposure = new NumberSetting("Exposure", 2.2, 3.5, .5, .1);
    private final BooleanSetting seperate = new BooleanSetting("Seperate Texture", false);
    private final MultipleBoolSetting validEntities = new MultipleBoolSetting("Entities",
            new BooleanSetting("Players", true),
            new BooleanSetting("Animals", true),
            new BooleanSetting("Mobs", true));



    public GlowEsp() {
        super("GlowESP", new String[]{}, Category.RENDER);
        glowColor.addParent(colorMode, modeSetting -> modeSetting.is("Custom"));
        addSettings(colorMode, glowColor, radius, exposure, seperate, validEntities);
    }


    public static boolean renderNameTags = true;
    private final ShaderUtil outlineShader = new ShaderUtil("Tenacity/Shaders/outline.frag");
    private final ShaderUtil glowShader = new ShaderUtil("Tenacity/Shaders/glow.frag");

    public Framebuffer framebuffer;
    public Framebuffer outlineFrameBuffer;
    public Framebuffer glowFrameBuffer;
    private final Frustum frustum = new Frustum();

    private final List<Entity> entities = new ArrayList<>();

    public static Animation fadeIn;

    @Override
    public void onEnable() {
        super.onEnable();
        fadeIn = new DecelerateAnimation(250, 1);
    }

    public void createFrameBuffers() {
        framebuffer = RenderUtil.createFrameBuffer(framebuffer);
        outlineFrameBuffer = RenderUtil.createFrameBuffer(outlineFrameBuffer);
        glowFrameBuffer = RenderUtil.createFrameBuffer(glowFrameBuffer);
    }


    private final EventListener<Render3DEvent> event3D = e -> {
        createFrameBuffers();
        collectEntities();
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        renderEntities(e.getTicks());
        framebuffer.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.disableLighting();
    };

    private final EventListener<Render2DEvent> event2D = e -> {

        ScaledResolution sr = new ScaledResolution(mc);
        if (framebuffer != null && outlineFrameBuffer != null && entities.size() > 0) {
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

            outlineFrameBuffer.framebufferClear();
            outlineFrameBuffer.bindFramebuffer(true);
            outlineShader.init();
            setupOutlineUniforms(0, 1);
            RenderUtil.bindTexture(framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            outlineShader.init();
            setupOutlineUniforms(1, 0);
            RenderUtil.bindTexture(framebuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            outlineShader.unload();
            outlineFrameBuffer.unbindFramebuffer();

            GlStateManager.color(1, 1, 1, 1);
            glowFrameBuffer.framebufferClear();
            glowFrameBuffer.bindFramebuffer(true);
            glowShader.init();
            setupGlowUniforms(1, 0);
            RenderUtil.bindTexture(outlineFrameBuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            glowShader.unload();
            glowFrameBuffer.unbindFramebuffer();

            mc.getFramebuffer().bindFramebuffer(true);
            glowShader.init();
            setupGlowUniforms(0, 1);
            if (seperate.isEnabled()) {
                GL13.glActiveTexture(GL13.GL_TEXTURE16);
                RenderUtil.bindTexture(framebuffer.framebufferTexture);
            }
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            RenderUtil.bindTexture(glowFrameBuffer.framebufferTexture);
            ShaderUtil.drawQuads();
            glowShader.unload();

        }

    };


    public void setupGlowUniforms(float dir1, float dir2) {
        Color color = Color.WHITE;
        glowShader.setUniformi("texture", 0);
        if (seperate.isEnabled()) {
            glowShader.setUniformi("textureToCheck", 16);
        }
        glowShader.setUniformf("radius", radius.getValue().floatValue());
        glowShader.setUniformf("texelSize", 1.0f / mc.displayWidth, 1.0f / mc.displayHeight);
        glowShader.setUniformf("direction", dir1, dir2);
        glowShader.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        glowShader.setUniformf("exposure", (float) (exposure.getValue().floatValue() * fadeIn.getOutput()));
        glowShader.setUniformi("avoidTexture", seperate.isEnabled() ? 1 : 0);

        final FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
        for (int i = 1; i <= radius.getValue().floatValue(); i++) {
            buffer.put(MathUtils.calculateGaussianValue(i, radius.getValue().floatValue() / 2));
        }
        buffer.rewind();

        glUniform1(glowShader.getUniform("weights"), buffer);
    }


    public void setupOutlineUniforms(float dir1, float dir2) {
        Color color = Color.WHITE;
        outlineShader.setUniformi("texture", 0);
        outlineShader.setUniformf("radius", radius.getValue().floatValue() / 1.5f);
        outlineShader.setUniformf("texelSize", 1.0f / mc.displayWidth, 1.0f / mc.displayHeight);
        outlineShader.setUniformf("direction", dir1, dir2);
        outlineShader.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    }

    public void renderEntities(float ticks) {
        entities.forEach(entity -> {
            renderNameTags = false;
            mc.getRenderManager().renderEntityStaticNoShadow(entity, ticks, false);
            renderNameTags = true;
        });
    }

    public int getColor() {
        if(colorMode.is("Custom")){
            Color color = Color.WHITE;
        }
        return this.getColor();
    }

    public void collectEntities() {
        entities.clear();
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (!ESPUtil.isInView(entity)) continue;
            if (entity == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) continue;
        }
    }


}

