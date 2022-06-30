package wtf.lifeline.module.modules.move;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;

public class ikun extends Module {
    public ikun() {
        super("ikun", new String[]{}, Category.Move);
    }
    @EventTarget
    public boolean onUpdate(EventUpdate e) {
        if (mc.isSingleplayer()) {
            System.out.println("你干嘛呵呵哎呦~~~~~~~~~~~~");
            return true;
        }
        return false;
    }
}
