package wtf.lifeline.events.world;

import wtf.lifeline.eventapi.events.Event;

public class EventPreStep implements Event {
	private float height;

	public EventPreStep(float height) {
		this.height = height;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
