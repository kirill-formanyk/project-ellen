package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.Flame;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.ghosts.RedFlameGhost;
import sk.tuke.kpi.oop.game.ghosts.WhiteGhost;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

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
        private Door.Orientation doorOrientation;

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            initializeOrientation(type);

            switch (name) {
                case "ellen" : return new Ripley();
                case "ammo" : return Flame.create(Flame.Color.LIGHTBLUE);
                case "alien" : return new WhiteGhost(new RandomlyMoving());
                case "back door" :
                case "front door":
                case "exit door" : return new Door(name, doorOrientation);
                case "energy" : return Flame.create(Flame.Color.GREEN);
                case "alien mother" : return new RedFlameGhost(new RandomlyMoving());
                default: return null;
            }
        }

        private void initializeOrientation(String type) {
            doorOrientation = (Objects.nonNull(type) && type.equals("vertical")) ? Door.Orientation.VERTICAL : Door.Orientation.HORIZONTAL;
        }
    }


}
