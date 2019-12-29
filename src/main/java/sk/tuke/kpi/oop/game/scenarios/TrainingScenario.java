package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class TrainingScenario implements SceneListener {
    private Ripley ellen;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ellen.showState(scene);
    }

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "player" : return new Ripley();
                default: return null;
            }
        }

    }


}
