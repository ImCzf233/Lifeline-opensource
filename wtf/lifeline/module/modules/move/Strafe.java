package wtf.lifeline.module.modules.move;

import wtf.lifeline.events.EventTarget;
import wtf.lifeline.events.world.EventUpdate;
import wtf.lifeline.module.Category;
import wtf.lifeline.module.Module;
import wtf.lifeline.utils.PlayerUtil;
import wtf.lifeline.Client;

public class Strafe extends Module {

    public Strafe() {
        super("Strafe",new String[]{}, Category.Move);
    }

    @EventTarget
    void onUpdate(EventUpdate event) {
        if (
                Client.instance.getModuleManager().getModuleByName("Speed").isEnabled()) return;
        PlayerUtil.doStrafe();
    }
}
