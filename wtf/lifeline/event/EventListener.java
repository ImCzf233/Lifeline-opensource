package wtf.lifeline.event;

public interface EventListener<T> {
    void call(T event);
}
