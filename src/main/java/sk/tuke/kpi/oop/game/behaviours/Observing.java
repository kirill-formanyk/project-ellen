package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;
import java.util.function.Predicate;

public class Observing <A extends Actor, T> implements Behaviour<A> {
    private final Topic<T> topic;
    private final Predicate<T> predicate;
    private final Behaviour<A> delegate;

    public Observing (Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }

    @Override
    public void setUp(A actor) {
        if (Objects.isNull(actor)) return;

        actor.getScene().getMessageBus().subscribe(topic,
            topicActor -> {
                if (predicate.test(topicActor)) delegate.setUp(actor);
        });
    }
}
