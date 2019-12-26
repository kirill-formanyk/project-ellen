package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

import java.util.Objects;

public class Shift <X extends Keeper> extends AbstractAction<X> {
    @Override
    public void execute(float deltaTime) {
        Keeper executingActor = getActor();
        if (Objects.nonNull(executingActor) && !isDone()) {
            executingActor.getBackpack().shift();
        }
        setDone(true);
    }
}
