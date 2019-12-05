package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Energy energy;
    private Ripley player;
    private Ammo ammo;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        player = new Ripley();
        energy = new Energy();
        ammo = new Ammo();
        player.setEnergy(50);
        scene.addActor(player, 0, 150);
        scene.addActor(energy, 10, -50);
        scene.addActor(ammo, -50, 120);

        MovableController movableController = new MovableController(player);
        scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(player);
        scene.getInput().registerListener(keeperController);


        new When<>(
            () -> player.intersects(energy),
            new Use<>(energy)
        ).scheduleFor(player);

        new When<>(
            () -> player.intersects(ammo),
            new Use<>(ammo)
        ).scheduleFor(player);

        Hammer hammer = new Hammer();
        Mjolnir mjolnir = new Mjolnir();
        FireExtinguisher extinguisher = new FireExtinguisher();
        FireExtinguisher e1 = new FireExtinguisher();
        Wrench wrench = new Wrench();

        scene.addActor(hammer, -10, 120);
        scene.addActor(extinguisher, 35, 10);
        scene.addActor(wrench, 100, -40);

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(() -> player.getBackpack().shift()),
            new Wait<>(2),
            new Invoke<>(() -> player.getBackpack().shift())
        ).scheduleFor(player);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

        scene.getGame().getOverlay().drawText("| Energy: " + player.getEnergy(), 100, yTextPos);
        scene.getGame().getOverlay().drawText("| Ammo: " + player.getAmmo(), 250, yTextPos);
        scene.getGame().pushActorContainer(player.getBackpack());
    }
}
