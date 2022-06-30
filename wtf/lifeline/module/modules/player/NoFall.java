
package wtf.lifeline.module.modules.player;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.misc.EventPacket;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.utils.move.PlayerUtils;
import wtf.lifeline.utils.PacketUtils;

public class NoFall
extends Module {

    public NoFall() {
        super("Nofall", new String[]{}, Category.Player);
    }

    private double getLastTickYDistance() {
        return Math.hypot(mc.thePlayer.posY - mc.thePlayer.prevPosY, mc.thePlayer.posY - mc.thePlayer.prevPosY);
    }

    @EventTarget
    public void onPacket(EventPacket event) {
        if (mc.thePlayer.posY > 0 && mc.thePlayer.fallDistance >= 2 && mc.thePlayer.lastTickPosY - mc.thePlayer.posY > 0 && mc.thePlayer.motionY != 0) {
            if (!PlayerUtils.isBlockUnder() || mc.thePlayer.fallDistance > 255 || !PlayerUtils.isBlockUnder() && mc.thePlayer.fallDistance > 50) {
                return;
            }

            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();

                if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    event.setCancelled(true);
                }
            }

            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();

                if (packet.isMoving() && packet.rotating) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(packet.x, packet.y, packet.z, packet.isOnGround()));
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onMotion(EventUpdate event) {
        setSuffix("Hypixel");
        if (event.isPre()) {
            if (mc.thePlayer.posY > 0 && mc.thePlayer.lastTickPosY - mc.thePlayer.posY > 0 && mc.thePlayer.motionY != 0 && mc.thePlayer.fallDistance >= 2.5) {
                if (!PlayerUtils.isBlockUnder() || mc.thePlayer.fallDistance > 255 || !PlayerUtils.isBlockUnder() && mc.thePlayer.fallDistance > 50) {
                    return;
                }

                if (mc.thePlayer.fallDistance > 10 || mc.thePlayer.ticksExisted % 2 == 0) {
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer(true));
                    mc.timer.timerSpeed = 1.0F;
                }
            }
        }
    }
}
