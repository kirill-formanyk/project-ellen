package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Drop <X extends Keeper> extends AbstractAction<X> {
    @Override
    public void execute(float deltaTime) {
        Keeper executingActor = getActor();

        if (Objects.nonNull(executingActor) && !isDone()) {
            Scene currentScene = executingActor.getScene();
            assert currentScene != null : "Current Scene is null!";

            Collectible item = executingActor.getBackpack().peek();
            if (Objects.nonNull(item)) {
                executingActor.getBackpack().remove(item);
                currentScene.addActor(item, executingActor.getPosX(), executingActor.getPosY());
            }

            setDone(true);
        }
    }
}
