package wtf.lifeline.events.rendering;


import wtf.lifeline.eventapi.events.Event;

public class EventDrawText implements Event {
   public String text;

   public EventDrawText(String text) {
      this.text = text;
   }
}
