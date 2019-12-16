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
import sk.tuke.kpi.oop.game.items.Backpack;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive {
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    private Backpack backpack;
    private Health health;
    private int ammo;
    private Animation dieAnimation;

    private Disposable movableControllerListener;
    private Disposable keeperControllerListener;

    private static final int MAX_AMMO = 500;

    public Ripley () {
        super("Ellen");
        this.health = new Health(200);
        this.ammo = 0;
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

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public boolean hasFullAmmunition () {
        return this.ammo == MAX_AMMO;
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
        scene.getGame().getOverlay().drawText("| Ammo: " + this.getAmmo(), 250, yTextPos);
        scene.getGame().getOverlay().drawText("| Max ammo: " + MAX_AMMO, 380, yTextPos);
        scene.getGame().pushActorContainer(this.getBackpack());
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.health.onExhaustion(this::die);

        MovableController movableController = new MovableController(this);
        KeeperController keeperController = new KeeperController(this);

        movableControllerListener = scene.getInput().registerListener(movableController);
        keeperControllerListener = scene.getInput().registerListener(keeperController);
    }

    private void die () {
        Scene scene = getScene();

        if (Objects.nonNull(scene)) {
            setAnimation(dieAnimation);

            scene.getMessageBus().publish(RIPLEY_DIED, this);
            scene.cancelActions(this);

            movableControllerListener.dispose();
            keeperControllerListener.dispose();

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
}
