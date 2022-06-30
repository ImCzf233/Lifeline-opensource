/*
 * Decompiled with CFR 0.136.
 */
package wtf.lifeline.module.modules.render;

import wtf.lifeline.gui.clickgui.GuiClickUI;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClickGui extends Module {
	public ClickGui() {
		super("ClickGui", new String[] { "clickui" }, Category.RENDER);
		this.setRemoved(true);
	}

	public static int memoriseX = 30;
	public static int memoriseY = 30;
	public static int memoriseWheel = 0;
	public static List<Module> memoriseML = new CopyOnWriteArrayList<>();
	public static Category memoriseCatecory = null;

	@Override
	public void onEnable() {
		mc.displayGuiScreen(new GuiClickUI());
		GuiClickUI.setX(memoriseX);
		GuiClickUI.setY(memoriseY);
		GuiClickUI.setWheel(memoriseWheel);
		GuiClickUI.setInSetting(memoriseML);
		if (memoriseCatecory != null)
			GuiClickUI.setCategory(memoriseCatecory);
		this.setEnabled(false);
	}
}
