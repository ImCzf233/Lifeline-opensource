package wtf.lifeline.events.world;


import wtf.lifeline.eventapi.events.Event;

public class EventPostStep implements Event {
	private float height;

	public EventPostStep(float height) {
		this.height = height;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
