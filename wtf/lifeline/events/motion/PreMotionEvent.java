package wtf.lifeline.events.motion;

import wtf.lifeline.eventapi.events.Event;


public final class PreMotionEvent implements Event {
    private float yaw, pitch;
    private boolean ground;
    private double x, y, z;
}
