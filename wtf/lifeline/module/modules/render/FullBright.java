/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.module.modules.render;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventTick;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

import java.awt.Color;

public class FullBright
extends Module {
    private float old;

    public FullBright() {
        super("FullBright", new String[]{"fbright", "brightness", "bright"}, Category.RENDER);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.old = this.mc.gameSettings.gammaSetting;
    }

    @EventTarget
    private void onTick(EventTick e) {
        this.mc.gameSettings.gammaSetting = 1.5999999E7f;
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = this.old;
    }
}

