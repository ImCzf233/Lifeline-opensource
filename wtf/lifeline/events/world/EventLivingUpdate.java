package wtf.lifeline.events.world;


import wtf.lifeline.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventLivingUpdate implements Event {
    public Entity entity;

    public EventLivingUpdate(Entity targetEntity) {
        this.entity = targetEntity;
    }

    public Entity getEntity() {
        return entity;
    }


}
