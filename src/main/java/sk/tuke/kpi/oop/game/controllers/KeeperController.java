package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.items.Collectible;

public class KeeperController implements KeyboardListener {
    private Keeper<Collectible> actor;

    public KeeperController(Keeper<Collectible> actor){
        this.actor = actor;
    }

    @Override
    public void keyPressed(Input.@NotNull Key key) {
        switch (key) {
            case ENTER :
                new Take<>().scheduleFor(actor);
                break;

            case BACKSPACE:
                new Drop<>().scheduleFor(actor);
                break;

            case S:
                new Shift<>().scheduleFor(actor);
                break;
        }

    }
}
