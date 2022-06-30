package wtf.lifeline.module.modules.move;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.misc.EventPacket;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.modules.combat.KillAura;
import wtf.lifeline.module.value.Mode;
import wtf.lifeline.module.value.Numbers;
import wtf.lifeline.module.value.Option;
import wtf.lifeline.utils.TimerUtil;
import wtf.lifeline.utils.move.MoveUtils;

public class Speed extends Module {

    public Mode<Enum> mode = new Mode<Enum>("Mode", "Mode", SMode.values(), SMode.Hypixel1);
    public Numbers<Double> timerSpeed = new Numbers("Timer", "Timer", 1.0, 0.1, 1.3, 0.05);
    public Option<Boolean> noAtkTimer = new Option<Boolean>("NoAttackTimer", "NoAttackTimer", true);
    TimerUtil timer = new TimerUtil();
    int counter;

    public Speed() {
        super("Speed", new String[]{}, Category.Move);
        addValues(mode, timerSpeed, noAtkTimer);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        counter = 0;
    }

    @Override
    public void onDisable() {
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.onDisable();
        timer.reset();
    }

    @EventTarget
    public void onMotion(EventUpdate e) {
        this.setSuffix(mode.getModeAsString());
        if (mode.getValue().equals(SMode.Hypixel1)) {
            if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                if (MoveUtils.isMoving()) {
                    if (mc.thePlayer.moveForward > 0.0f) {
                        MoveUtils.strafe(MoveUtils.getBaseMoveSpeed() * 1.6 + Math.random() / 100);
                    }
                    else {
                        MoveUtils.strafe(MoveUtils.getBaseMoveSpeed() * 1 + Math.random() / 100);
                    }
                    Minecraft.thePlayer.motionY = 0.418;
                }
            }
            if (MoveUtils.isMoving()) {
                if (!noAtkTimer.getValue() || KillAura.target == null) {
                    net.minecraft.util.Timer.timerSpeed = timerSpeed.getValue().floatValue();
                }
                else {
                    Timer.timerSpeed = 1f;
                }
            }
            MoveUtils.strafe();
        }
        if (mode.getValue().equals(SMode.CSGO)) {
            if (mc.thePlayer.onGround && MoveUtils.isMoving()) {
                counter++;
                mc.thePlayer.motionY = 0.41999999999999999999999;
                MoveUtils.strafe(MoveUtils.getBaseMoveSpeed() + counter * 0.05);
            }
            if (!MoveUtils.isMoving() || mc.thePlayer.isCollidedHorizontally) {
                counter = 0;
            }
            MoveUtils.strafe();
        }
    }

    @EventTarget
    void onPacket(EventPacket e) {
    }

    enum SMode {
        Hypixel1, CSGO
    }
}