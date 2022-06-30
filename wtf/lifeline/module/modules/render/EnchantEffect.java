/*
 * Decompiled with CFR 0.136.
 */
package wtf.lifeline.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventTick;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.value.Option;

public class EnchantEffect extends Module {
	public static Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", true);
	public static int color;

	public EnchantEffect() {
		super("EnchantEffect", new String[] { "enchant" }, Category.RENDER);
		this.addValues(rainbow);
		this.setRemoved(true);
	}

	@EventTarget
	public void onTick(EventTick e) {
		int rainbowTick = 0;
		Color rainbow = new Color(
				Color.HSBtoRGB((float) ((double) Minecraft.getMinecraft().thePlayer.ticksExisted / 50.0
						+ Math.sin((double) rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
		color = rainbow.getRGB();
	}
}
