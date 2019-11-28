package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;

public class FirstSteps implements SceneListener {
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley player = new Ripley();
        scene.addActor(player, 0, 150);

        Alien alien = new Alien();
        scene.addActor(alien, -50, 50);

        MovableController movableController = new MovableController(alien);
        scene.getInput().registerListener(movableController);
    }
}
