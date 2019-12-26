package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

import java.util.Objects;

public class ShooterController implements KeyboardListener {
    private Armed actor;

    public ShooterController(Armed actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(Input.@NotNull Key key) {
        if (key.equals(Input.Key.SPACE) && Objects.nonNull(actor)) {
            new Fire<>().scheduleFor(actor);
        }
    }
}
