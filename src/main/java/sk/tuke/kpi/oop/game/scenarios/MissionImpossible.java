package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class MissionImpossible implements SceneListener {
    private Ripley ellen;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        MovableController movableController = new MovableController(ellen);
        scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ellen);
        scene.getInput().registerListener(keeperController);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

        scene.getGame().getOverlay().drawText("| Energy: " + ellen.getEnergy(), 100, yTextPos);
        scene.getGame().getOverlay().drawText("| Ammo: " + ellen.getAmmo(), 250, yTextPos);
        scene.getGame().pushActorContainer(ellen.getBackpack());
    }

    public static class Factory implements ActorFactory {
        @Nullable
        @Override
        public Actor create(String type, String name) {
            switch (name) {
                case "ellen" : return new Ripley();
                case "access card" : return null;
                case "energy" : return new Energy();
                case "locker" : return null;
                case "door" : return new Door();
                case "ventilator" : return null;
                default: return null;
            }
        }
    }
}
