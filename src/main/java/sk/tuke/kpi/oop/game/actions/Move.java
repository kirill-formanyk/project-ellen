package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.gamelib.map.SceneMap;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move <A extends Movable> implements Action<A> {
    private A actor;
    private Direction direction;
    private float duration;
    private boolean isDone;
    private boolean isFirstCall;

    public Move (Direction direction) {
        this.direction = direction;
        this.duration = 0;
        this.isDone = false;
        this.isFirstCall = true;
    }

    public Move (Direction direction, float duration) {
        this(direction);
        this.duration = duration;
    }

    @Nullable
    @Override
    public A getActor() {
        return this.actor;
    }

    @Override
    public void setActor (A actor) {
        this.actor = actor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean isDone () {
        return isDone;
    }

    @Override
    public void execute (float deltaTime) {
        if (Objects.nonNull(actor) && !isDone()) {
            if (isFirstCall) {
                actor.startedMoving(direction);
                isFirstCall = false;
            }

            duration -= deltaTime;
            if (duration > 0) {
                int currentPlayerX = actor.getPosX();
                int currentPlayerY = actor.getPosY();

                int newPlayerX = actor.getPosX() + direction.getDx() * actor.getSpeed();
                int newPlayerY = actor.getPosY() + direction.getDy() * actor.getSpeed();
                actor.setPosition(newPlayerX, newPlayerY);

                SceneMap map = Objects.requireNonNull(actor.getScene()).getMap();
                if (map.intersectsWithWall(actor)) {
                    actor.setPosition(currentPlayerX, currentPlayerY);
                }
            } else {
                stop();
            }
        }
    }

    public void stop () {
        if (Objects.nonNull(actor)) {
            actor.stoppedMoving();
            isDone = true;
        }
    }

    @Override
    public void reset () {
        actor.stoppedMoving();
        isDone = false;
        isFirstCall = true;
    }
}
