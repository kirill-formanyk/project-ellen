package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.items.Repairable;

import java.util.Objects;
import java.util.Random;

public class DefectiveLight extends Light implements Repairable {
    private Disposable loop;
    private boolean isRepaired;

    public DefectiveLight() {
        throwBug();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        loop = new Loop<>(new Invoke<>(this::throwBug)).scheduleFor(this);
    }

    private void throwBug () {
        if (isRepaired) return;
        if (generateRandomNumber() == 6) {
            toggle();
        }
    }

    private int generateRandomNumber() {
        return new Random().nextInt(25) + 1;
    }

    @Override
    public boolean repair () {
        if (isRepaired) return false;
        if (Objects.nonNull(loop)) {
            loop.dispose();
            this.isRepaired = true;

            new ActionSequence<>(
                new Wait<>(10),
                new Invoke<>(() -> this.isRepaired = false),
                new Loop<>(new Invoke<>(this::throwBug))
            ).scheduleFor(this);

            return true;
        }
        return false;
    }

}
