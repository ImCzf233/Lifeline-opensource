/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.events.misc;

import wtf.lifeline.eventapi.events.Event;

public class EventKey
        implements Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

