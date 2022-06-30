/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.module.modules.world;

import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

import java.awt.Color;

public class SafeWalk
extends Module {
    public SafeWalk() {
        super("SafeWalk", new String[]{"eagle", "parkour"}, Category.World);
        this.setColor(new Color(198, 253, 191).getRGB());
    }
}

