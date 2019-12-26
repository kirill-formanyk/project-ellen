package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class KeeperController implements KeyboardListener {
    private Keeper actor;

    public KeeperController(Keeper actor){
        this.actor = actor;
    }

    @Override
    public void keyPressed(Input.@NotNull Key key) {
        switch (key) {
            case ENTER :
                createAndScheduleTakeAction();
                break;

            case BACKSPACE:
                createAndScheduleDropAction();
                break;

            case S:
                createAndScheduleShiftAction();
                break;

            case U:
                createAndScheduleUseAction();
                break;

            case B:
                createAndScheduleUseItemFromBackpackAction();
                break;
        }

    }

    private void createAndScheduleUseItemFromBackpackAction() {
        Usable<?> usableItem = (Usable<?>) actor.getBackpack().peek();
        if (Objects.nonNull(usableItem)) {
            new Use<>(usableItem).scheduleForIntersectingWith(actor);
        }
    }

    private void createAndScheduleUseAction() {
        Usable<?> usableItem = (Usable<?>) actor.getScene().getActors()
            .stream()
            .filter(actor::intersects)
            .filter(Usable.class::isInstance)
            .findFirst()
            .orElse(null);
        if (Objects.nonNull(usableItem)) {
            new Use<>(usableItem).scheduleForIntersectingWith(actor);
        }
    }

    private void createAndScheduleTakeAction () {
        new Take<>().scheduleFor(actor);
    }

    private void createAndScheduleDropAction () {
        new Drop<>().scheduleFor(actor);
    }

    private void createAndScheduleShiftAction () {
        new Shift<>().scheduleFor(actor);
    }
}
