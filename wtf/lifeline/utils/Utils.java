package wtf.lifeline.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public interface Utils {
    Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fr = mc.fontRendererObj;
}
