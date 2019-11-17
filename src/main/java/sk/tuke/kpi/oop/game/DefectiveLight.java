package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.Random;

public class DefectiveLight extends Light {

    public DefectiveLight() {
        throwBug();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::throwBug)).scheduleFor(this);
    }

    private void throwBug () {
        if (generateRandomNumber() == 6) {
            toggle();
        }
    }

    private int generateRandomNumber() {
        return new Random().nextInt(25) + 1;
    }
}
