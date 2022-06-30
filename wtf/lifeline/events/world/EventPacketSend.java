/*
 * Decompiled with CFR 0_132.
 */
package wtf.lifeline.events.world;

import wtf.lifeline.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketSend
extends EventCancellable {
    public Packet packet;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

