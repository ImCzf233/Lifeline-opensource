package wtf.lifeline.module.modules.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.value.Numbers;
import wtf.lifeline.utils.TimerUtil;

import java.awt.*;

public class ChestStealer extends Module {

    private Numbers<Double> delay = new Numbers<Double>("Delay", "delay", 50.0, 0.0, 1000.0, 10.0);
    private TimerUtil timer = new TimerUtil();

    public ChestStealer() {
        super("ChestStealer", new String[]{"cheststeal", "chests", "stealer"}, Category.Player);
        this.addValues(this.delay);
        this.setColor(new Color(218, 97, 127).getRGB());
    }

    @EventTarget
    private void onUpdate(EventUpdate event) {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)this.mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasReached(this.delay.getValue())) {
                    this.mc.playerController.windowClick(container.windowId, i, 0, 1, this.mc.thePlayer);
                    this.timer.reset();
                }
                ++i;
            }
            if (this.isEmpty()) {
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    private boolean isEmpty() {
        if (this.mc.thePlayer.openContainer != null && this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)this.mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() != null) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }
}

