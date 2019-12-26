package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.ActorFactory;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {
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
                case "ammo" : return new Ammo();
                case "alien" : return createAlien(type);
                case "back door" :
                case "front door":
                case "exit door" : return new Door(name, doorOrientation);
                case "energy" : return new Energy();
                case "alien mother" : return createAlienMother();
                default: return null;
            }
        }

        private Alien createAlien (String type) {
            if (Objects.isNull(type) || type.equals("running")) return new Alien(new RandomlyMoving());

            if (type.equals("waiting1")) return new Alien(
                new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("front door"),
                    new RandomlyMoving()
                )
            );

            if (type.equals("waiting2")) return new Alien(
                new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("back door"),
                    new RandomlyMoving()
                )
            );

            return new Alien(null);
        }

        private AlienMother createAlienMother () {
            return new AlienMother(
                new Observing<>(
                    Door.DOOR_OPENED,
                    door -> door.getName().equals("back door"),
                    new RandomlyMoving()
                )
            );
        }

        private void initializeOrientation(String type) {
            doorOrientation = (Objects.nonNull(type) && type.equals("vertical")) ? Door.Orientation.VERTICAL : Door.Orientation.HORIZONTAL;
        }
    }
}
