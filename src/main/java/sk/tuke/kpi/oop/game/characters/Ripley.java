package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Ripley extends AbstractActor implements Movable, Keeper {
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    private Backpack backpack;
    private int energy;
    private int ammo;
    private Animation dieAnimation;

    private static final int MAX_AMMO = 500;

    public Ripley () {
        super("Ellen");
        this.energy = 100;
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy <= 0) {
            this.energy = 0;
            setAnimation(dieAnimation);
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
            return;
        }
        this.energy = energy;
    }

    public boolean hasFullEnergy () {
        return energy == 100;
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

}
