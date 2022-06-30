package wtf.lifeline.events.world;

import wtf.lifeline.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacketReceive extends EventCancellable {

    public Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
