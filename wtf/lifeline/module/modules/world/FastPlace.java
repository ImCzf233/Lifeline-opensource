/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.module.modules.world;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventTick;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

import java.awt.*;

public class FastPlace
extends Module {
    public FastPlace() {
        super("FastPlace", new String[]{"fplace", "fc"}, Category.World);
        this.setColor(new Color(226, 197, 78).getRGB());
    }

    @EventTarget
    private void onTick(EventTick e) {
        this.mc.rightClickDelayTimer = 0;
    }
}

