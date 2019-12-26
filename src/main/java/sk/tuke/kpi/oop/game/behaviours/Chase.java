package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Chase implements Behaviour<Movable> {
    private Movable target;
    private Movable chaser;
    private Class<? extends Movable> targetType;
    private AtomicReference<Move<Movable>> moveAction = null;

    public Chase (Class<? extends Movable> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void setUp(Movable actor) {
        this.chaser = actor;
        resolveTarget();
        searchAndDestroy();
    }

    private void searchAndDestroy () {
        if (Objects.isNull(chaser) || Objects.isNull(target)) return;
        new Loop<>(new Invoke<>(this::startMovement)).scheduleFor(chaser);
    }

    private void startMovement () {
        if (Objects.isNull(moveAction)) {
            initializeMovementAction();
            return;
        }

        moveAction.get().stop();
        moveAction.set(new Move<>(resolveDirection(), Float.MAX_VALUE));
        moveAction.get().scheduleFor(chaser);
    }

    private void initializeMovementAction () {
        moveAction = new AtomicReference<>(new Move<>(Direction.fromCoordinates(resolveDx(), resolveDy()), Float.MAX_VALUE));
    }

    private void resolveTarget () {
        this.target = this.chaser.getScene().getFirstActorByType(targetType);
    }

    private Direction resolveDirection () {
        return Direction.fromCoordinates(resolveDx(), resolveDy());
    }

    private int resolveDy () {
        return (target.getPosY() > chaser.getPosY()) ? 1 : -1;
    }

    private int resolveDx () {
        return (target.getPosX() > chaser.getPosX()) ? 1 : -1;
    }
}
