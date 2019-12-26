package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class RandomlyMoving implements Behaviour<Movable> {

    @Override
    public void setUp(Movable actor) {
        startActorMoveRandomly(actor);
    }

    private void startActorMoveRandomly(Movable actor) {
        if (Objects.isNull(actor)) return;

        AtomicReference<Move<Movable>> moveAlienAction = new AtomicReference<>(new Move<>(getRandomDirection(), Float.MAX_VALUE));
        moveAlienAction.get().scheduleFor(actor);

        new Loop<>(
            new ActionSequence<>(
                new Wait<>(1),
                new Invoke<>(() -> {
                    moveAlienAction.get().stop();
                    moveAlienAction.set(new Move<>(getRandomDirection(), Float.MAX_VALUE));
                    moveAlienAction.get().scheduleFor(actor);
                })
            )
        ).scheduleFor(actor);
    }

    private Direction getRandomDirection () {
        Direction[] availableDirections = Direction.values();
        int randomDirectionIndex = new Random().nextInt(availableDirections.length);

        Direction direction = availableDirections[randomDirectionIndex];
        if (direction.equals(Direction.NONE)) return getRandomDirection();
        return direction;
    }
}
