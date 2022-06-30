package wtf.lifeline.module.modules.move;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.module.value.Numbers;

public class Timer extends Module {
    public Timer() {
        super("Timer", new String[]{"GameSpeed"}, Category.Move);
        addValues(this.timerSpee);
    }

    public static Numbers<Float> timerSpee = new Numbers<Float>("TimerSpeed", "TimerSpeed", 1f, 0.5f, 2f, 0.25f);

    @EventTarget
    public void onUpdate(EventUpdate e) {
        mc.timer.timerSpeed = timerSpee.getValue();
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }
}
