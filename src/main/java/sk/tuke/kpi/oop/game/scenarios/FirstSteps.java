package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class FirstSteps implements SceneListener {
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley player = new Ripley();
        scene.addActor(player, 0, 0);
    }
}
