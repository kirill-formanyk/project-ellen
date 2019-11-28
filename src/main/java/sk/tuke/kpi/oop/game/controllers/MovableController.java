package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Map <Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );

    private Movable actor;
    private Move<Movable> moveAction;
    private Set<Direction> pressedKeysDirections;

    public MovableController (Movable actor) {
        this.actor = actor;
        this.pressedKeysDirections = new HashSet<>();
    }

    @Override
    public void keyPressed (Input.@NotNull Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeysDirections.add(keyDirectionMap.get(key));
            updateMovementDirection();
        }
    }

    @Override
    public void keyReleased (Input.@NotNull Key key) {
        if (keyDirectionMap.containsKey(key)) {
            pressedKeysDirections.remove(keyDirectionMap.get(key));
            updateMovementDirection();
        }
    }

    private void updateMovementDirection () {
        stopMovement();

        Direction newMovementDirection = Direction.NONE;
        for (Direction direction : pressedKeysDirections) {
            newMovementDirection = newMovementDirection.combine(direction);
        }

        if (!newMovementDirection.equals(Direction.NONE)) startMovement(newMovementDirection);
    }

    private void stopMovement () {
        if (Objects.nonNull(moveAction)) moveAction.stop();
    }

    private void startMovement (Direction direction) {
        moveAction = new Move<>(direction, Float.MAX_VALUE);
        moveAction.scheduleFor(actor);
    }
}
