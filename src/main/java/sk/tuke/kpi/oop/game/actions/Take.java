package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Take <X extends Keeper> extends AbstractAction<X> {
    @Override
    public void execute (float deltaTime) {
        Keeper executingActor = getActor();

        if (!isDone() && Objects.nonNull(executingActor)) {
            Scene currentScene = executingActor.getScene();
            assert currentScene != null : "Current Scene is null!";

            Collectible item = currentScene.getFirstActorByType(Collectible.class);
            if (Objects.nonNull(item) && executingActor.intersects(item)) {
                try {
                    executingActor.getBackpack().add(item);
                    currentScene.removeActor(item);
                } catch (IllegalStateException exception) {
                    currentScene.getOverlay().drawText(exception.getMessage(), -100, 0).showFor(2);
                }
            }

            setDone(true);
        }
    }
}
