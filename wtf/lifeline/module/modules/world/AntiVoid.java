/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.module.modules.world;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.misc.EventPacket;
import wtf.lifeline.events.world.EventTick;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.utils.PacketUtils;
import wtf.lifeline.utils.move.PlayerUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AntiVoid
extends Module {
    public AntiVoid() {
        super("AntiVoid", new String[]{"novoid", "antifall"}, Category.World);
        this.setColor(new Color(223, 233, 233).getRGB());
    }

    double x, y, z, x1, x2, y1, y2, z1, z2;
    boolean g;
    int ticks;

    List<Packet> queue = new ArrayList();

    public void onEnable() {
        ticks = 0;
    }

    @EventTarget
    void onUpdate(EventUpdate e) {
        if (mc.thePlayer.onGround) {
            x1 = mc.thePlayer.posX;
            y1 = mc.thePlayer.posY;
            z1 = mc.thePlayer.posZ;
        }
        if (PlayerUtils.isBlockUnder()) {
            x = mc.thePlayer.posX;
            y = mc.thePlayer.posY;
            z = mc.thePlayer.posZ;
            g = mc.thePlayer.onGround;
        }
        if (mc.isSingleplayer()) {
            setSuffix("SinglePlayer");
        }
        else {
            setSuffix("Multiplayer");
        }
    }

    @EventTarget
    void onTick(EventTick e) {
        if (!PlayerUtils.isBlockUnder()) {
            ticks ++;
        }
        else {
            ticks = 0;
        }
        if (ticks == 30) {
            if (mc.isSingleplayer()) {
                mc.thePlayer.setPositionAndUpdate(x, y, z);
            }
            else {
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            }
        }
    }

    @EventTarget
    void onPacket(EventPacket e) {
        if (0 < ticks && ticks < 30) {
            if (!mc.isSingleplayer()) {
                if (e.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition){
                    e.setCancelled(true);
                }
            }
        } else if (ticks >= 30) {
            if (e.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
                e.setCancelled(true);
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            }
        }
    }
}

