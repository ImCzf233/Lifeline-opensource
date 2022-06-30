/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.events.misc;

import wtf.lifeline.eventapi.events.callables.EventCancellable;
import net.minecraft.entity.player.EntityPlayer;

public class EventInventory
        extends EventCancellable {
    private final EntityPlayer player;

    public EventInventory(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }
}

