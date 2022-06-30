package wtf.lifeline.event.impl.render;

import wtf.lifeline.event.Event;

public class ShaderEvent extends Event {

    public final boolean bloom;
    public ShaderEvent(boolean bloom){
        this.bloom = bloom;
    }
}
