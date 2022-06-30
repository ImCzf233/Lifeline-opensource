package wtf.lifeline.events.world;

import wtf.lifeline.eventapi.events.callables.EventCancellable;

public class EventSafeWalk extends EventCancellable {

    public EventSafeWalk(boolean safeWalking) {
        setCancelled(safeWalking);
    }
}
