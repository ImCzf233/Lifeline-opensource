/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.module.modules.render;

import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

import java.awt.Color;

public class NoRender
extends Module {
    public NoRender() {
        super("NoRender", new String[]{"noitems"}, Category.RENDER);
        this.setColor(new Color(166, 185, 123).getRGB());
    }
}

