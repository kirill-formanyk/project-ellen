package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    private Backpack backpack;
    private Health health;
    private Firearm gun;
    private Animation dieAnimation;

    private Disposable movableControllerListener;
    private Disposable keeperControllerListener;
    private Disposable shooterControllerListener;

    private static final int MAX_AMMO = 500;

    public Ripley () {
        super("Ellen");
        this.health = new Health(200);
        this.gun = new Gun(100, 500);
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.dieAnimation =
            new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(
            new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
        );
        getAnimation().pause();
    }

    @Override
    public Backpack getBackpack () {
        return this.backpack;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving (Direction direction) {
        this.getAnimation().play();
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void stoppedMoving () {
        this.getAnimation().pause();
    }

    public void showState (Scene scene) {
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

        scene.getGame().getOverlay().drawText("| Health: " + this.health.getValue(), 100, yTextPos);
        scene.getGame().getOverlay().drawText("| Ammo: " + this.gun.getValue(), 250, yTextPos);
        scene.getGame().getOverlay().drawText("| Max ammo: " + MAX_AMMO, 380, yTextPos);
        scene.getGame().pushActorContainer(this.getBackpack());
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.health.onExhaustion(this::die);

        MovableController movableController = new MovableController(this);
        KeeperController keeperController = new KeeperController(this);
        ShooterController shooterController = new ShooterController(this);

        movableControllerListener = scene.getInput().registerListener(movableController);
        keeperControllerListener = scene.getInput().registerListener(keeperController);
        shooterControllerListener = scene.getInput().registerListener(shooterController);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED,
            door -> {
                if (door.getName().equals("exit door")) {
                    scene.getGame().getOverlay().drawText("Well done!", this.getPosX(), this.getPosY()).showFor(3);
                    movableControllerListener.dispose();
                    keeperControllerListener.dispose();
                    shooterControllerListener.dispose();
                    new ActionSequence<>(
                        new Wait<>(3),
                        new Invoke<>(scene.getGame()::stop)
                    ).scheduleOn(scene);
                }
        });
    }

    private void die () {
        Scene scene = getScene();

        if (Objects.nonNull(scene)) {
            setAnimation(dieAnimation);

            scene.getMessageBus().publish(RIPLEY_DIED, this);
            scene.cancelActions(this);

            movableControllerListener.dispose();
            keeperControllerListener.dispose();
            shooterControllerListener.dispose();

            new ActionSequence<>(
                new Invoke<>(() -> scene.getGame().getOverlay().drawText("Ripley died!", this.getPosX(), this.getPosY()).showFor(Integer.MAX_VALUE)),
                new Wait<>(3),
                new Invoke<>(scene.getGame()::stop)
            ).scheduleOn(scene);
        }
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }
}
