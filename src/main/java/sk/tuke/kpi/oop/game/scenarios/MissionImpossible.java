package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {
    private Ripley ellen;
    private Disposable energyDecreasingAction;
    private Disposable movableControllerListener;
    private Disposable keeperControllerListener;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        MovableController movableController = new MovableController(ellen);
        movableControllerListener = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ellen);
        keeperControllerListener = scene.getInput().registerListener(keeperController);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED,
            door -> energyDecreasingAction =
                new Loop<>(
                    new ActionSequence<>(
                        new Wait<>(0.6f),
                        new Invoke<>(() -> ellen.setEnergy(ellen.getEnergy() - 1))
                    )
                ).scheduleFor(ellen)
        );

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,
            ripley -> new ActionSequence<>(
                new Invoke<>(movableControllerListener::dispose),
                new Invoke<>(keeperControllerListener::dispose),
                new Invoke<>(energyDecreasingAction::dispose),
                new Invoke<>(() -> scene.getGame().getOverlay().drawText("Ripley died!", 200, 250).showFor(Integer.MAX_VALUE)),
                new Wait<>(5),
                new Invoke<>(scene.getGame()::stop)
            ).scheduleFor(ellen)
        );

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED,
            ventilator -> new ActionSequence<>(
                new Invoke<>(movableControllerListener::dispose),
                new Invoke<>(keeperControllerListener::dispose),
                new Invoke<>(energyDecreasingAction::dispose),
                new Invoke<>(() -> scene.getGame().getOverlay().drawText("Mission completed", 200, 250).showFor(Integer.MAX_VALUE)),
                new Wait<>(5),
                new Invoke<>(scene.getGame()::stop)
            ).scheduleFor(ellen)
        );
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
                case "access card" : return new AccessCard();
                case "energy" : return new Energy();
                case "locker" : return new Locker();
                case "door" : return new LockedDoor();
                case "ventilator" : return new Ventilator();
                default: return null;
            }
        }
    }
}
